package com.lasterbergamot.balldontlie.graphql.scalar;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.lasterbergamot.balldontlie.util.Constants.ERR_MSG_THIS_SHOULD_NOT_BE_PARSED;
import static com.lasterbergamot.balldontlie.util.Constants.SCALAR_TYPE_DESCRIPTION_DESCRIBES_WEIGHT;
import static com.lasterbergamot.balldontlie.util.Constants.SCALAR_TYPE_NAME_WEIGHT;

@Configuration
public class WeightScalarConfig {

    @Bean
    public GraphQLScalarType weightScalar() {
        return new GraphQLScalarType(SCALAR_TYPE_NAME_WEIGHT, SCALAR_TYPE_DESCRIPTION_DESCRIBES_WEIGHT, new Coercing<Weight, String>() {
            @Override
            public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
                try {
                    return ((Weight) dataFetcherResult).format();
                } catch (RuntimeException e) {
                    throw new CoercingSerializeException(e);
                }
            }

            @Override
            public Weight parseValue(Object input) throws CoercingParseValueException {
                throw new CoercingParseValueException(ERR_MSG_THIS_SHOULD_NOT_BE_PARSED);
            }

            @Override
            public Weight parseLiteral(Object input) throws CoercingParseLiteralException {
                throw new CoercingParseValueException(ERR_MSG_THIS_SHOULD_NOT_BE_PARSED);
            }
        });
    }
}
