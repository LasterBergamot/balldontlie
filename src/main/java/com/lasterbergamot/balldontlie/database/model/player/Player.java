package com.lasterbergamot.balldontlie.database.model.player;

import com.lasterbergamot.balldontlie.database.model.averages.SeasonAverage;
import com.lasterbergamot.balldontlie.database.model.team.Team;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "player")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Player {

    @Id
    @Setter(AccessLevel.NONE)
    private Integer id;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_name")
    private String lastName;

    @NonNull
    @Column(name = "player_position")
    private String position;

    @NonNull
    @Column(name = "height_feet")
    private Integer heightFeet;

    @NonNull
    @Column(name = "height_inches")
    private Integer heightInches;

    @NonNull
    @Column(name = "weight_pounds")
    private Integer weightPounds;

    @NonNull
    @OneToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @NonNull
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "player")
    private List<SeasonAverage> seasonAverages;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id.equals(player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
