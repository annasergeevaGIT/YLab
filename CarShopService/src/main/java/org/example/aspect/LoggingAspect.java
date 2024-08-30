package org.example.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    // Pointcut to match all methods in service layer
    @Pointcut("execution(* org.example.service..*(..))")
    public void serviceMethods() {}

    // Around advice to log execution time of methods in service layer
    @Around("serviceMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result;
        try {
            // Proceed with method execution
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            // Log the exception and rethrow it
            log.error("Exception in method: {}", joinPoint.getSignature(), throwable);
            throw throwable;
        } finally {
            // Calculate and log execution time
            long elapsedTime = System.currentTimeMillis() - start;
            log.info("Method {} executed in {} ms", joinPoint.getSignature(), elapsedTime);
        }

        return result;
    }
}
