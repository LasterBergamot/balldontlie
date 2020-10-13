package com.lasterbergamot.balldontlie.domain.player.service.impl;

import com.lasterbergamot.balldontlie.client.ThrottledRestTemplate;
import com.lasterbergamot.balldontlie.database.model.player.Player;
import com.lasterbergamot.balldontlie.database.model.team.Team;
import com.lasterbergamot.balldontlie.database.repository.player.PlayerRepository;
import com.lasterbergamot.balldontlie.domain.DataImporter;
import com.lasterbergamot.balldontlie.domain.model.meta.Meta;
import com.lasterbergamot.balldontlie.domain.player.model.Height;
import com.lasterbergamot.balldontlie.domain.player.model.PlayerDTOWrapper;
import com.lasterbergamot.balldontlie.domain.player.service.PlayerService;
import com.lasterbergamot.balldontlie.domain.player.transform.PlayerTransformer;
import com.lasterbergamot.balldontlie.graphql.player.exception.PlayerQueryException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Range;
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

import static com.lasterbergamot.balldontlie.util.Constants.ATTRIBUTE_FEET;
import static com.lasterbergamot.balldontlie.util.Constants.ATTRIBUTE_INCHES;
import static com.lasterbergamot.balldontlie.util.Constants.ATTRIBUTE_WEIGHT;
import static com.lasterbergamot.balldontlie.util.Constants.ERR_MSG_THE_GIVEN_S_VALUE_IS_NOT_VALID_VALID_VALUES_ARE_IN_THE_RANGE_OF_S;
import static com.lasterbergamot.balldontlie.util.Constants.ERR_MSG_THE_INCHES_VALUE_IS_PRESENT_WITHOUT_THE_FEET_VALUE;
import static com.lasterbergamot.balldontlie.util.Constants.ERR_MSG_THE_PLAYER_DTOWRAPPER_OBJECT_GOT_FROM_THE_API_WAS_NULL;
import static com.lasterbergamot.balldontlie.util.Constants.URL_PLAYERS_BALLDONTLIE_API_PER_PAGE_100_PAGE;

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
                .getForObject(String.format(URL_PLAYERS_BALLDONTLIE_API_PER_PAGE_100_PAGE, 1), PlayerDTOWrapper.class);

        Optional.ofNullable(playerDTOWrapper)
                .ifPresentOrElse(this::handlePossibleNewPlayers, () -> log.error(ERR_MSG_THE_PLAYER_DTOWRAPPER_OBJECT_GOT_FROM_THE_API_WAS_NULL));
    }

    @Override
    public void doImport() {
        getAllPlayersFromBalldontlieAPI();
    }

    private void handlePossibleNewPlayers(final PlayerDTOWrapper playerDTOWrapper) {
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

    private List<CompletableFuture<PlayerDTOWrapper>> createCompletableFuturesFromTheAPICalls(final int totalPages) {
        List<CompletableFuture<PlayerDTOWrapper>> completableFutureList = new ArrayList<>();

        for (int currentPage = 2; currentPage <= totalPages; currentPage++) {
            int finalCurrentPage = currentPage;
            CompletableFuture<PlayerDTOWrapper> completableFuture = CompletableFuture.supplyAsync(
                    () -> restTemplate
                            .getForObject(String.format(URL_PLAYERS_BALLDONTLIE_API_PER_PAGE_100_PAGE, finalCurrentPage),
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
    private CompletableFuture<List<PlayerDTOWrapper>> collectReturnValuesFromAllThreads(final List<CompletableFuture<PlayerDTOWrapper>> completableFutureList) {
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
    private CompletableFuture<Void> waitForThreadCompletion(final List<CompletableFuture<PlayerDTOWrapper>> completableFutureList) {
        return CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]));
    }

    private List<Player> getPlayersFromAPI(final PlayerDTOWrapper playerDTOWrapper, final CompletableFuture<List<PlayerDTOWrapper>> allCompletableFuture) {
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

    @Override
    public List<Player> getPlayers(final Optional<Integer> count, final Optional<Integer> minimumFeet, final Optional<Integer> minimumInches, final Optional<Integer> minimumWeight) {
        validateQueryParameters(minimumFeet, minimumInches, minimumWeight);

        return playerRepository
                .findAll()
                .stream()
                .filter(getAttributesPredicate(minimumFeet, minimumInches, minimumWeight))
                .limit(count.orElse(Integer.MAX_VALUE))
                .collect(Collectors.toUnmodifiableList());
    }

    private void validateQueryParameters(final Optional<Integer> minimumFeet, final Optional<Integer> minimumInches, final Optional<Integer> minimumWeight) {
        validateQueryParameter(minimumFeet, Range.between(4, 8), ATTRIBUTE_FEET);
        validateQueryParameter(minimumInches, Range.between(0, 11), ATTRIBUTE_INCHES);
        validateQueryParameter(minimumWeight, Range.between(150, 400), ATTRIBUTE_WEIGHT);

        if (minimumFeet.isEmpty() && minimumInches.isPresent()) {
            throw new PlayerQueryException(ERR_MSG_THE_INCHES_VALUE_IS_PRESENT_WITHOUT_THE_FEET_VALUE);
        }
    }

    private void validateQueryParameter(final Optional<Integer> queryParameter, final Range<Integer> range, final String attribute) {
        if (queryParameter.isPresent() && !range.contains(queryParameter.get())) {
            throw new PlayerQueryException(String.format(ERR_MSG_THE_GIVEN_S_VALUE_IS_NOT_VALID_VALID_VALUES_ARE_IN_THE_RANGE_OF_S, attribute, range));
        }
    }

    private Predicate<Player> getAttributesPredicate(final Optional<Integer> minimumFeet, final Optional<Integer> minimumInches, final Optional<Integer> minimumWeight) {
        Predicate<Player> heightPredicate = createHeightPredicate(minimumFeet, minimumInches);
        Predicate<Player> weightPredicate = createPredicateFromQueryParameter(minimumWeight, Player::getWeightPounds);

        return heightPredicate.and(weightPredicate);
    }

    private Predicate<Player> createHeightPredicate(final Optional<Integer> minimumFeet, final Optional<Integer> minimumInches) {
        Height height = Height.convert(minimumFeet.orElse(0), minimumInches.orElse(0));

        return minimumFeet.isEmpty()
                ? player -> true
                : player -> {
                    Integer feet = Optional.ofNullable(player.getHeightFeet()).orElse(0);
                    Integer inches = Optional.ofNullable(player.getHeightInches()).orElse(0);

                    return height.compareTo(Height.convert(feet, inches)) <= 0;
                };
    }

    private Predicate<Player> createPredicateFromQueryParameter(final Optional<Integer> queryParameter, final Function<Player, Integer> attributeFunction) {
        return queryParameter.isEmpty()
                ? player -> true
                : player -> Optional.ofNullable(attributeFunction.apply(player)).orElse(0) >= queryParameter.get();
    }

    @Override
    public List<Player> getPlayers(final Team team) {
        return playerRepository.findAllByTeam(team);
    }

    @Override
    public Optional<Player> getPlayer(final int id) {
        log.info("Getting player with id: {}", id);
        return playerRepository.findById(id);
    }
}
