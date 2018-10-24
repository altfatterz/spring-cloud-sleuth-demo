package com.example.fooservice;

import brave.Span;
import brave.Tracer;
import brave.propagation.CurrentTraceContext;
import brave.propagation.ExtraFieldPropagation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@SpringBootApplication
public class FooServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FooServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CurrentTraceContext currentTraceContext() {
        return CustomSlf4jCurrentTraceContext.create();
    }
}

@RestController
@Slf4j
class FooController {

    private final Logger performance = LoggerFactory.getLogger("performance-logger");

    private final RestTemplate restTemplate;

    private final Tracer tracer;

    public FooController(RestTemplate restTemplate, Tracer tracer) {
        this.restTemplate = restTemplate;
        this.tracer = tracer;
    }

    @GetMapping("/")
    public String foo(HttpServletRequest request) {

        logHeaders(request);

        log.info("foo-service called...");
        performance.info("foo-service called...");

        log.info("Hello from service1. Setting baggage foo=>bar");
        Span span = tracer.currentSpan();

        String baggageKey = "key";
        String baggageValue = request.getHeader("trId");
        ExtraFieldPropagation.set(baggageKey, baggageValue);

        String barResponse = restTemplate.getForObject("http://localhost:8082", String.class);
        return "bar response:" + barResponse;
    }

    private void logHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.info(String.format("%s:%s", headerName, request.getHeader(headerName)));
        }
    }
}