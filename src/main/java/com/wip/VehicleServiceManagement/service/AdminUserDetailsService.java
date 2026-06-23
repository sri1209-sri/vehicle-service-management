package com.wip.VehicleServiceManagement.service;

import com.wip.VehicleServiceManagement.entity.Admin;
import com.wip.VehicleServiceManagement.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
/**
 * AdminUserDetailsService.
 *
 * @author Sridevi Srikumar
 */

@Service
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        java.util.List<Admin> admins = adminRepository.findAll().stream()
                .filter(a -> username.equalsIgnoreCase(a.getUsername()))
                .collect(java.util.stream.Collectors.toList());
        if (admins.isEmpty()) {
            throw new UsernameNotFoundException("Admin not found with username: " + username);
        }

        Admin admin = admins.get(0);
        return new User(
            admin.getUsername(),
            admin.getPassword(),
            Collections.singletonList(() -> "ROLE_ADMIN")
        );
    }
}
