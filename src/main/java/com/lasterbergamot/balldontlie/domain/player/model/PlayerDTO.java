package com.lasterbergamot.balldontlie.domain.player.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lasterbergamot.balldontlie.domain.team.model.TeamDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_FIRST_NAME;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_HEIGHT_FEET;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_HEIGHT_INCHES;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_ID;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_LAST_NAME;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_POSITION;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_TEAM;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_WEIGHT_POUNDS;

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
    public PlayerDTO(@JsonProperty(JSON_PROPERTY_ID) Integer id, @JsonProperty(JSON_PROPERTY_FIRST_NAME) String firstName,
                     @JsonProperty(JSON_PROPERTY_LAST_NAME) String lastName, @JsonProperty(JSON_PROPERTY_POSITION) String position,
                     @JsonProperty(JSON_PROPERTY_HEIGHT_FEET) Integer heightFeet, @JsonProperty(JSON_PROPERTY_HEIGHT_INCHES) Integer heightInches,
                     @JsonProperty(JSON_PROPERTY_WEIGHT_POUNDS) Integer weightPounds, @JsonProperty(JSON_PROPERTY_TEAM) TeamDTO team) {
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
