package com.lasterbergamot.balldontlie.graphql.team.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.lasterbergamot.balldontlie.database.model.player.Player;
import com.lasterbergamot.balldontlie.database.model.team.Team;
import com.lasterbergamot.balldontlie.domain.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TeamResolver implements GraphQLResolver<Team> {

    private final PlayerService playerService;

    public List<Player> getPlayers(Team team) {
        return playerService.getPlayers(team);
    }
}
