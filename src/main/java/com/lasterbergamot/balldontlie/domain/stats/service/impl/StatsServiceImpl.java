package com.lasterbergamot.balldontlie.domain.stats.service.impl;

import com.lasterbergamot.balldontlie.database.model.stats.Stats;
import com.lasterbergamot.balldontlie.database.repository.stats.StatsRepository;
import com.lasterbergamot.balldontlie.domain.model.meta.Meta;
import com.lasterbergamot.balldontlie.domain.stats.model.StatsDTOWrapper;
import com.lasterbergamot.balldontlie.domain.stats.service.StatsService;
import com.lasterbergamot.balldontlie.domain.stats.transform.StatsTransformer;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;
    private final StatsTransformer statsTransformer;
    private final RestTemplate restTemplate;

    @Override
    public void getAllStatsFromBalldontlieAPI() {
        StatsDTOWrapper statsDTOWrapper = restTemplate
                .getForObject(String.format("https://www.balldontlie.io/api/v1/stats?per_page=100&page=%d", 1), StatsDTOWrapper.class);

        Optional.ofNullable(statsDTOWrapper)
                .ifPresentOrElse(this::handlePossibleNewStats, () -> log.error("The StatsDTOWrapper object got from the API was null!"));
    }

    private void handlePossibleNewStats(StatsDTOWrapper statsDTOWrapper) {
        List<Stats> currentlySavedStats = statsRepository.findAll();
        Meta meta = statsDTOWrapper.getMeta();

        if (currentlySavedStats.size() < meta.getTotalCount()) {
            log.info("New stats are available!");

            List<CompletableFuture<StatsDTOWrapper>> completableFutureList = createCompletableFuturesFromTheAPICalls(meta.getTotalPages());
            CompletableFuture<List<StatsDTOWrapper>> allCompletableFuture = collectReturnValuesFromAllThreads(completableFutureList);
            List<Stats> statsFromAPI = getPlayersFromAPI(statsDTOWrapper, allCompletableFuture);

            Set<Stats> statsToSave = new HashSet<>(statsFromAPI);
            statsToSave.addAll(currentlySavedStats);

            statsRepository.saveAll(List.copyOf(statsToSave));
            log.info("Saved {} new stats!", statsToSave.size());
        } else {
            log.info("No new stats available!");
        }
    }

    private List<CompletableFuture<StatsDTOWrapper>> createCompletableFuturesFromTheAPICalls(Integer totalPages) {
        List<CompletableFuture<StatsDTOWrapper>> completableFutureList = new ArrayList<>();

        for (int currentPage = 2; currentPage <= totalPages; currentPage++) {
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
