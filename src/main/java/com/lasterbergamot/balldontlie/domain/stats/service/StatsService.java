package com.lasterbergamot.balldontlie.domain.stats.service;

import com.lasterbergamot.balldontlie.database.model.player.Player;
import com.lasterbergamot.balldontlie.database.model.stats.Stats;

import java.util.List;
import java.util.Optional;

public interface StatsService {
    void getAllStatsFromBalldontlieAPI();

    List<Stats> getAllStats(final int count);
    List<Stats> getAllStats(Player player);
    Optional<Stats> getStats(final int id);
}
