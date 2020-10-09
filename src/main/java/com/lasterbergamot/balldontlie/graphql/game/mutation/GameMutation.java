package com.lasterbergamot.balldontlie.graphql.game.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.lasterbergamot.balldontlie.database.model.game.Game;
import com.lasterbergamot.balldontlie.database.model.team.Team;
import com.lasterbergamot.balldontlie.domain.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameMutation implements GraphQLMutationResolver {

    private final GameService gameService;

    public Game createGame(final String date,
                           final Integer homeTeamScore, final Integer visitorTeamScore,
                           final Integer season, final Integer period,
                           final String status, final String time,
                           final Boolean postseason, final Integer homeTeamId,
                           final Integer visitorTeamId) {
        return gameService.createGame(date, homeTeamScore, visitorTeamScore, season, period, status, time, postseason, homeTeamId, visitorTeamId);
    }
}
