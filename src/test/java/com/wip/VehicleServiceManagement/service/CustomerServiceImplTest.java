package com.wip.VehicleServiceManagement.service;

import com.wip.VehicleServiceManagement.entity.Customer;
import com.wip.VehicleServiceManagement.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Component.
 *
 * @author Devadarshini M
 */

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setCustomerId(1L);
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setPhone("9876543210");
        customer.setAddress("123 Street");
    }

    @Test
    void testCreateCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer created = customerService.createCustomer(customer);

        assertNotNull(created);
        assertEquals("John Doe", created.getName());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testGetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer));

        List<Customer> list = customerService.getAllCustomers();

        assertEquals(1, list.size());
        assertEquals("John Doe", list.get(0).getName());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testGetCustomerById_Success() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer found = customerService.getCustomerById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getCustomerId());
    }

    @Test
    void testGetCustomerById_NotFound() {
        when(customerRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> customerService.getCustomerById(2L));
    }

    @Test
    void testUpdateCustomer() {
        Customer updateDetails = new Customer();
        updateDetails.setName("John Updated");
        updateDetails.setEmail("john.update@example.com");
        updateDetails.setPhone("1234567890");
        updateDetails.setAddress("456 Avenue");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer updated = customerService.updateCustomer(1L, updateDetails);

        assertNotNull(updated);
        assertEquals("John Updated", customer.getName());
        assertEquals("john.update@example.com", customer.getEmail());
    }

    @Test
    void testDeleteCustomer() {
        doNothing().when(customerRepository).deleteById(1L);

        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1)).deleteById(1L);
    }
}
