package com.lasterbergamot.balldontlie.domain.team.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    public TeamDTO(@JsonProperty("id") Integer id, @JsonProperty("abbreviation") String abbreviation,
                   @JsonProperty("city") String city, @JsonProperty("conference") String conference,
                   @JsonProperty("division") String division, @JsonProperty("full_name") String fullName,
                   @JsonProperty("name") String name) {
        this.id = id;
        this.abbreviation = abbreviation;
        this.city = city;
        this.conference = conference;
        this.division = division;
        this.fullName = fullName;
        this.name = name;
    }
}
