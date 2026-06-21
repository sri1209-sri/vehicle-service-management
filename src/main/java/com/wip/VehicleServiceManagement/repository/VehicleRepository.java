package com.wip.VehicleServiceManagement.repository;

import com.wip.VehicleServiceManagement.entity.Vehicle;
import com.wip.VehicleServiceManagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByCustomer(Customer customer);

    Vehicle findByVehicleNumber(String vehicleNumber);
}