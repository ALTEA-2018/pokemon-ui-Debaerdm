package com.miage.altea.tp.pokemon_ui.controller;

import com.miage.altea.tp.pokemon_ui.pokemonTypes.bo.PokemonType;
import com.miage.altea.tp.pokemon_ui.pokemonTypes.service.PokemonTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PokemonTypeController {

    private PokemonTypeService pokemonTypeService;

    @GetMapping("/pokedex")
    public ModelAndView pokedex(){
        Map<String, Object> stringObjectMap = new HashMap<>();
        List<PokemonType> pokemonTypes = pokemonTypeService.listPokemonsTypes();
        pokemonTypes.sort(Comparator.comparing(PokemonType::getId));
        stringObjectMap.put("pokemonTypes", pokemonTypes);
        return new ModelAndView("pokedex", stringObjectMap);
    }

    @Autowired
    public void setPokemonTypeService(PokemonTypeService pokemonTypeService) {
        this.pokemonTypeService = pokemonTypeService;
    }
}
