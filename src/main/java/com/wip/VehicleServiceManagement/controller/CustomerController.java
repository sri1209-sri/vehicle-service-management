package com.wip.VehicleServiceManagement.controller;

import com.wip.VehicleServiceManagement.entity.Customer;
import com.wip.VehicleServiceManagement.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Customers.
 */
@RestController
@RequestMapping("/api/customer")
@Tag(name = "Customer Management", description = "APIs for Customer registration and CRUD profiles")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Register a new customer in the system.
     *
     * @param customer the customer entity details
     * @return the saved Customer entity
     */
    @PostMapping("/create")
    @Operation(summary = "Register Customer", description = "Registers a new customer in the system with validation.")
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    /**
     * Retrieve all registered customers.
     *
     * @return list of Customer entities
     */
    @GetMapping("/all")
    @Operation(summary = "Get All Customers", description = "Retrieves a list of all registered customers.")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    /**
     * Retrieve a specific customer by their ID.
     *
     * @param id the customer identifier
     * @return the Customer entity details
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get Customer by ID", description = "Retrieves details of a single customer by their ID.")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    /**
     * Update details of an existing customer.
     *
     * @param id the customer identifier
     * @param customer the updated customer entity details
     * @return the updated Customer entity
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "Update Customer details", description = "Updates profile details of an existing customer.")
    public Customer updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    /**
     * Delete a customer by their ID.
     *
     * @param id the customer identifier
     * @return a success confirmation message
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete Customer", description = "Deletes a customer profile from the system.")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "Customer deleted successfully";
    }
}