package com.example.gsactuatorservice.client;

import com.example.gsactuatorservice.domain.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class GreetingService {

    @Autowired
    private RestTemplate restTemplate;

    public Greeting getGreeeting(Optional<String> name) {
        return restTemplate.getForObject("/hello-world?name={name}", Greeting.class, name.get());
    }

}