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

    public List<Player> players(final Optional<Integer> count, final Optional<Integer> minimumFeet, final Optional<Integer> minimumInches, final Optional<Integer> minimumWeight) {
        return playerService.getPlayers(count, minimumFeet, minimumInches, minimumWeight);
    }

    public Optional<Player> player(final int id) {
        return playerService.getPlayer(id);
    }
}
