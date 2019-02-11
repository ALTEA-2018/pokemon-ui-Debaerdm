package com.miage.altea.tp.pokemon_ui.pokemonTypes.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PokemonTypeServiceIntegrationTest {

    @Autowired
    PokemonTypeService service;

    @Autowired
    RestTemplate restTemplate;

    @Test
    void serviceAndTemplateShouldNotBeNull(){
        assertNotNull(service);
        assertNotNull(restTemplate);
    }

}
