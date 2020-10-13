package com.lasterbergamot.balldontlie.domain.player.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Height {

    private static final Comparator<Height> HEIGHT_COMPARATOR = Comparator.comparing(Height::getFeet).thenComparing(Height::getInches);

    @NonNull
    private final Integer feet;

    @NonNull
    private final Integer inches;

    public static Height convert(final Integer feet, final Integer inches) {
        return new Height(feet, inches);
    }

    public Integer compareTo(final Height otherHeight) {
        return HEIGHT_COMPARATOR.compare(this, otherHeight);
    }
}
