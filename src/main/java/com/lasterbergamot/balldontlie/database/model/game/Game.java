package com.lasterbergamot.balldontlie.database.model.game;

import com.lasterbergamot.balldontlie.database.model.team.Team;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "game")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Game {

    @Id
    private Integer id;

    @NonNull
    @Column(name = "date_of_match")
    private LocalDate date;

    @NonNull
    @Column(name = "home_team_score")
    private Integer homeTeamScore;

    @NonNull
    @Column(name = "visitor_team_score")
    private Integer visitorTeamScore;

    @NonNull
    private Integer season;

    @NonNull
    private Integer period;

    @NonNull
    private String status;

    @NonNull
    @Column(name = "time_till_start")
    private String time;

    @NonNull
    private Boolean postseason;

    @NonNull
    @OneToOne
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @NonNull
    @OneToOne
    @JoinColumn(name = "visitor_team_id")
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
