package com.lasterbergamot.balldontlie.domain.team.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class TeamDTOWrapper {

    @JsonProperty("data")
    private List<TeamDTO> teamDTOs;

    @JsonProperty("meta")
    private Meta meta;
}
