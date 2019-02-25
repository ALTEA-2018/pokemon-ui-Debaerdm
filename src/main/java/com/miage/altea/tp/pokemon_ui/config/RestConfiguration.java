package com.miage.altea.tp.pokemon_ui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestConfiguration {

    @Bean
    RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add((request, body, execution) -> {
            request.getHeaders().add("Accept-Language", LocaleContextHolder.getLocale().getLanguage());
            return execution.execute(request, body);
        });

        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }

}
