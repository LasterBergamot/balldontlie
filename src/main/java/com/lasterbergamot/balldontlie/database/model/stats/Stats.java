package com.lasterbergamot.balldontlie.database.model.stats;

import com.lasterbergamot.balldontlie.database.model.game.Game;
import com.lasterbergamot.balldontlie.database.model.player.Player;
import com.lasterbergamot.balldontlie.database.model.team.Team;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_DEFENSIVE_REBOUNDS;
import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_FIELD_GOALS_ATTEMPTED;
import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_FIELD_GOALS_MADE;
import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_FIELD_GOAL_PERCENTAGE;
import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_FREE_THROWS_ATTEMPTED;
import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_FREE_THROWS_MADE;
import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_FREE_THROW_PERCENTAGE;
import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_OFFENSIVE_REBOUNDS;
import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_PERSONAL_FOULS;
import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_THREE_POINTERS_ATTEMPTED;
import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_THREE_POINTERS_MADE;
import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_THREE_POINTER_PERCENTAGE;
import static com.lasterbergamot.balldontlie.util.Constants.JOIN_COLUMN_GAME_ID;
import static com.lasterbergamot.balldontlie.util.Constants.JOIN_COLUMN_PLAYER_ID;
import static com.lasterbergamot.balldontlie.util.Constants.JOIN_COLUMN_TEAM_ID;
import static com.lasterbergamot.balldontlie.util.Constants.TABLE_STATS;


@Entity
@Table(name = TABLE_STATS)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Stats {

    @Id
    @Setter(AccessLevel.NONE)
    private Integer id;

    @OneToOne
    @JoinColumn(name = JOIN_COLUMN_PLAYER_ID)
    private Player player;

    @OneToOne
    @JoinColumn(name = JOIN_COLUMN_TEAM_ID)
    private Team team;

    @OneToOne
    @JoinColumn(name = JOIN_COLUMN_GAME_ID)
    private Game game;

    private String minutes;

    private Integer points;

    private Integer assists;

    private Integer rebounds;

    @Column(name = COLUMN_DEFENSIVE_REBOUNDS)
    private Integer defensiveRebounds;

    @Column(name = COLUMN_OFFENSIVE_REBOUNDS)
    private Integer offensiveRebounds;

    private Integer blocks;

    private Integer steals;

    private Integer turnovers;

    @Column(name = COLUMN_PERSONAL_FOULS)
    private Integer personalFouls;

    @Column(name = COLUMN_FIELD_GOALS_ATTEMPTED)
    private Integer fieldGoalsAttempted;

    @Column(name = COLUMN_FIELD_GOALS_MADE)
    private Integer fieldGoalsMade;

    @Column(name = COLUMN_FIELD_GOAL_PERCENTAGE)
    private Double fieldGoalPercentage;

    @Column(name = COLUMN_THREE_POINTERS_ATTEMPTED)
    private Integer threePointersAttempted;

    @Column(name = COLUMN_THREE_POINTERS_MADE)
    private Integer threePointersMade;

    @Column(name = COLUMN_THREE_POINTER_PERCENTAGE)
    private Double threePointerPercentage;

    @Column(name = COLUMN_FREE_THROWS_ATTEMPTED)
    private Integer freeThrowsAttempted;

    @Column(name = COLUMN_FREE_THROWS_MADE)
    private Integer freeThrowsMade;

    @Column(name = COLUMN_FREE_THROW_PERCENTAGE)
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