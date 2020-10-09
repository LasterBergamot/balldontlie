package com.lasterbergamot.balldontlie.client;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

class ThrottleTest {

    private static final int EXECUTIONS = 160;
    private static final int LIMIT = 40;
    private static final Duration DURATION = Duration.ofSeconds(1);
    private static final Duration ACCEPTED_TIME_DIFFERENCE = Duration.ofMillis(990);

    private Throttle throttle;

    @Test
    void shouldNotAllowManyRequests() {
        throttle = new Throttle(LIMIT, DURATION);

        List<LocalDateTime> result = new LinkedList<>();
        for (int i = 0; i < EXECUTIONS; i++) {
            new Thread(() -> recordExecutionTime(result)).start();
        }

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
        }

        for (int i = 0; i < EXECUTIONS - LIMIT; i++) {
            if (result.get(i).plus(ACCEPTED_TIME_DIFFERENCE).isAfter(result.get(i + LIMIT))) {
                fail("Not enough time between " + result.get(i) + " and " + result.get(i + LIMIT) + ". Index of first: " + i);
            }
        }
    }

    private void recordExecutionTime(List<LocalDateTime> executionTimes) {
        while (!throttle.couldReserve()) {
            throttle.waitForCapacity();
        }
        executionTimes.add(LocalDateTime.now());
    }

}
