package com.wip.VehicleServiceManagement.service;

import com.wip.VehicleServiceManagement.dto.VehicleDTO;
import com.wip.VehicleServiceManagement.entity.Customer;
import com.wip.VehicleServiceManagement.entity.Vehicle;
import com.wip.VehicleServiceManagement.repository.CustomerRepository;
import com.wip.VehicleServiceManagement.repository.VehicleRepository;
import com.wip.VehicleServiceManagement.service.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
/**
 * VehicleServiceImpl.
 *
 * @author Sridevi Srikumar
 */

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public VehicleDTO createVehicle(VehicleDTO dto) {

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Vehicle vehicle = modelMapper.map(dto, Vehicle.class);
        vehicle.setCustomer(customer);

        Vehicle saved = vehicleRepository.save(vehicle);

        VehicleDTO response = modelMapper.map(saved, VehicleDTO.class);
        response.setCustomerId(customer.getCustomerId());

        return response;
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {

        return vehicleRepository.findAll()
                .stream()
                .map(vehicle -> {
                    VehicleDTO dto = modelMapper.map(vehicle, VehicleDTO.class);
                    dto.setCustomerId(vehicle.getCustomer().getCustomerId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VehicleDTO getVehicleById(Long id) {

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        VehicleDTO dto = modelMapper.map(vehicle, VehicleDTO.class);
        dto.setCustomerId(vehicle.getCustomer().getCustomerId());

        return dto;
    }

    @Override
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
}