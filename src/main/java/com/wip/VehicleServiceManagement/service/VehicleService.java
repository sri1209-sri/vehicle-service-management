package com.wip.VehicleServiceManagement.service;

import com.wip.VehicleServiceManagement.dto.VehicleDTO;
import java.util.List;
/**
 * VehicleService.
 *
 * @author Sridevi Srikumar
 */

public interface VehicleService {

    VehicleDTO createVehicle(VehicleDTO vehicleDTO);

    List<VehicleDTO> getAllVehicles();

    VehicleDTO getVehicleById(Long id);

    void deleteVehicle(Long id);
}