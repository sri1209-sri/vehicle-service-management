package com.wip.VehicleServiceManagement.repository;

import com.wip.VehicleServiceManagement.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * ServiceRepository.
 *
 * @author Sridevi Srikumar
 */

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
}