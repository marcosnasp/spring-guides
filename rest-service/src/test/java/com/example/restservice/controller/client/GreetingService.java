package com.example.restservice.controller.client;

import com.example.restservice.domain.Greeting;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class GreetingService {

    private final RestTemplate restTemplate;

    public GreetingService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public Greeting getGreeeting(Optional<String> name) {
        return restTemplate.getForObject("/greeting?name={name}", Greeting.class, name.get());
    }

}