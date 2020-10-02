package com.lasterbergamot.balldontlie.domain.player.service.impl;

import com.lasterbergamot.balldontlie.database.model.player.Player;
import com.lasterbergamot.balldontlie.database.repository.player.PlayerRepository;
import com.lasterbergamot.balldontlie.domain.model.meta.Meta;
import com.lasterbergamot.balldontlie.domain.player.model.PlayerDTOWrapper;
import com.lasterbergamot.balldontlie.domain.player.service.PlayerService;
import com.lasterbergamot.balldontlie.domain.player.transform.PlayerTransformer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerTransformer playerTransformer;
    private final RestTemplate restTemplate;

    @Override
    public void getAllPlayersFromBalldontlieAPI() {
        log.info("Getting all players!");
        PlayerDTOWrapper playerDTOWrapper = restTemplate
                .getForObject(String.format("https://www.balldontlie.io/api/v1/players?per_page=100&page=%d", 1),
                        PlayerDTOWrapper.class);

        Optional.ofNullable(playerDTOWrapper)
                .ifPresentOrElse(this::handlePossibleNewPlayers, () -> log.error("The PlayerDTOWrapper object got from the API was null!"));
    }

    private void handlePossibleNewPlayers(PlayerDTOWrapper playerDTOWrapper) {
        List<Player> currentlySavedPlayers = playerRepository.findAll();
        Meta meta = playerDTOWrapper.getMeta();

        if (currentlySavedPlayers.size() < meta.getTotalCount()) {
            log.info("New players are available!");

            List<CompletableFuture<PlayerDTOWrapper>> completableFutureList = createCompletableFutureFromTheAPICalls(meta.getTotalPages());
            CompletableFuture<List<PlayerDTOWrapper>> allCompletableFuture = collectReturnValuesFromAllThreads(completableFutureList);
            List<Player> playersFromAPI = getPlayersFromAPI(playerDTOWrapper, allCompletableFuture);

            playersFromAPI.removeAll(currentlySavedPlayers);
            playerRepository.saveAll(playersFromAPI);
            log.info("Saved {} new players!", playersFromAPI.size());
        } else {
            log.info("No new players are available!");
        }
    }

    private List<CompletableFuture<PlayerDTOWrapper>> createCompletableFutureFromTheAPICalls(int totalPages) {
        List<CompletableFuture<PlayerDTOWrapper>> completableFutureList = new ArrayList<>();

        for (int currentPage = 2; currentPage <= totalPages; currentPage++) {
            int finalCurrentPage = currentPage;
            CompletableFuture<PlayerDTOWrapper> completableFuture = CompletableFuture.supplyAsync(
                    () -> restTemplate
                            .getForObject("https://www.balldontlie.io/api/v1/players?per_page=100&page=" + finalCurrentPage,
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
}
