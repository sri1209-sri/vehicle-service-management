package com.wip.VehicleServiceManagement.repository;

import com.wip.VehicleServiceManagement.entity.ServiceHistory;
import com.wip.VehicleServiceManagement.entity.ServiceBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceHistoryRepository extends JpaRepository<ServiceHistory, Long> {

    ServiceHistory findByBooking(ServiceBooking booking);
}