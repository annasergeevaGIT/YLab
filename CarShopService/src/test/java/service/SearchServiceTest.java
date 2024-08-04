package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.CustomerRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    private UserService customerService;
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerService = new UserService(customerRepository);
    }

    @Test
    public void addCustomer_success() {
        Customer customer = new Customer("john_doe", "John Doe", "123-456-7890");
        customerService.addCustomer(customer);

        verify(customerRepository, times(1)).addCustomer(customer);
    }

    @Test
    public void listCustomers_returnsCustomers() {
        Customer customer1 = new Customer("john_doe", "John Doe", "123-456-7890");
        Customer customer2 = new Customer("jane_doe", "Jane Doe", "987-654-3210");
        when(customerRepository.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

        List<Customer> customers = customerService.listCustomers();

        assertThat(customers).hasSize(2);
        assertThat(customers).contains(customer1, customer2);
    }
}

