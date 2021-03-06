package com.lasterbergamot.balldontlie.graphql.team.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.lasterbergamot.balldontlie.database.model.team.Conference;
import com.lasterbergamot.balldontlie.database.model.team.Division;
import com.lasterbergamot.balldontlie.database.model.team.Team;
import com.lasterbergamot.balldontlie.domain.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TeamQuery implements GraphQLQueryResolver {

    private final TeamService teamService;

    public List<Team> teams(final Optional<Integer> count, final Conference conference, final Division division) {
        return teamService.getTeams(count, conference, division);
    }

    public Optional<Team> team(final int id) {
        return teamService.getTeam(id);
    }
}
