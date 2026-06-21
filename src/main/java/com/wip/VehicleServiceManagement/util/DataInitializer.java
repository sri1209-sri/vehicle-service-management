package com.wip.VehicleServiceManagement.util;

import com.wip.VehicleServiceManagement.entity.Admin;
import com.wip.VehicleServiceManagement.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (adminRepository.count() == 0) {
            Admin defaultAdmin = new Admin();
            defaultAdmin.setUsername("admin");
            defaultAdmin.setPassword(passwordEncoder.encode("admin123"));
            defaultAdmin.setEmail("admin@vehiserve.com");
            
            adminRepository.save(defaultAdmin);
            System.out.println("--- Seeded Default Admin Account (username: 'admin', password: 'admin123') ---");
        }
    }
}
