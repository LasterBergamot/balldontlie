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
import java.util.stream.Collectors;

import static com.lasterbergamot.balldontlie.util.Constants.ERR_MSG_THE_STATS_DTOWRAPPER_OBJECT_GOT_FROM_THE_API_WAS_NULL;
import static com.lasterbergamot.balldontlie.util.Constants.URL_STATS_BALLDONTLIE_API_PER_PAGE_100_PAGE;

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
                .getForObject(String.format(URL_STATS_BALLDONTLIE_API_PER_PAGE_100_PAGE, 1), StatsDTOWrapper.class);

        Optional.ofNullable(statsDTOWrapper)
                .ifPresentOrElse(this::handlePossibleNewStats, () -> log.error(ERR_MSG_THE_STATS_DTOWRAPPER_OBJECT_GOT_FROM_THE_API_WAS_NULL));
    }

    @Override
    public List<Stats> getAllStats(final Optional<Integer> count) {
        return statsRepository
                .findAll()
                .stream()
                .limit(count.orElse(Integer.MAX_VALUE))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Stats> getAllStats(final Player player) {
        return statsRepository.findAllByPlayer(player);
    }

    @Override
    public Optional<Stats> getStats(final int id) {
        return statsRepository.findById(id);
    }

    private void handlePossibleNewStats(final StatsDTOWrapper statsDTOWrapper) {
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

    private void handlePossibleNonStoredGames(final Set<Stats> statsToSave) {
        for (Iterator<Stats> statsIterator = statsToSave.iterator(); statsIterator.hasNext();) {
            Stats stats = statsIterator.next();
            Game game = stats.getGame();
            Optional<Game> gameFromRepository = gameService.getGame(game.getId());

            if (gameFromRepository.isEmpty()) {
                statsIterator.remove();
            }
        }
    }

    private List<CompletableFuture<StatsDTOWrapper>> createCompletableFuturesFromTheAPICalls(final Integer totalPages) {
        List<CompletableFuture<StatsDTOWrapper>> completableFutureList = new ArrayList<>();

        for (int currentPage = 2; currentPage <= totalPages; currentPage++) {
            int finalCurrentPage = currentPage;
            CompletableFuture<StatsDTOWrapper> completableFuture = CompletableFuture.supplyAsync(
                    () -> restTemplate
                            .getForObject(String.format(URL_STATS_BALLDONTLIE_API_PER_PAGE_100_PAGE, finalCurrentPage),
                                    StatsDTOWrapper.class)
            );

            completableFutureList.add(completableFuture);
        }

        return completableFutureList;
    }

    private CompletableFuture<List<StatsDTOWrapper>> collectReturnValuesFromAllThreads(final List<CompletableFuture<StatsDTOWrapper>> completableFutureList) {
        return waitForThreadCompletion(completableFutureList).thenApply(
                future -> completableFutureList
                        .stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );
    }

    private CompletableFuture<Void> waitForThreadCompletion(final List<CompletableFuture<StatsDTOWrapper>> completableFutureList) {
        return CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]));
    }

    private List<Stats> getPlayersFromAPI(final StatsDTOWrapper statsDTOWrapper, final CompletableFuture<List<StatsDTOWrapper>> allCompletableFuture) {
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
