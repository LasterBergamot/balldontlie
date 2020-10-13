package com.lasterbergamot.balldontlie.database.model.team;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_FULL_NAME;
import static com.lasterbergamot.balldontlie.util.Constants.TABLE_TEAM;

@Entity
@Table(name = TABLE_TEAM)
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
    @Enumerated(EnumType.STRING)
    private Conference conference;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Division division;

    @NonNull
    @Column(name = COLUMN_FULL_NAME)
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
