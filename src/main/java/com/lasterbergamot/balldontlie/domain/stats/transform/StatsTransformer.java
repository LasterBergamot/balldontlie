package com.lasterbergamot.balldontlie.domain.stats.transform;

import com.lasterbergamot.balldontlie.database.model.stats.Stats;
import com.lasterbergamot.balldontlie.domain.stats.model.StatsDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatsTransformer {

    public List<Stats> transformStatsDTOListToStatsList(List<StatsDTO> statsDTOList) {
        return statsDTOList
                .stream()
                .map(this::transformStatsDTOToStats)
                .collect(Collectors.toList());
    }

    private Stats transformStatsDTOToStats(StatsDTO statsDTO) {
        return Stats.builder()
                .assists(statsDTO.getAssists())
                .blocks(statsDTO.getBlocks())
                .defensiveRebounds(statsDTO.getDefensiveRebounds())
                .fieldGoalPercentage(statsDTO.getFieldGoalPercentage())
                .fieldGoalsAttempted(statsDTO.getFieldGoalsAttempted())
                .fieldGoalsMade(statsDTO.getFieldGoalsMade())
                .freeThrowPercentage(statsDTO.getFreeThrowPercentage())
                .freeThrowsAttempted(statsDTO.getFreeThrowsAttempted())
                .freeThrowsMade(statsDTO.getFreeThrowsMade())
                .game(statsDTO.getGame())
                .id(statsDTO.getId())
                .minutes(statsDTO.getMinutes())
                .offensiveRebounds(statsDTO.getOffensiveRebounds())
                .personalFouls(statsDTO.getPersonalFouls())
                .player(statsDTO.getPlayer())
                .points(statsDTO.getPoints())
                .rebounds(statsDTO.getRebounds())
                .steals(statsDTO.getSteals())
                .team(statsDTO.getTeam())
                .threePointerPercentage(statsDTO.getThreePointerPercentage())
                .threePointersAttempted(statsDTO.getThreePointersAttempted())
                .threePointersMade(statsDTO.getThreePointersMade())
                .turnovers(statsDTO.getTurnovers())
                .build();
    }
}
