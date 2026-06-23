package com.wip.VehicleServiceManagement.service;

import com.wip.VehicleServiceManagement.dto.ServiceDTO;

import java.util.List;
/**
 * ServiceService.
 *
 * @author Sridevi Srikumar
 */

public interface ServiceService {

    ServiceDTO createService(ServiceDTO serviceDTO);
    List<ServiceDTO> getAllServices();
    ServiceDTO getServiceById(Long id);
    ServiceDTO updateService(Long id, ServiceDTO serviceDTO);
    void deleteService(Long id);
}