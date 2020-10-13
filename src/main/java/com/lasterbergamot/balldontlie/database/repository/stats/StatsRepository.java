package com.lasterbergamot.balldontlie.database.repository.stats;

import com.lasterbergamot.balldontlie.database.model.player.Player;
import com.lasterbergamot.balldontlie.database.model.stats.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Integer> {
    List<Stats> findAllByPlayer(final Player player);
}
