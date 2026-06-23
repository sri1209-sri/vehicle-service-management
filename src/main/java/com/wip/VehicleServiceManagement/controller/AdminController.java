package com.wip.VehicleServiceManagement.controller;

import com.wip.VehicleServiceManagement.entity.Admin;
import com.wip.VehicleServiceManagement.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for managing Administrator accounts.
 * @author Devadarshini M
 */
@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin Management", description = "APIs for administrator profile CRUD operations")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * Create a new administrator account.
     *
     * @param admin administrator details
     * @return saved Admin entity
     */
    @PostMapping("/create")
    @Operation(summary = "Create an Admin", description = "Registers a new administrator profile with encrypted password.")
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.createAdmin(admin);
    }

    /**
     * Retrieve all administrator accounts.
     *
     * @return list of Admins
     */
    @GetMapping("/all")
    @Operation(summary = "Get All Admins", description = "Retrieves a list of all registered administrators.")
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    /**
     * Retrieve administrator details by ID.
     *
     * @param id Admin identifier
     * @return Admin details
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get Admin by ID", description = "Retrieves details of a single administrator by their ID.")
    public Admin getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id);
    }

    /**
     * Update an existing administrator account.
     *
     * @param id Admin identifier
     * @param admin administrator updated details
     * @return updated Admin details
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "Update Admin", description = "Updates details of an existing administrator.")
    public Admin updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        return adminService.updateAdmin(id, admin);
    }

    /**
     * Delete an administrator account by ID.
     *
     * @param id Admin identifier
     * @return success message
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete Admin", description = "Deletes an administrator account.")
    public String deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return "Admin deleted successfully";
    }
}