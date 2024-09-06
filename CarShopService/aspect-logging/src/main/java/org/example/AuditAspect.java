package org.example;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
@Slf4j
public class AuditAspect {

    /**
     * Log user action (method and its arguments),
     * {@code @Before} means it will be executed before each method, marked with {@code @Service}
     * Pointcut: within(@org.springframework.stereotype.Service *) specifies that this advice should apply
     * to all methods within classes annotated with @Service.
     * @param joinPoint Provides access to the state of the method being advised and its arguments.
     */
    @Before("within(@org.springframework.stereotype.Service *)")
    public void auditUserAction(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("User action: {} with arguments {}", methodName, args);
    }

    /**
     * Measures and logs the execution time of the target method
     * @param joinPoint ProceedingJoinPoint is able to control WHEN and IF the target method is executed.
     *                  When you call proceed(), it triggers the execution of the target method (the method that the advice is applied to)
     * @return          The method returns an Object because it is a generic representation of whatever the target method returns.
     *                  If the method being intercepted returns a String, then proceed() will return a String;
     *                  if it returns void, then proceed() will return null.
     * @throws Throwable ensures that any type of error or exception can be properly propagated.
     */
    @Around("within(@org.springframework.stereotype.Service *)")// This advice runs both before and after the method is executed.
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;
        log.info("Method {} executed in {} ms", joinPoint.getSignature(), executionTime);
        return proceed;
    }

    /**
     * This advice runs after the target method throws an exception.
     * This method logs any exceptions thrown by the target method, along with the method's name and arguments
     * Pointcut: Again, it targets all methods within classes annotated with @Service.
     * @param joinPoint Provides access to the method signature and arguments.
     * @param ex Captures the thrown exception to log the error details.
     */
    @AfterThrowing(pointcut = "within(@org.springframework.stereotype.Service *)", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        log.error("Exception in method {} with arguments {}. Exception: {}",
                joinPoint.getSignature().getName(),
                joinPoint.getArgs(),
                ex.getMessage(), ex);
    }

}