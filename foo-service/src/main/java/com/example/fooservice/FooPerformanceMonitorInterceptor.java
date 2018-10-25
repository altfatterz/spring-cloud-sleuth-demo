package com.example.fooservice;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;
import org.springframework.util.StopWatch;


public class FooPerformanceMonitorInterceptor extends AbstractMonitoringInterceptor {

    private static final Logger logger = LoggerFactory.getLogger("performance-logger");

    public FooPerformanceMonitorInterceptor()  {
    }

    public FooPerformanceMonitorInterceptor (boolean useDynamicLogger) {
        setUseDynamicLogger(useDynamicLogger);
    }

    @Override
    protected Object invokeUnderTrace(MethodInvocation methodInvocation, Log log) throws Throwable {
        String name = createInvocationTraceName(methodInvocation);

        StopWatch stopWatch = new StopWatch(name);
        stopWatch.start(name);
        try {
            return methodInvocation.proceed();
        }
        finally {
            stopWatch.stop();
            logger.info(stopWatch.shortSummary());
        }
    }
}
