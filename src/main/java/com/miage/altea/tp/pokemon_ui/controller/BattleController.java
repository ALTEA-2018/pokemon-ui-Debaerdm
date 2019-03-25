package com.miage.altea.tp.pokemon_ui.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BattleController {

    @GetMapping("/fight/{opponent}")
    public ModelAndView fight(@PathVariable String opponent){
        var modelAndView = new ModelAndView("fight");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();

        modelAndView.addObject("trainerName", principal.getUsername());
        modelAndView.addObject("opponentName", opponent);

        return modelAndView;
    }

    @GetMapping("/battles")
    public ModelAndView battles(){
        return new ModelAndView("battles");
    }
}
