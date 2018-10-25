package com.example.fooservice;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FooService {

    private final RestTemplate restTemplate;

    public FooService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getFoo() {
        return restTemplate.getForObject("http://localhost:8082", String.class);
    }
}
