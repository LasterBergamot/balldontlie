package com.lasterbergamot.balldontlie.domain.game.service;

import com.lasterbergamot.balldontlie.database.model.game.Game;

import java.util.List;
import java.util.Optional;

public interface GameService {
    void getAllGamesFromBalldontlieAPI();

    List<Game> getGames(final int count);
    Optional<Game> getGame(final int id);
}
