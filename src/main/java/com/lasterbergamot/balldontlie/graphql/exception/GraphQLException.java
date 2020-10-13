package com.lasterbergamot.balldontlie.graphql.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.lasterbergamot.balldontlie.util.Constants.KEY_ERROR_MESSAGE;

public class GraphQLException extends RuntimeException implements GraphQLError {

    public GraphQLException(final String message) {
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
        return Map.of(KEY_ERROR_MESSAGE, getMessage());
    }
}
