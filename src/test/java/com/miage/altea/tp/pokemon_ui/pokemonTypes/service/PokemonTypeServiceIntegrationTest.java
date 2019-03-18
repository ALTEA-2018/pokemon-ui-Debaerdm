package com.miage.altea.tp.pokemon_ui.pokemonTypes.service;

import com.miage.altea.tp.pokemon_ui.pokemonTypes.bo.PokemonType;
import io.github.resilience4j.retry.Retry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class PokemonTypeServiceIntegrationTest {

    @Autowired
    PokemonTypeServiceImpl pokemonTypeService;

    @Mock
    RestTemplate restTemplate;

    @Value("${pokemonType.service.url}/pokemon-types")
    String expectedUrl;

    @Autowired
    CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        pokemonTypeService.setRestTemplate(restTemplate);

        var pikachu = new PokemonType();
        pikachu.setId(25);
        pikachu.setName("Pikachu");
        when(restTemplate.getForObject(expectedUrl, PokemonType.class, 25)).thenReturn(pikachu);
    }

    @Test
    void getPokemonType_shouldUseCache() {
        pokemonTypeService.getPokemonByTypeId(25);

        // rest template should have been called once
        verify(restTemplate).getForObject(expectedUrl, PokemonType.class, 25);

        pokemonTypeService.getPokemonByTypeId(25);

        // rest template should not be called anymore because result is in cache !
        verifyNoMoreInteractions(restTemplate);

        // one result should be in cache !
        var cachedValue = cacheManager.getCache("pokemon-types").get(25).get();
        assertNotNull(cachedValue);
        assertEquals(PokemonType.class, cachedValue.getClass());
        assertEquals("Pikachu", ((PokemonType)cachedValue).getName());
    }

    @Test
    void getPokemonType_shouldRetry() {
        var retry = Retry.ofDefaults("pokemon-types");
        pokemonTypeService.setRetry(retry);

        var pikachu = new PokemonType();
        pikachu.setId(25);
        pikachu.setName("Pikachu");

        when(restTemplate.getForObject(expectedUrl, PokemonType.class, 25))
                .thenThrow(new RestClientException("500"))
                .thenThrow(new RestClientException("500"))
                .thenReturn(pikachu);

        var pokemonType = pokemonTypeService.getPokemonByTypeId(25);

        verify(restTemplate, times(3)).getForObject(expectedUrl, PokemonType.class, 25);
        assertEquals("Pikachu", pokemonType.getName());
    }

}
