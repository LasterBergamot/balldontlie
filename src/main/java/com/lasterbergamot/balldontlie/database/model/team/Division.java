package com.lasterbergamot.balldontlie.database.model.team;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Division {
    Pacific(Conference.West),
    Southeast(Conference.East),
    Southwest(Conference.West),
    Atlantic(Conference.East),
    Northwest(Conference.West),
    Central(Conference.East);

    private final Conference conference;
}
