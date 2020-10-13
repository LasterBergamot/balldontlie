package com.lasterbergamot.balldontlie.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String SCALAR_TYPE_NAME_WEIGHT = "Weight";
    public static final String SCALAR_TYPE_DESCRIPTION_DESCRIBES_WEIGHT = "Describes weight";

    public static final String ERR_MSG_THIS_SHOULD_NOT_BE_PARSED = "This should not be parsed";
    public static final String ERR_MSG_RESTORING_INVALID_WEIGHT_OF = "Restoring invalid weight of {}";
    public static final String ERR_MSG_THE_TEAM_DTOWRAPPER_OBJECT_GOT_FROM_THE_API_WAS_NULL = "The TeamDTOWrapper object got from the API was null!";
    public static final String ERR_MSG_THE_GIVEN_DIVISION_IS_NOT_PART_OF_THE_GIVEN_CONFERENCE = "The given division is not part of the given conference!";
    public static final String ERR_MSG_THE_STATS_DTOWRAPPER_OBJECT_GOT_FROM_THE_API_WAS_NULL = "The StatsDTOWrapper object got from the API was null!";
    public static final String ERR_MSG_THE_GAME_DTOWRAPPER_GOT_FROM_THE_API_WAS_NULL = "The GameDTOWrapper got from the API was null!";
    public static final String ERR_MSG_THE_GIVEN_HOME_TEAM_DOES_NOT_EXIST_IN_THE_DATABASE = "The given home team does not exist in the database!";
    public static final String ERR_MSG_THE_GIVEN_VISITOR_TEAM_DOES_NOT_EXIST_IN_THE_DATABASE = "The given visitor team does not exist in the database!";
    public static final String ERR_MSG_THE_INCHES_VALUE_IS_PRESENT_WITHOUT_THE_FEET_VALUE = "The inches value is present without the feet value!";
    public static final String ERR_MSG_THE_GIVEN_S_VALUE_IS_NOT_VALID_VALID_VALUES_ARE_IN_THE_RANGE_OF_S = "The given %s value is not valid! Valid values are in the range of %s";
    public static final String ERR_MSG_THE_PLAYER_DTOWRAPPER_OBJECT_GOT_FROM_THE_API_WAS_NULL = "The PlayerDTOWrapper object got from the API was null!";

    public static final String ATTRIBUTE_FEET = "feet";
    public static final String ATTRIBUTE_INCHES = "inches";
    public static final String ATTRIBUTE_WEIGHT = "weight";

    public static final String WEIGHT_FORMAT_LBS = " lbs";

    public static final String KEY_ERROR_MESSAGE = "errorMessage";
    public static final String KEY_HOME_TEAM = "homeTeam";
    public static final String KEY_VISITOR_TEAM = "visitorTeam";

    public static final String URL_TEAM_BALLDONTLIE_API_PER_PAGE_100 = "https://www.balldontlie.io/api/v1/teams?per_page=100";
    public static final String URL_STATS_BALLDONTLIE_API_PER_PAGE_100_PAGE = "https://www.balldontlie.io/api/v1/stats?per_page=100&page=%d";
    public static final String URL_GAME_BALLDONTLIE_API_PER_PAGE_100_PAGE = "https://www.balldontlie.io/api/v1/games?per_page=100&page=%d";
    public static final String URL_PLAYERS_BALLDONTLIE_API_PER_PAGE_100_PAGE = "https://www.balldontlie.io/api/v1/players?per_page=100&page=%d";

    public static final String JSON_PROPERTY_DATA = "data";
    public static final String JSON_PROPERTY_META = "meta";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_ABBREVIATION = "abbreviation";
    public static final String JSON_PROPERTY_CITY = "city";
    public static final String JSON_PROPERTY_CONFERENCE = "conference";
    public static final String JSON_PROPERTY_DIVISION = "division";
    public static final String JSON_PROPERTY_FULL_NAME = "full_name";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_PLAYER = "player";
    public static final String JSON_PROPERTY_TEAM = "team";
    public static final String JSON_PROPERTY_GAME = "game";
    public static final String JSON_PROPERTY_MIN = "min";
    public static final String JSON_PROPERTY_PTS = "pts";
    public static final String JSON_PROPERTY_AST = "ast";
    public static final String JSON_PROPERTY_REB = "reb";
    public static final String JSON_PROPERTY_DREB = "dreb";
    public static final String JSON_PROPERTY_OREB = "oreb";
    public static final String JSON_PROPERTY_BLK = "blk";
    public static final String JSON_PROPERTY_STL = "stl";
    public static final String JSON_PROPERTY_TURNOVER = "turnover";
    public static final String JSON_PROPERTY_PF = "pf";
    public static final String JSON_PROPERTY_FGA = "fga";
    public static final String JSON_PROPERTY_FGM = "fgm";
    public static final String JSON_PROPERTY_FG_PCT = "fg_pct";
    public static final String JSON_PROPERTY_FG_3_A = "fg3a";
    public static final String JSON_PROPERTY_FG_3_M = "fg3m";
    public static final String JSON_PROPERTY_FG_3_PCT = "fg3_pct";
    public static final String JSON_PROPERTY_FTA = "fta";
    public static final String JSON_PROPERTY_FTM = "ftm";
    public static final String JSON_PROPERTY_FT_PCT = "ft_pct";
    public static final String JSON_PROPERTY_FIRST_NAME = "first_name";
    public static final String JSON_PROPERTY_LAST_NAME = "last_name";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_HEIGHT_FEET = "height_feet";
    public static final String JSON_PROPERTY_HEIGHT_INCHES = "height_inches";
    public static final String JSON_PROPERTY_WEIGHT_POUNDS = "weight_pounds";
    public static final String JSON_PROPERTY_TOTAL_PAGES = "total_pages";
    public static final String JSON_PROPERTY_CURRENT_PAGE = "current_page";
    public static final String JSON_PROPERTY_NEXT_PAGE = "next_page";
    public static final String JSON_PROPERTY_PER_PAGE = "per_page";
    public static final String JSON_PROPERTY_TOTAL_COUNT = "total_count";
    public static final String JSON_PROPERTY_DATE = "date";
    public static final String JSON_PROPERTY_HOME_TEAM_SCORE = "home_team_score";
    public static final String JSON_PROPERTY_VISITOR_TEAM_SCORE = "visitor_team_score";
    public static final String JSON_PROPERTY_SEASON = "season";
    public static final String JSON_PROPERTY_PERIOD = "period";
    public static final String JSON_PROPERTY_STATUS = "status";
    public static final String JSON_PROPERTY_TIME = "time";
    public static final String JSON_PROPERTY_POSTSEASON = "postseason";
    public static final String JSON_PROPERTY_HOME_TEAM = "home_team";
    public static final String JSON_PROPERTY_VISITOR_TEAM = "visitor_team";

    public static final String CONFIG_PROP_APP_CONFIG = "app-config";

    public static final String QUERY_SEQ_GAME_ID_NEXTVAL = "SELECT nextval('seq_game_id')";

    public static final String TABLE_TEAM = "team";
    public static final String TABLE_STATS = "stats";
    public static final String TABLE_PLAYER = "player";
    public static final String TABLE_GAME = "game";

    public static final String JOIN_COLUMN_PLAYER_ID = "player_id";
    public static final String JOIN_COLUMN_TEAM_ID = "team_id";
    public static final String JOIN_COLUMN_GAME_ID = "game_id";
    public static final String JOIN_COLUMN_HOME_TEAM_ID = "home_team_id";
    public static final String JOIN_COLUMN_VISITOR_TEAM_ID = "visitor_team_id";

    public static final String COLUMN_FULL_NAME = "full_name";
    public static final String COLUMN_DEFENSIVE_REBOUNDS = "defensive_rebounds";
    public static final String COLUMN_OFFENSIVE_REBOUNDS = "offensive_rebounds";
    public static final String COLUMN_PERSONAL_FOULS = "personal_fouls";
    public static final String COLUMN_FIELD_GOALS_ATTEMPTED = "field_goals_attempted";
    public static final String COLUMN_FIELD_GOALS_MADE = "field_goals_made";
    public static final String COLUMN_FIELD_GOAL_PERCENTAGE = "field_goal_percentage";
    public static final String COLUMN_THREE_POINTERS_ATTEMPTED = "three_pointers_attempted";
    public static final String COLUMN_THREE_POINTERS_MADE = "three_pointers_made";
    public static final String COLUMN_THREE_POINTER_PERCENTAGE = "three_pointer_percentage";
    public static final String COLUMN_FREE_THROWS_ATTEMPTED = "free_throws_attempted";
    public static final String COLUMN_FREE_THROWS_MADE = "free_throws_made";
    public static final String COLUMN_FREE_THROW_PERCENTAGE = "free_throw_percentage";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_PLAYER_POSITION = "player_position";
    public static final String COLUMN_HEIGHT_FEET = "height_feet";
    public static final String COLUMN_HEIGHT_INCHES = "height_inches";
    public static final String COLUMN_WEIGHT_POUNDS = "weight_pounds";
    public static final String COLUMN_DATE_OF_MATCH = "date_of_match";
    public static final String COLUMN_HOME_TEAM_SCORE = "home_team_score";
    public static final String COLUMN_VISITOR_TEAM_SCORE = "visitor_team_score";
    public static final String COLUMN_TIME_TILL_START = "time_till_start";
}
