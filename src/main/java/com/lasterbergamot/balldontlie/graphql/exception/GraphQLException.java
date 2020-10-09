package com.lasterbergamot.balldontlie.graphql.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphQLException extends RuntimeException implements GraphQLError {

    public GraphQLException(String message) {
        super(message);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return new ArrayList<>();
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.ValidationError;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return Map.of("errorMessage", getMessage());
    }
}
