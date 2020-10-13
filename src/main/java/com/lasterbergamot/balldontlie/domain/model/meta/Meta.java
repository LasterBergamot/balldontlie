package com.lasterbergamot.balldontlie.domain.model.meta;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_CURRENT_PAGE;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_NEXT_PAGE;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_PER_PAGE;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_TOTAL_COUNT;
import static com.lasterbergamot.balldontlie.util.Constants.JSON_PROPERTY_TOTAL_PAGES;

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
    public Meta(@JsonProperty(JSON_PROPERTY_TOTAL_PAGES) Integer totalPages, @JsonProperty(JSON_PROPERTY_CURRENT_PAGE) Integer currentPage,
                @JsonProperty(JSON_PROPERTY_NEXT_PAGE) Integer nextPage, @JsonProperty(JSON_PROPERTY_PER_PAGE) Integer perPage,
                @JsonProperty(JSON_PROPERTY_TOTAL_COUNT) Integer totalCount) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.nextPage = nextPage;
        this.perPage = perPage;
        this.totalCount = totalCount;
    }
}
