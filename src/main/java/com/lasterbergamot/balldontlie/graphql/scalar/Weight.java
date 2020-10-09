package com.lasterbergamot.balldontlie.graphql.scalar;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Weight {

    private int value;

    private Weight(int value) {
        this.value = value;
    }

    public String format() {
        return value + " lbs";
    }

    public static Weight restore(int value) {
        if (hasError(value)) {
            log.error("Restoring invalid weight of {}", value);
        }
        return new Weight(value);
    }

    private static boolean hasError(int value) {
        return value < 0;
    }

}
