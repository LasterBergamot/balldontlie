package com.lasterbergamot.balldontlie.domain.player.service.impl;

import com.lasterbergamot.balldontlie.database.repository.player.PlayerRepository;
import com.lasterbergamot.balldontlie.domain.player.model.PlayerDTO;
import com.lasterbergamot.balldontlie.domain.player.service.PlayerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final RestTemplate restTemplate;

//    @PostConstruct
    @Override
    public void getAllPlayers() {
        PlayerDTO playerDTO = restTemplate.getForObject("https://www.balldontlie.io/api/v1/players/237", PlayerDTO.class);
        System.out.println(playerDTO);
    }
}
