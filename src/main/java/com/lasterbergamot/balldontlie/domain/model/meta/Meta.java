package com.lasterbergamot.balldontlie.domain.model.meta;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class Meta {

    private Integer totalPages;
    private Integer currentPage;
    private Integer nextPage;
    private Integer perPage;
    private Integer totalCount;

    @JsonCreator
    public Meta(@JsonProperty("total_pages") Integer totalPages, @JsonProperty("current_page") Integer currentPage,
                @JsonProperty("next_page") Integer nextPage, @JsonProperty("per_page") Integer perPage,
                @JsonProperty("total_count") Integer totalCount) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.nextPage = nextPage;
        this.perPage = perPage;
        this.totalCount = totalCount;
    }
}
