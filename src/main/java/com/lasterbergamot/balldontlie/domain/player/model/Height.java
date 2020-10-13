package com.lasterbergamot.balldontlie.domain.player.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Height {

    @NonNull
    private final Integer feet;

    @NonNull
    private final Integer inches;

    public static Height convert(Integer feet, Integer inches) {
        return new Height(feet, inches);
    }

    /**
     * If this Height equals to the other Height - return 0.
     * If this Height is lesser than the other Height - return -1.
     * If this Height is larger than the other Height - return 1.
     * @param otherHeight
     * @return
     */
    public Integer compareTo(Height otherHeight) {
        return this.equals(otherHeight)
                ? 0
                : compareFeet(otherHeight.getFeet(), otherHeight.getInches());
    }

    private Integer compareFeet(Integer otherFeet, Integer otherInches) {
        return feet > otherFeet
                ? 1
                : (feet.equals(otherFeet) ? compareInches(otherInches) : -1);
    }

    private Integer compareInches(Integer otherInches) {
        return inches > otherInches
                ? 1
                : (inches.equals(otherInches) ? 0 : -1);
    }
}
