package org.example.auditstarter;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.domain.model.User;
import org.example.service.AuditService;
import org.example.service.AuthServiceJdbc;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AuditAspect {

    private final AuditService auditService;
    private final AuthServiceJdbc authService;

    public AuditAspect(AuditService auditService, AuthServiceJdbc authService) {
        this.auditService = auditService;
        this.authService = authService;
    }

    @Pointcut("execution(* org.example.service..*(..))")
    public void serviceMethods() {}

    @AfterReturning(pointcut = "serviceMethods()")
    public void logAudit() {
        User user = authService.getCurrentUser();
        auditService.logAction(user.getId(), "Performed an action");
    }
}
