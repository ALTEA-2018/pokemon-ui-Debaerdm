package com.miage.altea.tp.pokemon_ui.trainers.service;

import com.miage.altea.tp.pokemon_ui.trainers.bo.Trainer;

import java.util.List;

public interface TrainersService {
    List<Trainer> listTrainers();
    Trainer getTrainer(String name);
}