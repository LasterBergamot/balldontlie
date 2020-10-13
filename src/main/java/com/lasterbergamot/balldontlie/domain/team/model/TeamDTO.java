package com.lasterbergamot.balldontlie.domain.team.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_ABBREVIATION;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_CITY;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_CONFERENCE;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_DIVISION;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_FULL_NAME;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_ID;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_NAME;

@NoArgsConstructor
@Getter
@ToString
public class TeamDTO {

    private Integer id;
    private String abbreviation;
    private String city;
    private String conference;
    private String division;
    private String fullName;
    private String name;

    @JsonCreator
    public TeamDTO(@JsonProperty(JSON_PROPERTY_ID) Integer id, @JsonProperty(JSON_PROPERTY_ABBREVIATION) String abbreviation,
                   @JsonProperty(JSON_PROPERTY_CITY) String city, @JsonProperty(JSON_PROPERTY_CONFERENCE) String conference,
                   @JsonProperty(JSON_PROPERTY_DIVISION) String division, @JsonProperty(JSON_PROPERTY_FULL_NAME) String fullName,
                   @JsonProperty(JSON_PROPERTY_NAME) String name) {
        this.id = id;
        this.abbreviation = abbreviation;
        this.city = city;
        this.conference = conference;
        this.division = division;
        this.fullName = fullName;
        this.name = name;
    }
}
