package org.example.service;

import org.example.model.AuditLog;
import org.example.model.User;
import org.example.model.UserRole;
import org.example.service.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.repository.AuditRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AuditServiceTest {

    private AuditService auditService;
    private AuditRepository auditRepository;

    @BeforeEach
    void setUp() {
        auditRepository = mock(AuditRepository.class);
        auditService = new AuditService(auditRepository);
    }

    @Test
    void testLogAction() {
        User user = new User(1, "admin", "password", UserRole.ADMIN);
        String action = "Added car: Toyota Camry";

        auditService.logAction(user, action);

        verify(auditRepository, times(1)).save(any(AuditLog.class));
    }

    @Test
    void testGetAllLogs() {
        User user = new User(1, "admin", "password", UserRole.ADMIN);
        AuditLog log1 = new AuditLog(1, user, "Action 1", LocalDateTime.now());
        AuditLog log2 = new AuditLog(2, user, "Action 2", LocalDateTime.now());
        when(auditRepository.findAll()).thenReturn(Arrays.asList(log1, log2));

        List<AuditLog> logs = auditService.getAllLogs();

        assertThat(logs).hasSize(2);
        assertThat(logs).contains(log1, log2);
    }
}
