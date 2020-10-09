package com.lasterbergamot.balldontlie.domain.game.service.impl;

import com.lasterbergamot.balldontlie.client.ThrottledRestTemplate;
import com.lasterbergamot.balldontlie.database.model.game.Game;
import com.lasterbergamot.balldontlie.database.model.player.Player;
import com.lasterbergamot.balldontlie.database.model.team.Team;
import com.lasterbergamot.balldontlie.database.repository.game.GameRepository;
import com.lasterbergamot.balldontlie.domain.DataImporter;
import com.lasterbergamot.balldontlie.domain.game.model.GameDTOWrapper;
import com.lasterbergamot.balldontlie.domain.game.service.GameService;
import com.lasterbergamot.balldontlie.domain.game.transform.GameTransformer;
import com.lasterbergamot.balldontlie.domain.model.meta.Meta;
import com.lasterbergamot.balldontlie.domain.team.service.TeamService;
import com.lasterbergamot.balldontlie.graphql.game.exception.GameMutationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService, DataImporter {

    private final GameRepository gameRepository;
    private final GameTransformer gameTransformer;
    private final TeamService teamService;
    private final ThrottledRestTemplate restTemplate;

    @Override
    public void getAllGamesFromBalldontlieAPI() {
        GameDTOWrapper gameDTOWrapper = restTemplate
                .getForObject(String.format("https://www.balldontlie.io/api/v1/games?per_page=100&page=%d", 1), GameDTOWrapper.class);

        Optional.ofNullable(gameDTOWrapper)
                .ifPresentOrElse(this::handlePossibleNewGames, () -> log.error("The GameDTOWrapper got from the API was null!"));
    }

    @Override
    public List<Game> getGames(Player player) {
        Team playerTeam = player.getTeam();

        if (playerTeam == null) {
            return new ArrayList<>();
        }

        List<Game> games = gameRepository.findAll();

        for (Iterator<Game> gameIterator = games.iterator(); gameIterator.hasNext();) {
            Game game = gameIterator.next();
            Team homeTeam = game.getHomeTeam();
            Team visitorTeam = game.getVisitorTeam();
            boolean thePlayerIsNotInEitherTeam = !playerTeam.equals(homeTeam) && !playerTeam.equals(visitorTeam);

            if (thePlayerIsNotInEitherTeam) {
                gameIterator.remove();
            }
        }

        return games;
    }

    @Override
    public List<Game> getGames(final Optional<Integer> count) {
        return gameRepository
                .findAll()
                .stream()
                .limit(count.orElse(Integer.MAX_VALUE))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<Game> getGame(final int id) {
        return gameRepository.findById(id);
    }

    @Override
    public Game createGame(String date,
                           Integer homeTeamScore, Integer visitorTeamScore,
                           Integer season, Integer period,
                           String status, String time,
                           Boolean postseason, Integer homeTeamId,
                           Integer visitorTeamId) {
        Map<String, Team> validationResults = validateMutationInputs(homeTeamId, visitorTeamId);

        Game game = createGameFromMutationInputs(gameRepository.getNextId(), date, homeTeamScore, visitorTeamScore, season, period, status, time, postseason,
                validationResults.get("homeTeam"), validationResults.get("visitorTeam"));

        return gameRepository.save(game);
    }

    private Map<String, Team> validateMutationInputs(Integer homeTeamId, Integer visitorTeamId) {
        Team homeTeam = checkAbsence(teamService.getTeam(homeTeamId), "The given home team does not exist in the database!");
        Team visitorTeam = checkAbsence(teamService.getTeam(visitorTeamId), "The given visitor team does not exist in the database!");

        return Map.of("homeTeam", homeTeam, "visitorTeam", visitorTeam);
    }

    private Team checkAbsence(Optional<Team> inputToValidate, String errorMessage) {
        return inputToValidate.orElseThrow(() -> new GameMutationException(errorMessage));
    }

    private Game createGameFromMutationInputs(Integer id, String date,
                                              Integer homeTeamScore, Integer visitorTeamScore,
                                              Integer season, Integer period,
                                              String status, String time,
                                              Boolean postseason, Team homeTeam,
                                              Team visitorTeam) {
        return Game.builder()
                .id(id)
                .date(LocalDate.parse(date))
                .homeTeam(homeTeam)
                .homeTeamScore(homeTeamScore)
                .period(period)
                .postseason(postseason)
                .season(season)
                .status(status)
                .time(time)
                .visitorTeam(visitorTeam)
                .visitorTeamScore(visitorTeamScore)
                .build();
    }

    private void handlePossibleNewGames(GameDTOWrapper gameDTOWrapper) {
        List<Game> currentlySavedGames = gameRepository.findAll();
        Meta meta = gameDTOWrapper.getMeta();

        log.info("Checking for possible new games!");
        if (currentlySavedGames.size() < meta.getTotalCount()) {
            List<CompletableFuture<GameDTOWrapper>> completableFutureList = createCompletableFuturesFromTheAPICalls(meta.getTotalPages());
            CompletableFuture<List<GameDTOWrapper>> allCompletableFuture = collectReturnValuesFromAllThreads(completableFutureList);
            List<Game> gamesFromAPI = getGamesFromAPI(gameDTOWrapper, allCompletableFuture);

            Set<Game> gamesToSave = new HashSet<>(gamesFromAPI);
            gamesToSave.removeAll(currentlySavedGames);

            if (gamesToSave.size() > 0) {
                log.info("New games are available!");
                gameRepository.saveAll(List.copyOf(gamesToSave));
                log.info("Saved {} new games!", gamesToSave.size());
            } else {
                log.info("No new games are available!");
            }
        } else {
            log.info("No new games are available!");
        }
    }

    @Override
    public void doImport() {
        getAllGamesFromBalldontlieAPI();
    }

    private List<CompletableFuture<GameDTOWrapper>> createCompletableFuturesFromTheAPICalls(int totalPages) {
        List<CompletableFuture<GameDTOWrapper>> completableFutureList = new ArrayList<>();

        for (int currentPage = 2; currentPage <= totalPages; currentPage++) {
            int finalCurrentPage = currentPage;
            CompletableFuture<GameDTOWrapper> completableFuture = CompletableFuture.supplyAsync(
                    () -> restTemplate
                            .getForObject(String.format("https://www.balldontlie.io/api/v1/games?per_page=100&page=%d", finalCurrentPage), GameDTOWrapper.class)
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
