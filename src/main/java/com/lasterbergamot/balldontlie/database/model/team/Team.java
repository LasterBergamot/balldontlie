package com.lasterbergamot.balldontlie.database.model.team;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "teams")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Team {

    private Integer id;
    private String abbreviation;
    private String city;
    private String conference;
    private String division;
    private String fullName;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return id.equals(team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
