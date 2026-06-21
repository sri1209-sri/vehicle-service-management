package com.wip.VehicleServiceManagement.controller;

import com.wip.VehicleServiceManagement.dto.ServiceHistoryDTO;
import com.wip.VehicleServiceManagement.service.ServiceHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing service completion logs and vehicle servicing history.
 */
@RestController
@RequestMapping("/api/service-history")
@Tag(name = "Service History Management", description = "APIs for recording service completions and reviewing logs")
public class ServiceHistoryController {

    @Autowired
    private ServiceHistoryService serviceHistoryService;

    /**
     * Record a new service completion history entry.
     *
     * @param dto the service history record DTO details
     * @return the created ServiceHistoryDTO details
     */
    @PostMapping("/create")
    @Operation(summary = "Record Completion & History", description = "Logs the details of a completed vehicle servicing event.")
    public ServiceHistoryDTO createHistory(@Valid @RequestBody ServiceHistoryDTO dto) {
        return serviceHistoryService.createHistory(dto);
    }

    /**
     * Retrieve all service history logs.
     *
     * @return a list of all ServiceHistoryDTO records
     */
    @GetMapping("/all")
    @Operation(summary = "Get All History", description = "Retrieves a list of all servicing completion histories.")
    public List<ServiceHistoryDTO> getAllHistory() {
        return serviceHistoryService.getAllHistory();
    }

    /**
     * Retrieve a specific service history log by its ID.
     *
     * @param id the history log identifier
     * @return the ServiceHistoryDTO details
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get History by ID", description = "Retrieves details of a single service history log by its ID.")
    public ServiceHistoryDTO getHistoryById(@PathVariable Long id) {
        return serviceHistoryService.getHistoryById(id);
    }

    /**
     * Update an existing service history record.
     *
     * @param id the history log identifier
     * @param dto the updated service history details DTO
     * @return the updated ServiceHistoryDTO details
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "Update History", description = "Updates details of an existing service history log.")
    public ServiceHistoryDTO updateHistory(@PathVariable Long id,
                                           @Valid @RequestBody ServiceHistoryDTO dto) {
        return serviceHistoryService.updateHistory(id, dto);
    }

    /**
     * Delete a service history log by its ID.
     *
     * @param id the history log identifier
     * @return a success confirmation message
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete History", description = "Removes a service history log from the system.")
    public String deleteHistory(@PathVariable Long id) {
        serviceHistoryService.deleteHistory(id);
        return "Service History deleted successfully";
    }
}