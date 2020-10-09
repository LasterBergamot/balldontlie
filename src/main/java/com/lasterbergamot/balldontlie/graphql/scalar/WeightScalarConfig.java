package com.lasterbergamot.balldontlie.graphql.scalar;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeightScalarConfig {

    @Bean
    public GraphQLScalarType weightScalar() {
        return new GraphQLScalarType("Weight", "Describes weight", new Coercing<Weight, String>() {
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
                throw new CoercingParseValueException("This should not be parsed");
            }

            @Override
            public Weight parseLiteral(Object input) throws CoercingParseLiteralException {
                throw new CoercingParseValueException("This should not be parsed");
            }
        });
    }
}
