package com.lasterbergamot.balldontlie.graphql.player.exception;

import com.lasterbergamot.balldontlie.graphql.exception.GraphQLException;

public class PlayerQueryException extends GraphQLException {
    public PlayerQueryException(String message) { super(message); }
}
