package com.lasterbergamot.balldontlie.database.model.stats;

import com.lasterbergamot.balldontlie.database.model.game.Game;
import com.lasterbergamot.balldontlie.database.model.player.Player;
import com.lasterbergamot.balldontlie.database.model.team.Team;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "stats")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Stats {

    private Integer id;
    private Player player; // the team is indicated with team_id -> get it from the db
    private Team team;
    private Game game; // the teams are indicated with ids -> get from the db
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
    private Integer fieldGoalAttempted;
    private Integer fieldGoalMade;
    private Double fieldGoalPercentage;
    private Integer threePointerAttempted;
    private Integer threePointerMade;
    private Double threePointerPercentage;
    private Integer freeThrowAttempted;
    private Integer freeThrowMade;
    private Double freeThrowPercentage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stats stats = (Stats) o;
        return id.equals(stats.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}