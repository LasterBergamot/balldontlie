package com.lasterbergamot.balldontlie.domain.team.service;

import com.lasterbergamot.balldontlie.database.model.team.Conference;
import com.lasterbergamot.balldontlie.database.model.team.Division;
import com.lasterbergamot.balldontlie.database.model.team.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    void getAllTeamsFromBalldontlieAPI();

    List<Team> getTeams(final Optional<Integer> count, final Conference conference, final Division division);
    Optional<Team> getTeam(final int id);
}
