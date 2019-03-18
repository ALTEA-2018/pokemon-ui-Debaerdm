package com.miage.altea.tp.pokemon_ui.trainers.service;

import com.miage.altea.tp.pokemon_ui.pokemonTypes.bo.PokemonType;
import com.miage.altea.tp.pokemon_ui.trainers.bo.Trainer;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerOpenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TrainersServiceIntegrationTest {
    @Autowired
    TrainerServiceImpl trainerService;

    @Mock
    RestTemplate restTemplate;

    @Value("${trainers.service.url}/trainers/{name}")
    String expectedUrl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        trainerService.setRestTemplate(restTemplate);

        var ash = new Trainer();
        ash.setName("ash");
        when(restTemplate.getForObject(expectedUrl, Trainer.class, "ash")).thenReturn(ash);
    }

    @Test
    void getTrainers_shouldCircuitBreaker() {
        var circuitBreaker = CircuitBreaker.of("trainers",
                CircuitBreakerConfig.custom().ringBufferSizeInClosedState(2).build());

        trainerService.setCircuitBreaker(circuitBreaker);

        assertEquals(CircuitBreaker.State.CLOSED, circuitBreaker.getState());

        for (int i = 0; i < 2; i++) {
            circuitBreaker.onError(0, new Exception());
        }

        assertEquals(CircuitBreaker.State.OPEN, circuitBreaker.getState());
        assertThrows(CircuitBreakerOpenException.class, () -> trainerService.getTrainer("Ash"));
        verifyZeroInteractions(restTemplate);
    }
}
