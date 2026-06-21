package com.wip.VehicleServiceManagement.service;

import com.wip.VehicleServiceManagement.dto.ServiceHistoryDTO;
import com.wip.VehicleServiceManagement.entity.Admin;
import com.wip.VehicleServiceManagement.entity.ServiceBooking;
import com.wip.VehicleServiceManagement.entity.ServiceHistory;
import com.wip.VehicleServiceManagement.repository.AdminRepository;
import com.wip.VehicleServiceManagement.repository.BookingRepository;
import com.wip.VehicleServiceManagement.repository.ServiceHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceHistoryServiceImpl implements ServiceHistoryService {

    @Autowired
    private ServiceHistoryRepository repository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public ServiceHistoryDTO createHistory(ServiceHistoryDTO dto) {

        ServiceBooking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Admin admin;
        if (dto.getAdminId() == null) {
            admin = adminRepository.findAll().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("No admin accounts exist to assign this action"));
        } else {
            admin = adminRepository.findById(dto.getAdminId())
                    .orElseThrow(() -> new RuntimeException("Admin not found"));
        }

        ServiceHistory history = new ServiceHistory();

        history.setServiceDate(dto.getServiceDate());
        history.setServiceStatus(dto.getServiceStatus());
        history.setRemarks(dto.getRemarks());
        history.setTotalCost(dto.getTotalCost());
        history.setBooking(booking);
        history.setUpdatedBy(admin);

        ServiceHistory saved = repository.save(history);

        return convertToDTO(saved);
    }

    @Override
    public List<ServiceHistoryDTO> getAllHistory() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceHistoryDTO getHistoryById(Long id) {

        ServiceHistory history = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("History not found"));

        return convertToDTO(history);
    }

    @Override
    public ServiceHistoryDTO updateHistory(Long id, ServiceHistoryDTO dto) {

        ServiceHistory history = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("History not found"));

        ServiceBooking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Admin admin;
        if (dto.getAdminId() == null) {
            admin = adminRepository.findAll().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("No admin accounts exist to assign this action"));
        } else {
            admin = adminRepository.findById(dto.getAdminId())
                    .orElseThrow(() -> new RuntimeException("Admin not found"));
        }

        history.setServiceDate(dto.getServiceDate());
        history.setServiceStatus(dto.getServiceStatus());
        history.setRemarks(dto.getRemarks());
        history.setTotalCost(dto.getTotalCost());
        history.setBooking(booking);
        history.setUpdatedBy(admin);

        return convertToDTO(repository.save(history));
    }

    @Override
    public void deleteHistory(Long id) {
        repository.deleteById(id);
    }

    private ServiceHistoryDTO convertToDTO(ServiceHistory history) {

        ServiceHistoryDTO dto = new ServiceHistoryDTO();

        dto.setHistoryId(history.getHistoryId());
        dto.setServiceDate(history.getServiceDate());
        dto.setServiceStatus(history.getServiceStatus());
        dto.setRemarks(history.getRemarks());
        dto.setTotalCost(history.getTotalCost());
        dto.setBookingId(history.getBooking().getBookingId());
        dto.setAdminId(history.getUpdatedBy().getAdminId());

        return dto;
    }
}