package com.lasterbergamot.balldontlie.domain.game.service.impl;

import com.lasterbergamot.balldontlie.database.repository.game.GameRepository;
import com.lasterbergamot.balldontlie.domain.game.model.GameDTOWrapper;
import com.lasterbergamot.balldontlie.domain.game.service.GameService;
import com.lasterbergamot.balldontlie.domain.game.transform.GameTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameTransformer gameTransformer;
    private final RestTemplate restTemplate;

    @Override
    public void getAllGamesFromBalldontlieAPI() {
        GameDTOWrapper gameDTOWrapper = restTemplate
                .getForObject(String.format("https://www.balldontlie.io/api/v1/games?per_page=100?page=%d", 1), GameDTOWrapper.class);

        Optional.ofNullable(gameDTOWrapper)
                .ifPresentOrElse(this::handlePossibleNewGames, () -> log.error("The GameDTOWrapper got from the API was null!"));
    }

    private void handlePossibleNewGames(GameDTOWrapper gameDTOWrapper) {

    }
}
