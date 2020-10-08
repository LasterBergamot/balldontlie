package com.lasterbergamot.balldontlie.domain.game.service;

import com.lasterbergamot.balldontlie.database.model.game.Game;
import com.lasterbergamot.balldontlie.database.model.player.Player;

import java.util.List;
import java.util.Optional;

public interface GameService {
    void getAllGamesFromBalldontlieAPI();

    List<Game> getGames(Player player);
    List<Game> getGames(final Optional<Integer> count);
    Optional<Game> getGame(final int id);
}
