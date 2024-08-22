package org.example.service;

import org.example.util.AuditLog;
import org.example.model.User;
import org.example.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.example.repository.AuditRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
@DisplayName("AuditService Tests")
public class AuditServiceTest {

    private AuditService auditService;
    private AuditRepository auditRepository;

    @BeforeEach
    @DisplayName("Set up AuditService and repository mock")
    void setUp() {
        auditRepository = mock(AuditRepository.class);
        auditService = new AuditService(auditRepository);
    }

    @Test
    @DisplayName("Test logAction() - Should log a user action")
    void testLogAction() {
        User user = new User(1, "admin", "password", UserRole.ADMIN,null);
        String action = "Added car: Toyota Camry";

        auditService.logAction(user, action);

        verify(auditRepository, times(1)).create(any(AuditLog.class));
    }

    @Test
    @DisplayName("Test getAllLogs() - Should retrieve all audit logs")
    void testGetAllLogs() {
        User user = new User(1, "admin", "password", UserRole.ADMIN,null);
        AuditLog log1 = new AuditLog(1, user, "Action 1", LocalDateTime.now());
        AuditLog log2 = new AuditLog(2, user, "Action 2", LocalDateTime.now());
        when(auditRepository.findAll()).thenReturn(Arrays.asList(log1, log2));

        List<AuditLog> logs = auditService.getAllLogs();

        assertThat(logs).hasSize(2);
        assertThat(logs).contains(log1, log2);
    }
}
