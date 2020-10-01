package com.lasterbergamot.balldontlie.database.model.averages;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "seasonAverages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SeasonAverages {

    @Id
    private String id;
    private Integer playerId;
    private Integer gamesPlayed;
    private Integer season;
    private String minutes;
    private Double points;
    private Double assists;
    private Double rebounds;
    private Double defensiveRebounds;
    private Double offensiveRebounds;
    private Double steals;
    private Double blocks;
    private Double turnovers;
    private Double personalFouls;
    private Double fieldGoalsMade;
    private Double fieldGoalsAttempted;
    private Double fieldGoalPercentage;
    private Double threePointersMade;
    private Double threePointersAttempted;
    private Double threePointerPercentage;
    private Double freeThrowsMade;
    private Double freeThroesAttempted;
    private Double freeThrowPercentage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeasonAverages that = (SeasonAverages) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
