package com.lasterbergamot.balldontlie.domain.team.service;

import com.lasterbergamot.balldontlie.database.model.team.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    void getAllTeamsFromBalldontlieAPI();

    List<Team> getTeams(final int count, final String conference, final String division);
    Optional<Team> getTeam(final int id);
}
