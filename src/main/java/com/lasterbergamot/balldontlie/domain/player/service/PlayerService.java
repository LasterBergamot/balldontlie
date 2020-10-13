package com.lasterbergamot.balldontlie.domain.player.service;

import com.lasterbergamot.balldontlie.database.model.player.Player;
import com.lasterbergamot.balldontlie.database.model.team.Team;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    void getAllPlayersFromBalldontlieAPI();

    List<Player> getPlayers(final Optional<Integer> count, final Optional<Integer> minimumFeet, final Optional<Integer> minimumInches, final Optional<Integer> minimumWeight);
    List<Player> getPlayers(Team team);
    Optional<Player> getPlayer(final int id);
}
