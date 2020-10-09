package com.lasterbergamot.balldontlie.graphql.game.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.lasterbergamot.balldontlie.database.model.game.Game;
import com.lasterbergamot.balldontlie.domain.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GameQuery implements GraphQLQueryResolver {

    private final GameService gameService;

    public List<Game> games(final Optional<Integer> count) {
        return gameService.getGames(count);
    }

    public Optional<Game> game(final int id) {
        return gameService.getGame(id);
    }
}
