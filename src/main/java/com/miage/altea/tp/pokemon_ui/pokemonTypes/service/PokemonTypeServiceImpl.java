package com.miage.altea.tp.pokemon_ui.pokemonTypes.service;

import com.miage.altea.tp.pokemon_ui.pokemonTypes.bo.PokemonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
public class PokemonTypeServiceImpl implements PokemonTypeService {

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
    public PokemonType getPokemonByTypeId(Integer id) {
        PokemonType pokemonType = this.restTemplate.getForObject(this.url+"/pokemon-types/", PokemonType.class, id);

        if (pokemonType != null) {
            return pokemonType;
        } else {
            throw new NullPointerException();
        }
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
