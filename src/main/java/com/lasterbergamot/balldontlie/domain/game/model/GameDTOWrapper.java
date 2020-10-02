package com.lasterbergamot.balldontlie.domain.game.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lasterbergamot.balldontlie.domain.model.meta.Meta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class GameDTOWrapper {

    @JsonProperty("data")
    private List<GameDTO> gameDTOs;

    @JsonProperty("meta")
    private Meta meta;
}
