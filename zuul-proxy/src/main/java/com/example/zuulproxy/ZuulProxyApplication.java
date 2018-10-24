package com.example.zuulproxy;

import brave.propagation.CurrentTraceContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class ZuulProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulProxyApplication.class, args);
    }

    @Bean
    public AuditFilter auditFilter() {
        return new AuditFilter();
    }

    @Bean
    public CurrentTraceContext currentTraceContext() {
        return CustomSlf4jCurrentTraceContext.create();
    }
}
