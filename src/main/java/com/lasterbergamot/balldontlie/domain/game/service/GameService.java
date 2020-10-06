package com.lasterbergamot.balldontlie.domain.game.service;

import com.lasterbergamot.balldontlie.database.model.game.Game;

import java.util.Optional;

public interface GameService {
    void getAllGamesFromBalldontlieAPI();
    Optional<Game> getGame(Integer id);
}
