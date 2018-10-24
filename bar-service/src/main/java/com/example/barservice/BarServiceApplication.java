package com.example.barservice;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@SpringBootApplication
@RestController
@Slf4j
public class BarServiceApplication {

    private final Logger performance = LoggerFactory.getLogger("performance-logger");

    public static void main(String[] args) {
        SpringApplication.run(BarServiceApplication.class, args);
    }

    @GetMapping("/")
    public String bar(HttpServletRequest request) {
        logHeaders(request);

        performance.info("bar-service called...");
        log.info("bar-service called...");
        return "bar";
    }

    private void logHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.info(String.format("%s:%s", headerName, request.getHeader(headerName)));
        }
    }

}
