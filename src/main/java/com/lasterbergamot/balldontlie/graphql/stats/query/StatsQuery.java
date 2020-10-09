package com.lasterbergamot.balldontlie.graphql.stats.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.lasterbergamot.balldontlie.database.model.stats.Stats;
import com.lasterbergamot.balldontlie.domain.stats.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StatsQuery implements GraphQLQueryResolver {

    private final StatsService statsService;

    public List<Stats> allStats(final int count) {
        return statsService.getAllStats(count);
    }

    public Optional<Stats> stats(final int id) {
        return statsService.getStats(id);
    }
}
