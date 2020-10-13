package com.lasterbergamot.balldontlie.domain.stats.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lasterbergamot.balldontlie.database.model.game.Game;
import com.lasterbergamot.balldontlie.database.model.player.Player;
import com.lasterbergamot.balldontlie.database.model.team.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_AST;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_BLK;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_DREB;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_FGA;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_FGM;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_FG_3_A;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_FG_3_M;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_FG_3_PCT;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_FG_PCT;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_FTA;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_FTM;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_FT_PCT;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_GAME;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_ID;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_MIN;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_OREB;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_PF;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_PLAYER;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_PTS;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_REB;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_STL;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_TEAM;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_TURNOVER;

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
    public StatsDTO(@JsonProperty(JSON_PROPERTY_ID) Integer id, @JsonProperty(JSON_PROPERTY_PLAYER) Player player,
                    @JsonProperty(JSON_PROPERTY_TEAM) Team team, @JsonProperty(JSON_PROPERTY_GAME) Game game,
                    @JsonProperty(JSON_PROPERTY_MIN) String minutes, @JsonProperty(JSON_PROPERTY_PTS) Integer points,
                    @JsonProperty(JSON_PROPERTY_AST) Integer assists, @JsonProperty(JSON_PROPERTY_REB) Integer rebounds,
                    @JsonProperty(JSON_PROPERTY_DREB) Integer defensiveRebounds, @JsonProperty(JSON_PROPERTY_OREB) Integer offensiveRebounds,
                    @JsonProperty(JSON_PROPERTY_BLK) Integer blocks, @JsonProperty(JSON_PROPERTY_STL) Integer steals,
                    @JsonProperty(JSON_PROPERTY_TURNOVER) Integer turnovers, @JsonProperty(JSON_PROPERTY_PF) Integer personalFouls,
                    @JsonProperty(JSON_PROPERTY_FGA) Integer fieldGoalsAttempted, @JsonProperty(JSON_PROPERTY_FGM) Integer fieldGoalsMade,
                    @JsonProperty(JSON_PROPERTY_FG_PCT) Double fieldGoalPercentage, @JsonProperty(JSON_PROPERTY_FG_3_A) Integer threePointersAttempted,
                    @JsonProperty(JSON_PROPERTY_FG_3_M) Integer threePointersMade, @JsonProperty(JSON_PROPERTY_FG_3_PCT) Double threePointerPercentage,
                    @JsonProperty(JSON_PROPERTY_FTA) Integer freeThrowsAttempted, @JsonProperty(JSON_PROPERTY_FTM) Integer freeThrowsMade,
                    @JsonProperty(JSON_PROPERTY_FT_PCT) Double freeThrowPercentage) {
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
