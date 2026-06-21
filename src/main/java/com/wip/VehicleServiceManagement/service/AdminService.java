package com.wip.VehicleServiceManagement.service;

import com.wip.VehicleServiceManagement.entity.Admin;
import java.util.List;

public interface AdminService {

    Admin createAdmin(Admin admin);

    List<Admin> getAllAdmins();

    Admin getAdminById(Long id);

    Admin updateAdmin(Long id, Admin admin);

    void deleteAdmin(Long id);
}