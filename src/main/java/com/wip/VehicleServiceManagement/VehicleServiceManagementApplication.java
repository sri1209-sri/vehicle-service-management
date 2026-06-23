package com.wip.VehicleServiceManagement;

import com.wip.VehicleServiceManagement.entity.Admin;
import com.wip.VehicleServiceManagement.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * VehicleServiceManagementApplication.
 *
 * @author Devadarshini M
 * @author Sridevi Srikumar
 */

@SpringBootApplication
public class VehicleServiceManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleServiceManagementApplication.class, args);
		System.out.println("Application started sucessfully...");
	}

	@Bean
	public CommandLineRunner initData(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			java.util.List<Admin> admins = adminRepository.findAll().stream()
					.filter(a -> "admin".equalsIgnoreCase(a.getUsername()))
					.collect(java.util.stream.Collectors.toList());
			if (admins.isEmpty()) {
				Admin defaultAdmin = new Admin();
				defaultAdmin.setUsername("admin");
				defaultAdmin.setEmail("admin@vehiserve.com");
				defaultAdmin.setPassword(passwordEncoder.encode("admin123"));
				adminRepository.save(defaultAdmin);
				System.out.println("Default Admin seeded successfully!");
			} else {
				for (Admin admin : admins) {
					admin.setPassword(passwordEncoder.encode("admin123"));
					adminRepository.save(admin);
				}
				System.out.println("Existing Admin passwords reset to 'admin123' successfully!");
			}
		};
	}

}
