package com.wip.VehicleServiceManagement.repository;

import com.wip.VehicleServiceManagement.entity.ServiceBooking;
import com.wip.VehicleServiceManagement.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * BookingRepository.
 *
 * @author Sridevi Srikumar
 */

@Repository
public interface BookingRepository extends JpaRepository<ServiceBooking, Long> {

    List<ServiceBooking> findByVehicle(Vehicle vehicle);

    List<ServiceBooking> findByStatus(String status);
}