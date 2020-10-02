package com.lasterbergamot.balldontlie.domain.player.service;

import com.lasterbergamot.balldontlie.database.model.player.Player;

public interface PlayerService {
    void getAllPlayersFromBalldontlieAPI();

    Player getPlayer(Integer id);
}
