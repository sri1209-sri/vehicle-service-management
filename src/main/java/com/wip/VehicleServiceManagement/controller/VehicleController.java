package com.wip.VehicleServiceManagement.controller;

import com.wip.VehicleServiceManagement.dto.VehicleDTO;
import com.wip.VehicleServiceManagement.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing vehicle registrations.
 */
@RestController
@RequestMapping("/api/vehicle")
@Tag(name = "Vehicle Management", description = "APIs for registering and managing customer vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    /**
     * Register a new vehicle.
     *
     * @param dto the vehicle registration data DTO details
     * @return the created VehicleDTO details
     */
    @PostMapping("/create")
    @Operation(summary = "Register Vehicle", description = "Registers a new customer vehicle in the system.")
    public VehicleDTO createVehicle(@Valid @RequestBody VehicleDTO dto) {
        return vehicleService.createVehicle(dto);
    }

    /**
     * Retrieve all registered vehicles.
     *
     * @return a list of all VehicleDTO records
     */
    @GetMapping("/all")
    @Operation(summary = "Get All Vehicles", description = "Retrieves a list of all registered vehicles.")
    public List<VehicleDTO> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    /**
     * Retrieve details of a specific vehicle by its ID.
     *
     * @param id the vehicle identifier
     * @return the VehicleDTO details
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get Vehicle by ID", description = "Retrieves details of a single registered vehicle by its ID.")
    public VehicleDTO getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleById(id);
    }

    /**
     * Delete a vehicle record by its ID.
     *
     * @param id the vehicle identifier
     * @return a success confirmation message
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Vehicle", description = "Removes a vehicle record from the system by ID.")
    public String deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return "Deleted successfully";
    }
}