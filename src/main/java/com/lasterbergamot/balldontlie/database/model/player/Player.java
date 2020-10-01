package com.lasterbergamot.balldontlie.database.model.player;

import com.lasterbergamot.balldontlie.database.model.team.Team;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "players")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Player {

    private Integer id;
    private String firstName;
    private String lastName;
    private String position;
    private Integer heightFeet;
    private Integer heightInches;
    private Integer weightPounds;
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
}
