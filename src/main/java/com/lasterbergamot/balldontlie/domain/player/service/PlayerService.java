package com.lasterbergamot.balldontlie.domain.player.service;

import com.lasterbergamot.balldontlie.database.model.player.Player;
import com.lasterbergamot.balldontlie.database.model.team.Team;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    void getAllPlayersFromBalldontlieAPI();

    List<Player> getPlayers(final int count);
    List<Player> getPlayers(Team team);
    Optional<Player> getPlayer(final int id);
}
