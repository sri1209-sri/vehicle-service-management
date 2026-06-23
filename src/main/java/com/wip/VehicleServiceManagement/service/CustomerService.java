package com.wip.VehicleServiceManagement.service;

import com.wip.VehicleServiceManagement.entity.Customer;
import java.util.List;
/**
 * CustomerService.
 *
 * @author Sridevi Srikumar
 */

public interface CustomerService {

    Customer createCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

    Customer updateCustomer(Long id, Customer customer);

    void deleteCustomer(Long id);
}