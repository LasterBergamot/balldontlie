package com.lasterbergamot.balldontlie.database.model.player;

import com.lasterbergamot.balldontlie.database.model.team.Team;
import com.lasterbergamot.balldontlie.graphql.scalar.Weight;
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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Optional;

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

    @Column(name = "height_feet")
    private Integer heightFeet;

    @Column(name = "height_inches")
    private Integer heightInches;

    @Column(name = "weight_pounds")
    private Integer weightPounds;

    @NonNull
    @OneToOne
    @JoinColumn(name = "team_id")
    private Team team;

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

    public Weight getWeight() {
        return Weight.restore(Optional.ofNullable(weightPounds).orElse(0));
    }
}
