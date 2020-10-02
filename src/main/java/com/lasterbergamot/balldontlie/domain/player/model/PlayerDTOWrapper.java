package com.lasterbergamot.balldontlie.domain.player.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lasterbergamot.balldontlie.domain.model.meta.Meta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class PlayerDTOWrapper {

    @JsonProperty("data")
    private List<PlayerDTO> playerDTOs;

    @JsonProperty("meta")
    private Meta meta;
}
