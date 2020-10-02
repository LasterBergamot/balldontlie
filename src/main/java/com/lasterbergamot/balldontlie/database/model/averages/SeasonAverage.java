package com.lasterbergamot.balldontlie.database.model.averages;


import com.lasterbergamot.balldontlie.database.model.player.Player;
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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "season_average")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SeasonAverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @NonNull
    @Column(name = "games_played")
    private Integer gamesPlayed;

    @NonNull
    private Integer season;

    @NonNull
    private String minutes;

    @NonNull
    private Double points;

    @NonNull
    private Double assists;

    @NonNull
    private Double rebounds;

    @NonNull
    @Column(name = "defensive_rebounds")
    private Double defensiveRebounds;

    @NonNull
    @Column(name = "offensive_rebounds")
    private Double offensiveRebounds;

    @NonNull
    private Double steals;

    @NonNull
    private Double blocks;

    @NonNull
    private Double turnovers;

    @NonNull
    @Column(name = "personal_fouls")
    private Double personalFouls;

    @NonNull
    @Column(name = "field_goals_made")
    private Double fieldGoalsMade;

    @NonNull
    @Column(name = "field_goals_attempted")
    private Double fieldGoalsAttempted;

    @NonNull
    @Column(name = "field_goal_percentage")
    private Double fieldGoalPercentage;

    @NonNull
    @Column(name = "three_pointers_made")
    private Double threePointersMade;

    @NonNull
    @Column(name = "three_pointers_attempted")
    private Double threePointersAttempted;

    @NonNull
    @Column(name = "three_pointer_percentage")
    private Double threePointerPercentage;

    @NonNull
    @Column(name = "free_throws_made")
    private Double freeThrowsMade;

    @NonNull
    @Column(name = "free_throws_attempted")
    private Double freeThrowsAttempted;

    @NonNull
    @Column(name = "free_throw_percentage")
    private Double freeThrowPercentage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeasonAverage that = (SeasonAverage) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
