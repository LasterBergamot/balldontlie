package com.lasterbergamot.balldontlie.client;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

class Throttle {
    private static final Random RANDOM = new Random();

    private final int capacity;
    private final Duration duration;
    private final LocalDateTime[] calls;
    private final AtomicInteger start;
    private final AtomicInteger end;

    public Throttle(int capacity, Duration duration) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be geq 0!");
        }
        this.capacity = capacity;
        this.duration = duration;
        this.calls = new LocalDateTime[capacity];
        this.start = new AtomicInteger(0);
        this.end = new AtomicInteger(0);
    }

    public synchronized void waitForCapacity() {
        if (!hasCapacity()) {
            long millis = Duration.between(LocalDateTime.now(), nextFreeUp()).toMillis();
            try {
                Thread.sleep(millis + RANDOM.nextInt(50));
            } catch (InterruptedException e) {

            }
        }
    }

    public synchronized boolean couldReserve() {
        if (hasCapacity()) {
            reserve();
            return true;
        }
        return false;
    }

    private boolean hasCapacity() {
        while (!isEmpty() && LocalDateTime.now().isAfter(nextFreeUp())) {
            freeUpFirst();
        }
        return !isFull();
    }

    private void reserve() {
        if (!isFull()) {
            calls[end.getAndIncrement() % capacity] = LocalDateTime.now();
        } else {
            throw new IllegalStateException("Full capacity");
        }
    }

    private void freeUpFirst() {
        if (!isEmpty()) {
            start.getAndIncrement();
        }
    }

    private boolean isEmpty() {
        return start.get() == end.get();
    }

    private boolean isFull() {
        return end.get() - start.get() >= capacity - 1;
    }

    private LocalDateTime getFirst() {
        return calls[start.get() % capacity];
    }

    private LocalDateTime nextFreeUp() {
        return getFirst().plus(duration);
    }
}
