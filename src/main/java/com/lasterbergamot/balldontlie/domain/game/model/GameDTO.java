package com.lasterbergamot.balldontlie.domain.game.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lasterbergamot.balldontlie.domain.team.model.TeamDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_DATE;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_HOME_TEAM;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_HOME_TEAM_SCORE;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_ID;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_PERIOD;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_POSTSEASON;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_SEASON;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_STATUS;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_TIME;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_VISITOR_TEAM;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_VISITOR_TEAM_SCORE;

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
    public GameDTO(@JsonProperty(JSON_PROPERTY_ID) Integer id, @JsonProperty(JSON_PROPERTY_DATE) LocalDate date,
                   @JsonProperty(JSON_PROPERTY_HOME_TEAM_SCORE) Integer homeTeamScore, @JsonProperty(JSON_PROPERTY_VISITOR_TEAM_SCORE) Integer visitorTeamScore,
                   @JsonProperty(JSON_PROPERTY_SEASON) Integer season, @JsonProperty(JSON_PROPERTY_PERIOD) Integer period,
                   @JsonProperty(JSON_PROPERTY_STATUS) String status, @JsonProperty(JSON_PROPERTY_TIME) String time,
                   @JsonProperty(JSON_PROPERTY_POSTSEASON) Boolean postseason, @JsonProperty(JSON_PROPERTY_HOME_TEAM) TeamDTO homeTeam,
                   @JsonProperty(JSON_PROPERTY_VISITOR_TEAM) TeamDTO visitorTeam) {
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
