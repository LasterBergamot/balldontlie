package com.lasterbergamot.balldontlie.graphql.player.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.lasterbergamot.balldontlie.database.model.game.Game;
import com.lasterbergamot.balldontlie.database.model.player.Player;
import com.lasterbergamot.balldontlie.database.model.stats.Stats;
import com.lasterbergamot.balldontlie.domain.game.service.GameService;
import com.lasterbergamot.balldontlie.domain.stats.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlayerResolver implements GraphQLResolver<Player> {

    private final GameService gameService;
    private final StatsService statsService;

    public List<Game> getGames(Player player) {
        return gameService.getGames(player);
    }

    public List<Stats> getStats(Player player) {
        return statsService.getAllStats(player);
    }
}
