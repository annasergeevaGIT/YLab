package org.example.auditstarter;

import org.example.service.AuditService;
import org.example.service.AuthServiceJdbc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AuditAutoConfiguration {

    @Bean
    public AuditAspect auditAspect(AuditService auditService, AuthServiceJdbc authService) {
        return new AuditAspect(auditService, authService);
    }
}
