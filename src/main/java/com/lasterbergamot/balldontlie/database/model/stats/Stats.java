package com.lasterbergamot.balldontlie.database.model.stats;

import com.lasterbergamot.balldontlie.database.model.game.Game;
import com.lasterbergamot.balldontlie.database.model.player.Player;
import com.lasterbergamot.balldontlie.database.model.team.Team;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;


@Entity
@Table(name = "stats")
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

    @NonNull
    @OneToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @NonNull
    @OneToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @NonNull
    @OneToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @NonNull
    private String minutes;

    @NonNull
    private Integer points;

    @NonNull
    private Integer assists;

    @NonNull
    private Integer rebounds;

    @NonNull
    @Column(name = "defensive_rebounds")
    private Integer defensiveRebounds;

    @NonNull
    @Column(name = "offensive_rebounds")
    private Integer offensiveRebounds;

    @NonNull
    private Integer blocks;

    @NonNull
    private Integer steals;

    @NonNull
    private Integer turnovers;

    @NonNull
    @Column(name = "personal_fouls")
    private Integer personalFouls;

    @NonNull
    @Column(name = "field_goals_attempted")
    private Integer fieldGoalsAttempted;

    @NonNull
    @Column(name = "field_goals_made")
    private Integer fieldGoalsMade;

    @NonNull
    @Column(name = "field_goal_percentage")
    private Double fieldGoalPercentage;

    @NonNull
    @Column(name = "three_pointers_attempted")
    private Integer threePointersAttempted;

    @NonNull
    @Column(name = "three_pointers_made")
    private Integer threePointersMade;

    @NonNull
    @Column(name = "three_pointer_percentage")
    private Double threePointerPercentage;

    @NonNull
    @Column(name = "free_throws_attempted")
    private Integer freeThrowsAttempted;

    @NonNull
    @Column(name = "free_throws_made")
    private Integer freeThrowsMade;

    @NonNull
    @Column(name = "free_throw_percentage")
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