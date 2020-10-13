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

import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_FIRST_NAME;
import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_HEIGHT_FEET;
import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_HEIGHT_INCHES;
import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_LAST_NAME;
import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_PLAYER_POSITION;
import static com.lasterbergamot.balldontlie.util.Constants.COLUMN_WEIGHT_POUNDS;
import static com.lasterbergamot.balldontlie.util.Constants.JOIN_COLUMN_TEAM_ID;
import static com.lasterbergamot.balldontlie.util.Constants.TABLE_PLAYER;

@Entity
@Table(name = TABLE_PLAYER)
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
    @Column(name = COLUMN_FIRST_NAME)
    private String firstName;

    @NonNull
    @Column(name = COLUMN_LAST_NAME)
    private String lastName;

    @NonNull
    @Column(name = COLUMN_PLAYER_POSITION)
    private String position;

    @Column(name = COLUMN_HEIGHT_FEET)
    private Integer heightFeet;

    @Column(name = COLUMN_HEIGHT_INCHES)
    private Integer heightInches;

    @Column(name = COLUMN_WEIGHT_POUNDS)
    private Integer weightPounds;

    @NonNull
    @OneToOne
    @JoinColumn(name = JOIN_COLUMN_TEAM_ID)
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
