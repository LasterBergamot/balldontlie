package com.lasterbergamot.balldontlie.graphql.scalar;

import lombok.extern.slf4j.Slf4j;

import static com.lasterbergamot.balldontlie.util.Constants.ERR_MSG_RESTORING_INVALID_WEIGHT_OF;
import static com.lasterbergamot.balldontlie.util.Constants.WEIGHT_FORMAT_LBS;

@Slf4j
public class Weight {

    private final int value;

    private Weight(final int value) {
        this.value = value;
    }

    public String format() {
        return value + WEIGHT_FORMAT_LBS;
    }

    public static Weight restore(final int value) {
        if (hasError(value)) {
            log.error(ERR_MSG_RESTORING_INVALID_WEIGHT_OF, value);
        }
        return new Weight(value);
    }

    private static boolean hasError(final int value) {
        return value < 0;
    }

}
