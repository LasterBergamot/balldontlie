package com.lasterbergamot.balldontlie.domain.game.service.impl;

import com.lasterbergamot.balldontlie.database.model.game.Game;
import com.lasterbergamot.balldontlie.database.repository.game.GameRepository;
import com.lasterbergamot.balldontlie.domain.game.model.GameDTOWrapper;
import com.lasterbergamot.balldontlie.domain.game.service.GameService;
import com.lasterbergamot.balldontlie.domain.game.transform.GameTransformer;
import com.lasterbergamot.balldontlie.domain.model.meta.Meta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameTransformer gameTransformer;
    private final RestTemplate restTemplate;

    @Override
    public void getAllGamesFromBalldontlieAPI() {
        GameDTOWrapper gameDTOWrapper = restTemplate
                .getForObject(String.format("https://www.balldontlie.io/api/v1/games?per_page=100?page=%d", 1), GameDTOWrapper.class);

        Optional.ofNullable(gameDTOWrapper)
                .ifPresentOrElse(this::handlePossibleNewGames, () -> log.error("The GameDTOWrapper got from the API was null!"));
    }

    private void handlePossibleNewGames(GameDTOWrapper gameDTOWrapper) {
        List<Game> currentlySavedGames = gameRepository.findAll();
        Meta meta = gameDTOWrapper.getMeta();

        if (currentlySavedGames.size() < meta.getTotalCount()) {
            log.info("New games are available!");

            List<CompletableFuture<GameDTOWrapper>> completableFutureList = createCompletableFuturesFromTheAPICalls(meta.getTotalPages());
            CompletableFuture<List<GameDTOWrapper>> allCompletableFuture = collectReturnValuesFromAllThreads(completableFutureList);
            List<Game> gamesFromAPI = getGamesFromAPI(gameDTOWrapper, allCompletableFuture);

            Set<Game> gamesToSave = new HashSet<>(currentlySavedGames);
            gamesToSave.addAll(gamesFromAPI);

            gameRepository.saveAll(List.copyOf(gamesToSave));
            log.info("Saved {} new games!", gamesToSave.size());
        } else {
            log.info("No new games are available!");
        }
    }

    private List<CompletableFuture<GameDTOWrapper>> createCompletableFuturesFromTheAPICalls(int totalPages) {
        List<CompletableFuture<GameDTOWrapper>> completableFutureList = new ArrayList<>();
        // if the app is started for the first time, then:
        // - all of the teams were get in 1 request
        // - all of the players were get in 33 requests
        // -> 26 requests remain for this minute, because of the 60 requests/min limit
        int maxNumberOfRequests = Math.min(totalPages, 26);

        int min = 2;
        int max = totalPages - maxNumberOfRequests;
        int minLimit = ThreadLocalRandom.current().nextInt(max - min) + min;
        int maxLimit = minLimit + maxNumberOfRequests;

        log.info("minLimit: {}",  minLimit);
        log.info("maxLimit: {}", maxLimit);
        for (int currentPage = minLimit; currentPage <= maxLimit; currentPage++) {
            int finalCurrentPage = currentPage;
            CompletableFuture<GameDTOWrapper> completableFuture = CompletableFuture.supplyAsync(
                    () -> restTemplate
                            .getForObject(String.format("https://www.balldontlie.io/api/v1/games?per_page=100?page=%d", finalCurrentPage), GameDTOWrapper.class)
            );

            completableFutureList.add(completableFuture);
        }

        return completableFutureList;
    }

    private CompletableFuture<List<GameDTOWrapper>> collectReturnValuesFromAllThreads(List<CompletableFuture<GameDTOWrapper>> completableFutureList) {
        return waitForThreadCompletion(completableFutureList).thenApply(
                future -> completableFutureList
                            .stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList())
        );
    }

    private CompletableFuture<Void> waitForThreadCompletion(List<CompletableFuture<GameDTOWrapper>> completableFutureList) {
        return CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]));
    }

    private List<Game> getGamesFromAPI(GameDTOWrapper gameDTOWrapper, CompletableFuture<List<GameDTOWrapper>> allCompletableFuture) {
        List<Game> gamesFromAPI = new ArrayList<>();

        try {
            List<GameDTOWrapper> gameDTOWrappers = allCompletableFuture.toCompletableFuture().get();
            gameDTOWrappers.add(0, gameDTOWrapper);

            gameDTOWrappers.forEach(
                    gameDTOWrapper1 -> gamesFromAPI.addAll(gameTransformer.transformGameDTOListToGameList(gameDTOWrapper1.getGameDTOs()))
            );
        } catch (ExecutionException | InterruptedException exception) {
            log.error(exception.getMessage());
        }

        return gamesFromAPI;
    }
}
