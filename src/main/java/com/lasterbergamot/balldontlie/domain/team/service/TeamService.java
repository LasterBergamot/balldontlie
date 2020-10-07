package com.lasterbergamot.balldontlie.domain.team.service;

import com.lasterbergamot.balldontlie.database.model.team.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    void getAllTeamsFromBalldontlieAPI();

    List<Team> getAllTeams();
    List<Team> getTeams(final int count);
    Optional<Team> getTeam(final int id);
}
