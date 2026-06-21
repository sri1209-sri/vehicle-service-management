package com.wip.VehicleServiceManagement.service;

import com.wip.VehicleServiceManagement.dto.ServiceDTO;
import com.wip.VehicleServiceManagement.entity.Admin;
import com.wip.VehicleServiceManagement.entity.ServiceEntity;
import com.wip.VehicleServiceManagement.repository.AdminRepository;
import com.wip.VehicleServiceManagement.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public ServiceDTO createService(ServiceDTO dto) {

        Admin admin;
        if (dto.getAdminId() == null) {
            admin = adminRepository.findAll().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("No admin accounts exist to assign this action"));
        } else {
            admin = adminRepository.findById(dto.getAdminId())
                    .orElseThrow(() -> new RuntimeException("Admin not found"));
        }

        ServiceEntity service = new ServiceEntity();
        service.setServiceName(dto.getServiceName());
        service.setDescription(dto.getDescription());
        service.setBaseCost(dto.getBaseCost());
        service.setCreatedBy(admin);

        ServiceEntity saved = serviceRepository.save(service);

        return convertToDTO(saved);
    }

    @Override
    public List<ServiceDTO> getAllServices() {
        return serviceRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceDTO getServiceById(Long id) {

        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        return convertToDTO(service);
    }

    @Override
    public ServiceDTO updateService(Long id, ServiceDTO dto) {

        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Admin admin;
        if (dto.getAdminId() == null) {
            admin = adminRepository.findAll().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("No admin accounts exist to assign this action"));
        } else {
            admin = adminRepository.findById(dto.getAdminId())
                    .orElseThrow(() -> new RuntimeException("Admin not found"));
        }

        service.setServiceName(dto.getServiceName());
        service.setDescription(dto.getDescription());
        service.setBaseCost(dto.getBaseCost());
        service.setCreatedBy(admin);

        ServiceEntity updated = serviceRepository.save(service);

        return convertToDTO(updated);
    }

    @Override
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    private ServiceDTO convertToDTO(ServiceEntity service) {

        ServiceDTO dto = new ServiceDTO();

        dto.setServiceId(service.getServiceId());
        dto.setServiceName(service.getServiceName());
        dto.setDescription(service.getDescription());
        dto.setBaseCost(service.getBaseCost());

        if(service.getCreatedBy()!=null){
            dto.setAdminId(service.getCreatedBy().getAdminId());
        }

        return dto;
    }
}