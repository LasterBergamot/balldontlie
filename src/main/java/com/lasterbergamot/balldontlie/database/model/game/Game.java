package com.lasterbergamot.balldontlie.database.model.game;

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
import java.time.LocalDate;
import java.util.Objects;

import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_DATE_OF_MATCH;
import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_HOME_TEAM_SCORE;
import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_TIME_TILL_START;
import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_VISITOR_TEAM_SCORE;
import static com.lasterbergamot.balldontlie.util.Constants.JOIN_COLUMN_HOME_TEAM_ID;
import static com.lasterbergamot.balldontlie.util.Constants.JOIN_COLUMN_VISITOR_TEAM_ID;
import static com.lasterbergamot.balldontlie.util.Constants.TABLE_GAME;

@Entity
@Table(name = TABLE_GAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Game {

    @Id
    @Setter(AccessLevel.NONE)
    private Integer id;

    @NonNull
    @Column(name = COLUMN_DATE_OF_MATCH)
    private LocalDate date;

    @NonNull
    @Column(name = COLUMN_HOME_TEAM_SCORE)
    private Integer homeTeamScore;

    @NonNull
    @Column(name = COLUMN_VISITOR_TEAM_SCORE)
    private Integer visitorTeamScore;

    @NonNull
    private Integer season;

    @NonNull
    private Integer period;

    @NonNull
    private String status;

    @NonNull
    @Column(name = COLUMN_TIME_TILL_START)
    private String time;

    @NonNull
    private Boolean postseason;

    @NonNull
    @OneToOne
    @JoinColumn(name = JOIN_COLUMN_HOME_TEAM_ID)
    private Team homeTeam;

    @NonNull
    @OneToOne
    @JoinColumn(name = JOIN_COLUMN_VISITOR_TEAM_ID)
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
