package com.lasterbergamot.balldontlie.database.model.game;

import com.lasterbergamot.balldontlie.database.model.team.Team;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Objects;

@Document(collection = "games")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Game {

    private Integer id;
    private LocalDate date;
    private Integer homeTeamScore;
    private Integer visitorTeamScore;
    private Integer season;
    private Integer period;
    private String status;
    private String time;
    private Boolean postseason;
    private Team homeTeam;
    private Team visitorTeam;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id.equals(game.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
