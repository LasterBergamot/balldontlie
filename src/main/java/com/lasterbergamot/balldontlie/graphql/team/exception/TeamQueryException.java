package com.lasterbergamot.balldontlie.graphql.team.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;
import java.util.Map;

public class TeamQueryException extends RuntimeException implements GraphQLError {

    public TeamQueryException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public Map<String, Object> getExtensions() {
        return Map.of("errorMessage", getMessage());
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.ValidationError;
    }
}
