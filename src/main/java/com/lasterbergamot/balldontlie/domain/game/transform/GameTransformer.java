package com.lasterbergamot.balldontlie.domain.game.transform;

import com.lasterbergamot.balldontlie.database.model.game.Game;
import com.lasterbergamot.balldontlie.domain.game.model.GameDTO;
import com.lasterbergamot.balldontlie.domain.team.transform.TeamTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GameTransformer {

    private final TeamTransformer teamTransformer;

    public List<Game> transformGameDTOListToGameList(List<GameDTO> gameDTOList) {
        return gameDTOList.stream()
                .map(this::transformGameDTOToGame)
                .collect(Collectors.toUnmodifiableList());
    }

    private Game transformGameDTOToGame(GameDTO gameDTO) {
        return Game.builder()
                .id(gameDTO.getId())
                .date(gameDTO.getDate())
                .homeTeam(teamTransformer.transformTeamDTOToTeam(gameDTO.getHomeTeam()))
                .visitorTeam(teamTransformer.transformTeamDTOToTeam(gameDTO.getVisitorTeam()))
                .homeTeamScore(gameDTO.getHomeTeamScore())
                .visitorTeamScore(gameDTO.getVisitorTeamScore())
                .period(gameDTO.getPeriod())
                .postseason(gameDTO.getPostseason())
                .season(gameDTO.getSeason())
                .status(gameDTO.getStatus())
                .time(gameDTO.getTime())
                .build();
    }
}
