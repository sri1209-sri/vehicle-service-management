package com.wip.VehicleServiceManagement.controller;

import com.wip.VehicleServiceManagement.dto.ServiceDTO;
import com.wip.VehicleServiceManagement.service.ServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing catalog service types.
 * @author Devadarshini M
 */
@RestController
@RequestMapping("/api/service")
@Tag(name = "Service Catalog Management", description = "APIs for defining and managing vehicle service catalog offerings")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    /**
     * Define a new service offering in the catalog.
     *
     * @param serviceDTO the service details DTO
     * @return the created ServiceDTO details
     */
    @PostMapping("/create")
    @Operation(summary = "Create Service Type", description = "Defines a new service type in the catalog.")
    public ServiceDTO createService(@Valid @RequestBody ServiceDTO serviceDTO) {
        return serviceService.createService(serviceDTO);
    }

    /**
     * Retrieve all catalog services.
     *
     * @return a list of all ServiceDTO offerings
     */
    @GetMapping("/all")
    @Operation(summary = "Get All Services", description = "Retrieves a list of all defined catalog services.")
    public List<ServiceDTO> getAllServices() {
        return serviceService.getAllServices();
    }

    /**
     * Retrieve a specific catalog service by its ID.
     *
     * @param id the service identifier
     * @return the ServiceDTO details
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get Service by ID", description = "Retrieves details of a single catalog service by its ID.")
    public ServiceDTO getServiceById(@PathVariable Long id) {
        return serviceService.getServiceById(id);
    }

    /**
     * Update an existing catalog service offering.
     *
     * @param id the service identifier
     * @param serviceDTO the updated service details DTO
     * @return the updated ServiceDTO details
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "Update Service Type", description = "Updates details of an existing catalog service.")
    public ServiceDTO updateService(@PathVariable Long id,
                                    @Valid @RequestBody ServiceDTO serviceDTO) {
        return serviceService.updateService(id, serviceDTO);
    }

    /**
     * Delete a catalog service type by its ID.
     *
     * @param id the service identifier
     * @return a success confirmation message
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete Service Type", description = "Removes a service type from the catalog.")
    public String deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return "Service deleted successfully";
    }
}