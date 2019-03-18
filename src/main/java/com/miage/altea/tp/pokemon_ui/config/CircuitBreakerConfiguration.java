package com.miage.altea.tp.pokemon_ui.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfiguration {
    @Bean
    CircuitBreaker circuitBreaker() {
        var config = CircuitBreakerConfig.custom()
                .ringBufferSizeInClosedState(10)
                .failureRateThreshold(50.0f)
                .waitDurationInOpenState(Duration.ofSeconds(60))
                .build();
        return CircuitBreaker.of("trainers", config);
    }
}
