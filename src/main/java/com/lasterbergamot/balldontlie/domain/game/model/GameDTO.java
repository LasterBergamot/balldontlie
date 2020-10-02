package com.lasterbergamot.balldontlie.domain.game.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lasterbergamot.balldontlie.domain.team.model.TeamDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@ToString
public class GameDTO {

    private Integer id;
    private LocalDate date;
    private Integer homeTeamScore;
    private Integer visitorTeamScore;
    private Integer season;
    private Integer period;
    private String status;
    private String time;
    private Boolean postseason;
    private TeamDTO homeTeam;
    private TeamDTO visitorTeam;

    @JsonCreator
    public GameDTO(@JsonProperty("id") Integer id, @JsonProperty("date") LocalDate date,
                   @JsonProperty("home_team_score") Integer homeTeamScore, @JsonProperty("visitor_team_score") Integer visitorTeamScore,
                   @JsonProperty("season") Integer season, @JsonProperty("period") Integer period,
                   @JsonProperty("status") String status, @JsonProperty("time") String time,
                   @JsonProperty("postseason") Boolean postseason, @JsonProperty("home_team") TeamDTO homeTeam,
                   @JsonProperty("visitor_team") TeamDTO visitorTeam) {
        this.id = id;
        this.date = date;
        this.homeTeamScore = homeTeamScore;
        this.visitorTeamScore = visitorTeamScore;
        this.season = season;
        this.period = period;
        this.status = status;
        this.time = time;
        this.postseason = postseason;
        this.homeTeam = homeTeam;
        this.visitorTeam = visitorTeam;
    }
}
