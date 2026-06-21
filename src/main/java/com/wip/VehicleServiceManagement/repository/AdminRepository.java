package com.wip.VehicleServiceManagement.repository;

import com.wip.VehicleServiceManagement.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByEmail(String email);
    Admin findByUsername(String username);
}