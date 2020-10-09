package com.lasterbergamot.balldontlie.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class ThrottledRestTemplate {

    private final RestTemplate restTemplate;
    private final Throttle throttle = new Throttle(55, Duration.ofMinutes(1));

    public <T> T getForObject(String url, Class<T> clazz) {
        while (!throttle.couldReserve()) {
            throttle.waitForCapacity();
        }
        return restTemplate.getForObject(url, clazz);
    }
}
