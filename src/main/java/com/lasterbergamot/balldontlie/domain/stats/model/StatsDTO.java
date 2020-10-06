package com.lasterbergamot.balldontlie.domain.stats.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lasterbergamot.balldontlie.database.model.game.Game;
import com.lasterbergamot.balldontlie.database.model.player.Player;
import com.lasterbergamot.balldontlie.database.model.team.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class StatsDTO {

    private Integer id;
    private Player player;
    private Team team;
    private Game game;
    private String minutes;
    private Integer points;
    private Integer assists;
    private Integer rebounds;
    private Integer defensiveRebounds;
    private Integer offensiveRebounds;
    private Integer blocks;
    private Integer steals;
    private Integer turnovers;
    private Integer personalFouls;
    private Integer fieldGoalsAttempted;
    private Integer fieldGoalsMade;
    private Double fieldGoalPercentage;
    private Integer threePointersAttempted;
    private Integer threePointersMade;
    private Double threePointerPercentage;
    private Integer freeThrowsAttempted;
    private Integer freeThrowsMade;
    private Double freeThrowPercentage;

    @JsonCreator
    public StatsDTO(@JsonProperty("id") Integer id, @JsonProperty("player") Player player,
                    @JsonProperty("team") Team team, @JsonProperty("game") Game game,
                    @JsonProperty("min") String minutes, @JsonProperty("pts") Integer points,
                    @JsonProperty("ast") Integer assists, @JsonProperty("reb") Integer rebounds,
                    @JsonProperty("dreb") Integer defensiveRebounds, @JsonProperty("oreb") Integer offensiveRebounds,
                    @JsonProperty("blk") Integer blocks, @JsonProperty("stl") Integer steals,
                    @JsonProperty("turnover") Integer turnovers, @JsonProperty("pf") Integer personalFouls,
                    @JsonProperty("fga") Integer fieldGoalsAttempted, @JsonProperty("fgm") Integer fieldGoalsMade,
                    @JsonProperty("fg_pct") Double fieldGoalPercentage, @JsonProperty("fg3a") Integer threePointersAttempted,
                    @JsonProperty("fg3m") Integer threePointersMade, @JsonProperty("fg3_pct") Double threePointerPercentage,
                    @JsonProperty("fta") Integer freeThrowsAttempted, @JsonProperty("ftm") Integer freeThrowsMade,
                    @JsonProperty("ft_pct") Double freeThrowPercentage) {
        this.id = id;
        this.player = player;
        this.team = team;
        this.game = game;
        this.minutes = minutes;
        this.points = points;
        this.assists = assists;
        this.rebounds = rebounds;
        this.defensiveRebounds = defensiveRebounds;
        this.offensiveRebounds = offensiveRebounds;
        this.blocks = blocks;
        this.steals = steals;
        this.turnovers = turnovers;
        this.personalFouls = personalFouls;
        this.fieldGoalsAttempted = fieldGoalsAttempted;
        this.fieldGoalsMade = fieldGoalsMade;
        this.fieldGoalPercentage = fieldGoalPercentage;
        this.threePointersAttempted = threePointersAttempted;
        this.threePointersMade = threePointersMade;
        this.threePointerPercentage = threePointerPercentage;
        this.freeThrowsAttempted = freeThrowsAttempted;
        this.freeThrowsMade = freeThrowsMade;
        this.freeThrowPercentage = freeThrowPercentage;
    }
}
