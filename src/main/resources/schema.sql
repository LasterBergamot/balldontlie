CREATE TABLE IF NOT EXISTS teams (
    id              INTEGER PRIMARY KEY,
    abbreviation    TEXT    NOT NULL,
    city            TEXT    NOT NULL,
    conference      TEXT    NOT NULL,
    division        TEXT    NOT NULL,
    full_name       TEXT    NOT NULL,
    name            TEXT    NOT NULL,
);

CREATE TABLE IF NOT EXISTS players (
    id              INTEGER PRIMARY KEY,
    first_name      TEXT    NOT NULL,
    last_name       TEXT    NOT NULL,
    player_position TEXT    NOT NULL,
    height_feet     INTEGER NOT NULL,
    height_inches   INTEGER NOT NULL,
    weight_pounds   INTEGER NOT NULL,
    team_id         INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS games (
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