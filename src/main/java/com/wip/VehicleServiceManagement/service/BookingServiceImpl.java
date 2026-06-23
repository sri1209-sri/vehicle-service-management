package com.wip.VehicleServiceManagement.service;

import com.wip.VehicleServiceManagement.dto.BookingDTO;
import com.wip.VehicleServiceManagement.entity.ServiceBooking;
import com.wip.VehicleServiceManagement.entity.ServiceEntity;
import com.wip.VehicleServiceManagement.entity.Vehicle;
import com.wip.VehicleServiceManagement.repository.BookingRepository;
import com.wip.VehicleServiceManagement.repository.ServiceRepository;
import com.wip.VehicleServiceManagement.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
/**
 * BookingServiceImpl.
 *
 * @author Sridevi Srikumar
 */

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public BookingDTO createBooking(BookingDTO dto) {

        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        java.util.List<ServiceEntity> services;
        if (dto.getServiceIds() != null && !dto.getServiceIds().isEmpty()) {
            services = serviceRepository.findAllById(dto.getServiceIds());
            if (services.size() != dto.getServiceIds().size()) {
                throw new RuntimeException("One or more services not found");
            }
        } else if (dto.getServiceId() != null) {
            ServiceEntity service = serviceRepository.findById(dto.getServiceId())
                    .orElseThrow(() -> new RuntimeException("Service not found"));
            services = java.util.Collections.singletonList(service);
        } else {
            throw new RuntimeException("At least one service must be selected");
        }

        ServiceBooking booking = new ServiceBooking();

        booking.setVehicle(vehicle);
        booking.setServices(services);
        booking.setProblemDescription(dto.getProblemDescription());
        booking.setStatus("BOOKED");

        ServiceBooking saved = bookingRepository.save(booking);

        return convertToDTO(saved);
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDTO getBookingById(Long id) {

        ServiceBooking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        return convertToDTO(booking);
    }

    @Override
    public BookingDTO updateBooking(Long id, BookingDTO dto) {

        ServiceBooking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        java.util.List<ServiceEntity> services;
        if (dto.getServiceIds() != null && !dto.getServiceIds().isEmpty()) {
            services = serviceRepository.findAllById(dto.getServiceIds());
            if (services.size() != dto.getServiceIds().size()) {
                throw new RuntimeException("One or more services not found");
            }
        } else if (dto.getServiceId() != null) {
            ServiceEntity service = serviceRepository.findById(dto.getServiceId())
                    .orElseThrow(() -> new RuntimeException("Service not found"));
            services = java.util.Collections.singletonList(service);
        } else {
            throw new RuntimeException("At least one service must be selected");
        }

        booking.setVehicle(vehicle);
        booking.setServices(services);
        booking.setProblemDescription(dto.getProblemDescription());
        booking.setStatus(dto.getStatus());

        ServiceBooking updated = bookingRepository.save(booking);

        return convertToDTO(updated);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    private BookingDTO convertToDTO(ServiceBooking booking) {

        BookingDTO dto = new BookingDTO();

        dto.setBookingId(booking.getBookingId());
        dto.setVehicleId(booking.getVehicle().getVehicleId());
        
        if (booking.getServices() != null && !booking.getServices().isEmpty()) {
            dto.setServiceId(booking.getServices().get(0).getServiceId());
            dto.setServiceIds(booking.getServices().stream()
                    .map(ServiceEntity::getServiceId)
                    .collect(Collectors.toList()));
        }
        
        dto.setProblemDescription(booking.getProblemDescription());
        dto.setStatus(booking.getStatus());

        return dto;
    }
}