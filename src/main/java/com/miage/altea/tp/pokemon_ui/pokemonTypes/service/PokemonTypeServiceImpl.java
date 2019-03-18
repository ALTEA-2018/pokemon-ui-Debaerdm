package com.miage.altea.tp.pokemon_ui.pokemonTypes.service;

import com.miage.altea.tp.pokemon_ui.pokemonTypes.bo.PokemonType;
import io.github.resilience4j.retry.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PokemonTypeServiceImpl implements PokemonTypeService {

    private Retry retry;
    private RestTemplate restTemplate;
    private String url;


    @Override
    public List<PokemonType> listPokemonsTypes() {
        PokemonType[] objects = this.restTemplate.getForObject(this.url+"/pokemon-types/", PokemonType[].class);

        if (objects != null) {
            return Arrays.asList(objects);
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    @Cacheable("pokemon-types")
    public PokemonType getPokemonByTypeId(Integer id) {
        PokemonType pokemonType = this.retry.executeSupplier(() ->this.restTemplate.getForObject(this.url+"/pokemon-types", PokemonType.class, id));

        if (pokemonType != null) {
            return pokemonType;
        } else {
            throw new NullPointerException();
        }
    }

    @Autowired
    public void setRetry(Retry retry) {
        this.retry = retry;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${pokemonType.service.url}")
    public void setPokemonTypeServiceUrl(String pokemonServiceUrl) {
        this.url = pokemonServiceUrl;
    }
}
