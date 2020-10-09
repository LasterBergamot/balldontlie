package com.lasterbergamot.balldontlie.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Weight {

    private static final Pattern FORMATTED_WEIGHT = Pattern.compile("^(?<value>\\d+) lb$");

    private int value;

    private Weight(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Weight should not be negative.");
        }
        this.value = value;
    }

    public static Weight from(String formattedValue) {
        Matcher matcher = FORMATTED_WEIGHT.matcher(formattedValue);
        if (matcher.matches()) {
            return new Weight(Integer.parseInt(matcher.group("value")));
        } else {
            throw new IllegalArgumentException(formattedValue + " is not a valid Weight.");
        }
    }

    public static Weight from(int value) {
        return new Weight(value);
    }

    public String format() {
        return value + " lb";
    }

}
