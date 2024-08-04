package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import out.EmployeeService;
import repository.EmployeeRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    public void addEmployee_success() {
        Employee employee = new Employee("john_doe", "John Doe", "Manager");
        employeeService.addEmployee(employee);

        verify(employeeRepository, times(1)).addEmployee(employee);
    }

    @Test
    public void listEmployees_returnsEmployees() {
        Employee employee1 = new Employee("john_doe", "John Doe", "Manager");
        Employee employee2 = new Employee("jane_doe", "Jane Doe", "Sales");
        when(employeeRepository.getAllEmployees()).thenReturn(Arrays.asList(employee1, employee2));

        List<Employee> employees = employeeService.listEmployees();

        assertThat(employees).hasSize(2);
        assertThat(employees).contains(employee1, employee2);
    }
}

