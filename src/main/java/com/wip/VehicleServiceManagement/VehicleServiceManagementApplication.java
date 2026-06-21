package com.wip.VehicleServiceManagement;

import com.wip.VehicleServiceManagement.entity.Admin;
import com.wip.VehicleServiceManagement.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class VehicleServiceManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleServiceManagementApplication.class, args);
		System.out.println("Application started sucessfully...");
	}

	@Bean
	public CommandLineRunner initData(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			Admin admin = adminRepository.findByUsername("admin");
			if (admin == null) {
				Admin defaultAdmin = new Admin();
				defaultAdmin.setUsername("admin");
				defaultAdmin.setEmail("admin@vehiserve.com");
				defaultAdmin.setPassword(passwordEncoder.encode("admin123"));
				adminRepository.save(defaultAdmin);
				System.out.println("Default Admin seeded successfully!");
			} else {
				String pwd = admin.getPassword();
				if (pwd == null || !pwd.startsWith("$2")) {
					admin.setPassword(passwordEncoder.encode("admin123"));
					adminRepository.save(admin);
					System.out.println("Existing Admin password updated to BCrypt encoded format!");
				}
			}
		};
	}

}
