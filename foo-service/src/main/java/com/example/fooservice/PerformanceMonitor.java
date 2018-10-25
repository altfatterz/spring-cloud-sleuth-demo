package com.example.fooservice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.StopWatch;

@Configuration
@EnableAspectJAutoProxy
@Aspect
public class PerformanceMonitor {

    @Pointcut("execution(public String com.example.fooservice.FooService.getFoo(..))")
    public void fooService() {
    }

    private static final Logger logger = LoggerFactory.getLogger("performance-logger");

    @Around("fooService()")
    public Object basicProfile(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            return pjp.proceed();
        } finally {
            stopWatch.stop();
            logger.info(String.format("duration=%s method=%s", stopWatch.getLastTaskTimeMillis(), pjp.getSignature().getName()));
        }
    }


}
