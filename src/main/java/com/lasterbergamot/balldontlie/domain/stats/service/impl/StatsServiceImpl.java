package com.lasterbergamot.balldontlie.domain.stats.service.impl;

import com.lasterbergamot.balldontlie.client.ThrottledRestTemplate;
import com.lasterbergamot.balldontlie.database.model.game.Game;
import com.lasterbergamot.balldontlie.database.model.player.Player;
import com.lasterbergamot.balldontlie.database.model.stats.Stats;
import com.lasterbergamot.balldontlie.database.repository.stats.StatsRepository;
import com.lasterbergamot.balldontlie.domain.DataImporter;
import com.lasterbergamot.balldontlie.domain.game.service.GameService;
import com.lasterbergamot.balldontlie.domain.model.meta.Meta;
import com.lasterbergamot.balldontlie.domain.stats.model.StatsDTOWrapper;
import com.lasterbergamot.balldontlie.domain.stats.service.StatsService;
import com.lasterbergamot.balldontlie.domain.stats.transform.StatsTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
public class StatsServiceImpl implements StatsService, DataImporter {

    private final StatsRepository statsRepository;
    private final GameService gameService;
    private final StatsTransformer statsTransformer;
    private final ThrottledRestTemplate restTemplate;

    @Override
    public void getAllStatsFromBalldontlieAPI() {
        StatsDTOWrapper statsDTOWrapper = restTemplate
                .getForObject(String.format("https://www.balldontlie.io/api/v1/stats?per_page=100&page=%d", 1), StatsDTOWrapper.class);

        Optional.ofNullable(statsDTOWrapper)
                .ifPresentOrElse(this::handlePossibleNewStats, () -> log.error("The StatsDTOWrapper object got from the API was null!"));
    }

    @Override
    public List<Stats> getAllStats(final int count) {
        List<Stats> statsList = statsRepository.findAll();

        return count == -1
                ? statsList
                : statsList.stream().limit(count).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Stats> getAllStats(Player player) {
        return statsRepository.findAllByPlayer(player);
    }

    @Override
    public Optional<Stats> getStats(final int id) {
        return statsRepository.findById(id);
    }

    private void handlePossibleNewStats(StatsDTOWrapper statsDTOWrapper) {
        List<Stats> currentlySavedStats = statsRepository.findAll();
        Meta meta = statsDTOWrapper.getMeta();

        log.info("Checking for possible new stats!");
        if (currentlySavedStats.size() < meta.getTotalCount()) {
            List<CompletableFuture<StatsDTOWrapper>> completableFutureList = createCompletableFuturesFromTheAPICalls(meta.getTotalPages());
            CompletableFuture<List<StatsDTOWrapper>> allCompletableFuture = collectReturnValuesFromAllThreads(completableFutureList);
            List<Stats> statsFromAPI = getPlayersFromAPI(statsDTOWrapper, allCompletableFuture);

            Set<Stats> statsToSave = new HashSet<>(statsFromAPI);
            statsToSave.removeAll(currentlySavedStats);

            handlePossibleNonStoredGames(statsToSave);

            if (statsToSave.size() > 0) {
                log.info("New stats are available!");
                statsRepository.saveAll(List.copyOf(statsToSave));
                log.info("Saved {} new stats!", statsToSave.size());
            } else {
                log.info("No new stats available!");
            }
        } else {
            log.info("No new stats available!");
        }
    }

    @Override
    public void doImport() {
        getAllStatsFromBalldontlieAPI();
    }

    private void handlePossibleNonStoredGames(Set<Stats> statsToSave) {
        for (Iterator<Stats> statsIterator = statsToSave.iterator(); statsIterator.hasNext();) {
            Stats stats = statsIterator.next();
            Game game = stats.getGame();
            Optional<Game> gameFromRepository = gameService.getGame(game.getId());

            if (gameFromRepository.isEmpty()) {
                statsIterator.remove();
            }
        }
    }

    private List<CompletableFuture<StatsDTOWrapper>> createCompletableFuturesFromTheAPICalls(Integer totalPages) {
        List<CompletableFuture<StatsDTOWrapper>> completableFutureList = new ArrayList<>();
        int maxNumberOfRequests = Math.min(totalPages, 13);

        int min = 2;
        int max = totalPages - maxNumberOfRequests;
        int minLimit = ThreadLocalRandom.current().nextInt(max - min) + min;
        int maxLimit = minLimit + maxNumberOfRequests;

        for (int currentPage = minLimit; currentPage <= maxLimit; currentPage++) {
            int finalCurrentPage = currentPage;
            CompletableFuture<StatsDTOWrapper> completableFuture = CompletableFuture.supplyAsync(
                    () -> restTemplate
                            .getForObject(String.format("https://www.balldontlie.io/api/v1/stats?per_page=100&page=%d", finalCurrentPage),
                                    StatsDTOWrapper.class)
            );

            completableFutureList.add(completableFuture);
        }

        return completableFutureList;
    }

    private CompletableFuture<List<StatsDTOWrapper>> collectReturnValuesFromAllThreads(List<CompletableFuture<StatsDTOWrapper>> completableFutureList) {
        return waitForThreadCompletion(completableFutureList).thenApply(
                future -> completableFutureList
                        .stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );
    }

    private CompletableFuture<Void> waitForThreadCompletion(List<CompletableFuture<StatsDTOWrapper>> completableFutureList) {
        return CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]));
    }

    private List<Stats> getPlayersFromAPI(StatsDTOWrapper statsDTOWrapper, CompletableFuture<List<StatsDTOWrapper>> allCompletableFuture) {
        List<Stats> playersFromAPI = new ArrayList<>();

        try {
            List<StatsDTOWrapper> statsDTOWrappers = allCompletableFuture.toCompletableFuture().get();

            // adding the first wrapper from the first API call
            statsDTOWrappers.add(0, statsDTOWrapper);

            statsDTOWrappers.forEach(
                    statsDTOWrapper1 -> playersFromAPI.addAll(statsTransformer.transformStatsDTOListToStatsList(statsDTOWrapper1.getStatsDTOs()))
            );
        } catch (ExecutionException | InterruptedException exception) {
            log.error(exception.getMessage());
        }

        return playersFromAPI;
    }
}
