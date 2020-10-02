package com.lasterbergamot.balldontlie.database.model.team;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "team")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Team {

    @Id
    @Setter(AccessLevel.NONE)
    private Integer id;

    @NonNull
    private String abbreviation;

    @NonNull
    private String city;

    @NonNull
    private String conference;

    @NonNull
    private String division;

    @NonNull
    @Column(name = "full_name")
    private String fullName;

    @NonNull
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
