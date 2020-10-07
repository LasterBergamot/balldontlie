package com.lasterbergamot.balldontlie.graphql.player.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.lasterbergamot.balldontlie.database.model.player.Player;
import com.lasterbergamot.balldontlie.domain.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PlayerQuery implements GraphQLQueryResolver {

    private final PlayerService playerService;

    public List<Player> getPlayers(final int count) {
        return playerService.getPlayers(count);
    }

    public Optional<Player> getPlayer(final int id) {
        return playerService.getPlayer(id);
    }
}
