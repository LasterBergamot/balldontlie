package com.lasterbergamot.balldontlie.database.repository.stats;

import com.lasterbergamot.balldontlie.database.model.stats.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Integer> {}
