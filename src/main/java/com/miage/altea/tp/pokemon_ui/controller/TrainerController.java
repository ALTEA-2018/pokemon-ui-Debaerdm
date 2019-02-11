package com.miage.altea.tp.pokemon_ui.controller;

import com.miage.altea.tp.pokemon_ui.pokemonTypes.bo.PokemonType;
import com.miage.altea.tp.pokemon_ui.pokemonTypes.service.PokemonTypeService;
import com.miage.altea.tp.pokemon_ui.trainers.bo.Trainer;
import com.miage.altea.tp.pokemon_ui.trainers.service.TrainersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TrainerController {
    private TrainersService trainersService;

    @GetMapping("/trainers")
    public ModelAndView getTrainers(){
        Map<String, Object> stringObjectMap = new HashMap<>();
        List<Trainer> trainers = trainersService.listTrainers();
        trainers.sort(Comparator.comparing(Trainer::getName));
        stringObjectMap.put("trainers", trainers);
        return new ModelAndView("trainers", stringObjectMap);
    }

    @GetMapping(value = "/trainers/{name}")
    public ModelAndView getTrainer(@PathVariable("name") String name){
        Map<String, Object> stringObjectMap = new HashMap<>();
        Trainer trainer = trainersService.getTrainer(name);
        stringObjectMap.put("trainer", trainer);
        return new ModelAndView("trainer", stringObjectMap);
    }

    @Autowired
    public void setTrainerService(TrainersService trainerService) {
        this.trainersService = trainerService;
    }
}
