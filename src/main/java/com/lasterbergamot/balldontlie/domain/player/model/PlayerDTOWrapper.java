package com.lasterbergamot.balldontlie.domain.player.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lasterbergamot.balldontlie.domain.model.meta.Meta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_DATA;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_META;

@NoArgsConstructor
@Getter
@ToString
public class PlayerDTOWrapper {

    @JsonProperty(JSON_PROPERTY_DATA)
    private List<PlayerDTO> playerDTOs;

    @JsonProperty(JSON_PROPERTY_META)
    private Meta meta;
}
