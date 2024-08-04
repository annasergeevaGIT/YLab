package service;

import out.AuditLog;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import out.AuditService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class AuditServiceTest {

    private AuditService auditService;

    @BeforeEach
    public void setUp() {
        auditService = new AuditService();
    }

    @Test
    public void logAction_success() {
        User user = new User("john_doe", "password", Role.CUSTOMER);
        auditService.logAction("Login", user);

        List<AuditLog> logs = auditService.getLogs();
        assertThat(logs).hasSize(1);
        assertThat(logs.get(0).getAction()).isEqualTo("Login");
    }

    @Test
    public void exportLogsToFile_success() throws FileNotFoundException {
        User user = new User("john_doe", "password", Role.CUSTOMER);
        auditService.logAction("Login", user);

        String filename = "audit_logs.txt";
        auditService.exportLogsToFile(filename);

        File file = new File(filename);
        assertThat(file.exists()).isTrue();

        Scanner scanner = new Scanner(file);
        assertThat(scanner.hasNextLine()).isTrue();
        scanner.close();
    }
}
