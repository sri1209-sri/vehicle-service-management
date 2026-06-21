package com.wip.VehicleServiceManagement.service;

import com.wip.VehicleServiceManagement.entity.Admin;
import com.wip.VehicleServiceManagement.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Admin createAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Admin getAdminById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    @Override
    public Admin updateAdmin(Long id, Admin admin) {
        Admin existing = getAdminById(id);
        existing.setUsername(admin.getUsername());
        existing.setEmail(admin.getEmail());
        if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(admin.getPassword()));
        }
        return adminRepository.save(existing);
    }

    @Override
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
}