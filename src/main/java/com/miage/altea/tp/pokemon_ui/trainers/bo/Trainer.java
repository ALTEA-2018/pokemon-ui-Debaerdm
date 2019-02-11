package com.miage.altea.tp.pokemon_ui.trainers.bo;

import com.miage.altea.tp.pokemon_ui.pokemonTypes.bo.PokemonType;

import java.util.List;

public class Trainer {

    private String name;

    private List<Pokemon> team;
    private List<PokemonType> teams;

    public Trainer() {
    }

    public Trainer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pokemon> getTeam() {
        return team;
    }

    public void setTeam(List<Pokemon> team) {
        this.team = team;
    }

    public List<PokemonType> getTeams() {
        return teams;
    }

    public void setTeams(List<PokemonType> teams) {
        this.teams = teams;
    }

    public void addPokemonTypeToTeam(PokemonType type) {
        this.teams.add(type);
    }
}
