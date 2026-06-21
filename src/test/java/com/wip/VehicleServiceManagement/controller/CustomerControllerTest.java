package com.wip.VehicleServiceManagement.controller;

import com.wip.VehicleServiceManagement.entity.Customer;
import com.wip.VehicleServiceManagement.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

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
    void testCreateCustomer_Success() {
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);

        Customer response = customerController.createCustomer(customer);

        assertNotNull(response);
        assertEquals("John Doe", response.getName());
        verify(customerService, times(1)).createCustomer(customer);
    }

    @Test
    void testGetCustomerById_Success() {
        when(customerService.getCustomerById(1L)).thenReturn(customer);

        Customer found = customerController.getCustomerById(1L);

        assertNotNull(found);
        assertEquals("John Doe", found.getName());
    }

    @Test
    void testUpdateCustomer_Success() {
        Customer updateDetails = new Customer();
        updateDetails.setName("John Updated");

        when(customerService.updateCustomer(eq(1L), any(Customer.class))).thenReturn(customer);

        Customer updated = customerController.updateCustomer(1L, updateDetails);

        assertNotNull(updated);
        verify(customerService, times(1)).updateCustomer(eq(1L), any(Customer.class));
    }

    @Test
    void testDeleteCustomer_Success() {
        doNothing().when(customerService).deleteCustomer(1L);

        String response = customerController.deleteCustomer(1L);

        assertEquals("Customer deleted successfully", response);
        verify(customerService, times(1)).deleteCustomer(1L);
    }
}
