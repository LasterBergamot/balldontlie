package com.lasterbergamot.balldontlie.domain.player.service.impl;

import com.lasterbergamot.balldontlie.client.ThrottledRestTemplate;
import com.lasterbergamot.balldontlie.database.model.player.Player;
import com.lasterbergamot.balldontlie.database.model.team.Team;
import com.lasterbergamot.balldontlie.database.repository.player.PlayerRepository;
import com.lasterbergamot.balldontlie.domain.DataImporter;
import com.lasterbergamot.balldontlie.domain.model.meta.Meta;
import com.lasterbergamot.balldontlie.domain.player.model.PlayerDTOWrapper;
import com.lasterbergamot.balldontlie.domain.player.service.PlayerService;
import com.lasterbergamot.balldontlie.domain.player.transform.PlayerTransformer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService, DataImporter {

    private final PlayerRepository playerRepository;
    private final PlayerTransformer playerTransformer;
    private final ThrottledRestTemplate restTemplate;

    @Override
    public void getAllPlayersFromBalldontlieAPI() {
        PlayerDTOWrapper playerDTOWrapper = restTemplate
                .getForObject(String.format("https://www.balldontlie.io/api/v1/players?per_page=100&page=%d", 1), PlayerDTOWrapper.class);

        Optional.ofNullable(playerDTOWrapper)
                .ifPresentOrElse(this::handlePossibleNewPlayers, () -> log.error("The PlayerDTOWrapper object got from the API was null!"));
    }

    @Override
    public void doImport() {
        getAllPlayersFromBalldontlieAPI();
    }

    private void handlePossibleNewPlayers(PlayerDTOWrapper playerDTOWrapper) {
        List<Player> currentlySavedPlayers = playerRepository.findAll();
        Meta meta = playerDTOWrapper.getMeta();

        log.info("Checking for possible new players!");
        if (currentlySavedPlayers.size() < meta.getTotalCount()) {
            List<CompletableFuture<PlayerDTOWrapper>> completableFutureList = createCompletableFuturesFromTheAPICalls(meta.getTotalPages());
            CompletableFuture<List<PlayerDTOWrapper>> allCompletableFuture = collectReturnValuesFromAllThreads(completableFutureList);
            List<Player> playersFromAPI = getPlayersFromAPI(playerDTOWrapper, allCompletableFuture);

            Set<Player> playersToSave = new HashSet<>(playersFromAPI);
            playersToSave.removeAll(currentlySavedPlayers);

            if (playersToSave.size() > 0) {
                log.info("New players are available!");
                playerRepository.saveAll(List.copyOf(playersToSave));
                log.info("Saved {} new players!", playersToSave.size());
            } else {
                log.info("No new players available!");
            }
        } else {
            log.info("No new players available!");
        }
    }

    private List<CompletableFuture<PlayerDTOWrapper>> createCompletableFuturesFromTheAPICalls(int totalPages) {
        List<CompletableFuture<PlayerDTOWrapper>> completableFutureList = new ArrayList<>();

        for (int currentPage = 2; currentPage <= totalPages; currentPage++) {
            int finalCurrentPage = currentPage;
            CompletableFuture<PlayerDTOWrapper> completableFuture = CompletableFuture.supplyAsync(
                    () -> restTemplate
                            .getForObject(String.format("https://www.balldontlie.io/api/v1/players?per_page=100&page=%d", finalCurrentPage),
                                    PlayerDTOWrapper.class)
            );

            completableFutureList.add(completableFuture);
        }

        return completableFutureList;
    }

    /**
     * After the completion of execution for all threads, collect all the return values from all the threads.
     * @param completableFutureList
     * @return
     */
    private CompletableFuture<List<PlayerDTOWrapper>> collectReturnValuesFromAllThreads(List<CompletableFuture<PlayerDTOWrapper>> completableFutureList) {
        return waitForThreadCompletion(completableFutureList).thenApply(
                future -> completableFutureList
                        .stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Wait until all the threads in the arguments get completed.
     * @param completableFutureList
     * @return
     */
    private CompletableFuture<Void> waitForThreadCompletion(List<CompletableFuture<PlayerDTOWrapper>> completableFutureList) {
        return CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]));
    }

    private List<Player> getPlayersFromAPI(PlayerDTOWrapper playerDTOWrapper, CompletableFuture<List<PlayerDTOWrapper>> allCompletableFuture) {
        List<Player> playersFromAPI = new ArrayList<>();

        try {
            List<PlayerDTOWrapper> playerDTOWrappers = allCompletableFuture.toCompletableFuture().get();

            // adding the first wrapper from the first API call
            playerDTOWrappers.add(0, playerDTOWrapper);

            playerDTOWrappers.forEach(
                    playerDTOWrapper1 -> playersFromAPI.addAll(playerTransformer.transformPlayerDTOListToPlayerList(playerDTOWrapper1.getPlayerDTOs()))
            );
        } catch (ExecutionException | InterruptedException exception) {
            log.error(exception.getMessage());
        }

        return playersFromAPI;
    }

    //TODO:
    // Create Height class: with feet and inches. Create compareTo() method (similar behaviour as e.g. Integer.compareTo()).
    // Create static Height.convert(feet, inches) method.
    // Make sure that the feet value can exist without the inches value, but not vice versa. If feet is absent while inches exists throw an exception.
    // Validate feet to fall in the following interval: [4, 8], if not throw an exception
    // Validate inches to fall in the following interval: [0, 11], if not throw and exception
    // Validate weight to fall in the following interval: [150, 400], if not throw and exception
    @Override
    public List<Player> getPlayers(final Optional<Integer> count, final Optional<Integer> minimumFeet, final Optional<Integer> minimumInches, final Optional<Integer> minimumWeight) {
        return playerRepository
                .findAll()
                .stream()
                .filter(getAttributesPredicate(minimumFeet, minimumInches, minimumWeight))
                .limit(count.orElse(Integer.MAX_VALUE))
                .collect(Collectors.toUnmodifiableList());
    }

    private Predicate<Player> getAttributesPredicate(final Optional<Integer> minimumFeet, final Optional<Integer> minimumInches, final Optional<Integer> minimumWeight) {
        Predicate<Player> feetPredicate = createPredicateFromQueryParameter(minimumFeet, Player::getHeightFeet);
        Predicate<Player> inchesPredicate = createPredicateFromQueryParameter(minimumInches, Player::getHeightInches);
        Predicate<Player> weightPredicate = createPredicateFromQueryParameter(minimumWeight, Player::getWeightPounds);

        return feetPredicate.and(inchesPredicate).and(weightPredicate);
    }

    private Predicate<Player> createPredicateFromQueryParameter(final Optional<Integer> queryParameter, final Function<Player, Integer> attributeFunction) {
        return queryParameter.isEmpty()
                ? player -> true
                : player -> Optional.ofNullable(attributeFunction.apply(player)).orElse(0) >= queryParameter.get();
    }

    @Override
    public List<Player> getPlayers(Team team) {
        return playerRepository.findAllByTeam(team);
    }

    @Override
    public Optional<Player> getPlayer(final int id) {
        log.info("Getting player with id: {}", id);
        return playerRepository.findById(id);
    }
}
