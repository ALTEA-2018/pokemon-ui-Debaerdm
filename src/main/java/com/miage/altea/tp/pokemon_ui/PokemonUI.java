package com.miage.altea.tp.pokemon_ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class PokemonUI {
    public static void main(String... args){
        SpringApplication.run(PokemonUI.class, args);
    }
}
