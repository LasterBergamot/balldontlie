package com.lasterbergamot.balldontlie.graphql.game.exception;

import com.lasterbergamot.balldontlie.graphql.exception.GraphQLException;

public class GameMutationException extends GraphQLException {
    public GameMutationException(String message) { super(message); }
}
