package com.lasterbergamot.balldontlie.domain.stats.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lasterbergamot.balldontlie.domain.model.meta.Meta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class StatsDTOWrapper {

    @JsonProperty("data")
    private StatsDTO statsDTO;

    @JsonProperty("meta")
    private Meta meta;
}
