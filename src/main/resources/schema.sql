CREATE TABLE IF NOT EXISTS team (
    id              INTEGER PRIMARY KEY,
    abbreviation    TEXT    NOT NULL,
    city            TEXT    NOT NULL,
    conference      TEXT    NOT NULL,
    division        TEXT    NOT NULL,
    full_name       TEXT    NOT NULL,
    name            TEXT    NOT NULL
);

CREATE TABLE IF NOT EXISTS player (
    id              INTEGER PRIMARY KEY,
    first_name      TEXT    NOT NULL,
    last_name       TEXT    NOT NULL,
    player_position TEXT    NOT NULL,
    height_feet     INTEGER NOT NULL,
    height_inches   INTEGER NOT NULL,
    weight_pounds   INTEGER NOT NULL,
    team_id         INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS game (
    id                  INTEGER PRIMARY KEY,
    date_of_match       DATE    NOT NULL,
    home_team_score     INTEGER NOT NULL,
    visitor_team_score  INTEGER NOT NULL,
    season              INTEGER NOT NULL,
    period              INTEGER NOT NULL,
    status              TEXT    NOT NULL,
    time_till_start     TEXT    NOT NULL,
    postseason          BOOLEAN NOT NULL,
    home_team_id        INTEGER NOT NULL,
    visitor_team_id     INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS stats (
    id                          INTEGER NOT NULL,
    player_id                   INTEGER NOT NULL,
    team_id                     INTEGER NOT NULL,
    game_id                     INTEGER NOT NULL,
    minutes                     TEXT    NOT NULL,
    points                      INTEGER NOT NULL,
    assists                     INTEGER NOT NULL,
    rebounds                    INTEGER NOT NULL,
    defensive_rebounds          INTEGER NOT NULL,
    offensive_rebounds          INTEGER NOT NULL,
    blocks                      INTEGER NOT NULL,
    steals                      INTEGER NOT NULL,
    turnovers                   INTEGER             NOT NULL,
    personal_fouls              INTEGER             NOT NULL,
    field_goals_attempted       INTEGER             NOT NULL,
    field_goals_made            INTEGER             NOT NULL,
    field_goal_percentage       DOUBLE PRECISION    NOT NULL,
    three_pointers_attempted    INTEGER NOT NULL,
    three_pointers_made         INTEGER NOT NULL,
    three_pointer_percentage    DOUBLE PRECISION  NOT NULL,
    free_throws_attempted       INTEGER NOT NULL,
    free_throws_made            INTEGER NOT NULL,
    free_throw_percentage       DOUBLE PRECISION  NOT NULL
);

CREATE TABLE IF NOT EXISTS season_average (
    id                          INTEGER GENERATED ALWAYS AS IDENTITY    PRIMARY KEY,
    player_id                   INTEGER                                 NOT NULL,
    games_played                INTEGER                                 NOT NULL,
    season                      INTEGER                                 NOT NULL,
    minutes                     TEXT                                    NOT NULL,
    points                      DOUBLE PRECISION                                  NOT NULL,
    assists                     DOUBLE PRECISION                                  NOT NULL,
    rebounds                    DOUBLE PRECISION                                  NOT NULL,
    defensive_rebounds          DOUBLE PRECISION                                  NOT NULL,
    offensive_rebounds          DOUBLE PRECISION                                  NOT NULL,
    steals                      DOUBLE PRECISION                                  NOT NULL,
    blocks                      DOUBLE PRECISION                                  NOT NULL,
    turnovers                   DOUBLE PRECISION                                  NOT NULL,
    personal_fouls              DOUBLE PRECISION                                  NOT NULL,
    field_goals_made            DOUBLE PRECISION                                  NOT NULL,
    field_goals_attempted       DOUBLE PRECISION                                  NOT NULL,
    field_goal_percentage       DOUBLE PRECISION                                  NOT NULL,
    three_pointers_made         DOUBLE PRECISION                                  NOT NULL,
    three_pointers_attempted    DOUBLE PRECISION                                  NOT NULL,
    three_pointer_percentage    DOUBLE PRECISION                                  NOT NULL,
    free_throws_made            DOUBLE PRECISION                                  NOT NULL,
    free_throws_attempted       DOUBLE PRECISION                                  NOT NULL,
    free_throw_percentage       DOUBLE PRECISION                                  NOT NULL
);