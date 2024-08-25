package org.example.controller;

import org.example.domain.model.AuditLog;
import org.example.domain.model.User;
import org.example.domain.model.UserRole;
import org.example.service.AuthServiceJdbc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.example.service.AuditService;
import org.example.service.SearchService;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("SearchController Tests")
public class SearchControllerTest {

    private SearchController searchController;
    private AuditService auditService;
    private SearchService searchService;
    private AuthServiceJdbc authService;

    @BeforeEach
    @DisplayName("Set up SearchController and service mocks")
    void setUp() {
        auditService = mock(AuditService.class);
        searchController = new SearchController(searchService, auditService, authService);
    }

    @Test
    @DisplayName("Test exportLogs() - Should export audit logs to a file")
    void testExportLogs() throws IOException {
        User user = new User(1, "admin", "password", UserRole.ADMIN,null);
        AuditLog log1 = new AuditLog(1, user, "Action 1", null);
        AuditLog log2 = new AuditLog(2, user, "Action 2", null);
        List<AuditLog> logs = Arrays.asList(log1, log2);
        when(auditService.getAllLogs()).thenReturn(logs);

        searchController.exportLogs();

        File file = new File("audit_logs.txt");
        assertThat(file.exists()).isTrue();

        // Delete the test export log file if exists
        deleteTestExportLogs(file);
    }

    private static void deleteTestExportLogs(File file) {
        if (file.exists()) {
            file.delete();
        }
    }
}
