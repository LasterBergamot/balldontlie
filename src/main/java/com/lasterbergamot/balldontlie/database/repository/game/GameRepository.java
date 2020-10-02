package com.lasterbergamot.balldontlie.database.repository.game;

import com.lasterbergamot.balldontlie.database.model.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {}