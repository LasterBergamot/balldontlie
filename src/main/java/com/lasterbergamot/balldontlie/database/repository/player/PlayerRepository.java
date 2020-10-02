package com.lasterbergamot.balldontlie.database.repository.player;

import com.lasterbergamot.balldontlie.database.model.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {}