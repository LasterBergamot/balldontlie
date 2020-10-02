package com.lasterbergamot.balldontlie.domain.player.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lasterbergamot.balldontlie.domain.team.model.TeamDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class PlayerDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String position;
    private Integer heightFeet;
    private Integer heightInches;
    private Integer weightPounds;
    private TeamDTO team;

    @JsonCreator
    public PlayerDTO(@JsonProperty("id") Integer id, @JsonProperty("first_name") String firstName,
                     @JsonProperty("last_name") String lastName, @JsonProperty("position") String position,
                     @JsonProperty("height_feet") Integer heightFeet, @JsonProperty("height_inches") Integer heightInches,
                     @JsonProperty("weight_pounds") Integer weightPounds, @JsonProperty("team") TeamDTO team) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.heightFeet = heightFeet;
        this.heightInches = heightInches;
        this.weightPounds = weightPounds;
        this.team = team;
    }
}
