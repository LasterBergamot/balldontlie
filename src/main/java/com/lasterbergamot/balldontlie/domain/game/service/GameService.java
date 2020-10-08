package com.lasterbergamot.balldontlie.domain.game.service;

import com.lasterbergamot.balldontlie.database.model.game.Game;
import com.lasterbergamot.balldontlie.database.model.player.Player;
import com.lasterbergamot.balldontlie.database.model.team.Team;

import java.util.List;
import java.util.Optional;

public interface GameService {
    void getAllGamesFromBalldontlieAPI();

    List<Game> getGames(Player player);
    List<Game> getGames(final Optional<Integer> count);
    Optional<Game> getGame(final int id);

    Game createGame(final Integer id, final String date,
                    final Integer homeTeamScore, final Integer visitorTeamScore,
                    final Integer season, final Integer period,
                    final String status, final String time,
                    final Boolean postseason, final Team homeTeam,
                    final Team visitorTeam);
}
