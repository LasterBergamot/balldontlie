package com.lasterbergamot.balldontlie.graphql.team.exception;

import com.lasterbergamot.balldontlie.graphql.exception.GraphQLException;

public class TeamQueryException extends GraphQLException {
    public TeamQueryException(String errorMessage) {
        super(errorMessage);
    }
}
