package org.example;

import org.example.AuditAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.*;

@Configuration //Configuration class contains bean definitions that Spring will manage.
@Slf4j
@ComponentScan(basePackages = "org.example") //scan specified package for components
public class AuditLogAutoConfig {
    @Bean
    public AuditAspect auditAspect() {
        return new AuditAspect();
    }
}
