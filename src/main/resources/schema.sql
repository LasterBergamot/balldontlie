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
    height_feet     INTEGER,
    height_inches   INTEGER,
    weight_pounds   INTEGER,
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
    player_id                   INTEGER,
    team_id                     INTEGER,
    game_id                     INTEGER,
    minutes                     TEXT,
    points                      INTEGER,
    assists                     INTEGER,
    rebounds                    INTEGER,
    defensive_rebounds          INTEGER,
    offensive_rebounds          INTEGER,
    blocks                      INTEGER,
    steals                      INTEGER,
    turnovers                   INTEGER,
    personal_fouls              INTEGER,
    field_goals_attempted       INTEGER,
    field_goals_made            INTEGER,
    field_goal_percentage       DOUBLE PRECISION,
    three_pointers_attempted    INTEGER,
    three_pointers_made         INTEGER,
    three_pointer_percentage    DOUBLE PRECISION,
    free_throws_attempted       INTEGER,
    free_throws_made            INTEGER,
    free_throw_percentage       DOUBLE PRECISION
);