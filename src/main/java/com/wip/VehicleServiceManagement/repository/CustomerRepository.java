package com.wip.VehicleServiceManagement.repository;

import com.wip.VehicleServiceManagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * CustomerRepository.
 *
 * @author Sridevi Srikumar
 */

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByEmail(String email);
    Customer findByPhone(String phone);
}