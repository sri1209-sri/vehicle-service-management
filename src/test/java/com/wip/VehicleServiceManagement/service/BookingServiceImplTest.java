package com.wip.VehicleServiceManagement.service;

import com.wip.VehicleServiceManagement.dto.BookingDTO;
import com.wip.VehicleServiceManagement.entity.ServiceBooking;
import com.wip.VehicleServiceManagement.entity.ServiceEntity;
import com.wip.VehicleServiceManagement.entity.Vehicle;
import com.wip.VehicleServiceManagement.repository.BookingRepository;
import com.wip.VehicleServiceManagement.repository.ServiceRepository;
import com.wip.VehicleServiceManagement.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Component.
 *
 * @author Devadarshini M
 */

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Vehicle vehicle;
    private ServiceEntity serviceEntity;
    private ServiceBooking booking;
    private BookingDTO bookingDTO;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle();
        vehicle.setVehicleId(1L);
        vehicle.setVehicleNumber("MH-12-AB-1234");

        serviceEntity = new ServiceEntity();
        serviceEntity.setServiceId(10L);
        serviceEntity.setServiceName("Oil Change");
        serviceEntity.setBaseCost(49.99);

        booking = new ServiceBooking();
        booking.setBookingId(100L);
        booking.setVehicle(vehicle);
        booking.setService(serviceEntity);
        booking.setStatus("BOOKED");
        booking.setProblemDescription("Engine oil leakage");

        bookingDTO = new BookingDTO();
        bookingDTO.setBookingId(100L);
        bookingDTO.setVehicleId(1L);
        bookingDTO.setServiceId(10L);
        bookingDTO.setStatus("BOOKED");
        bookingDTO.setProblemDescription("Engine oil leakage");
    }

    @Test
    void testCreateBooking_Success() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(serviceRepository.findById(10L)).thenReturn(Optional.of(serviceEntity));
        when(bookingRepository.save(any(ServiceBooking.class))).thenReturn(booking);

        BookingDTO created = bookingService.createBooking(bookingDTO);

        assertNotNull(created);
        assertEquals(100L, created.getBookingId());
        assertEquals("BOOKED", created.getStatus());
        assertEquals("Engine oil leakage", created.getProblemDescription());
    }

    @Test
    void testCreateBooking_VehicleNotFound() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookingService.createBooking(bookingDTO));
    }

    @Test
    void testGetAllBookings() {
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking));

        List<BookingDTO> list = bookingService.getAllBookings();

        assertEquals(1, list.size());
        assertEquals(100L, list.get(0).getBookingId());
    }

    @Test
    void testGetBookingById_Success() {
        when(bookingRepository.findById(100L)).thenReturn(Optional.of(booking));

        BookingDTO found = bookingService.getBookingById(100L);

        assertNotNull(found);
        assertEquals(100L, found.getBookingId());
    }

    @Test
    void testUpdateBooking_Success() {
        BookingDTO updateDTO = new BookingDTO();
        updateDTO.setVehicleId(1L);
        updateDTO.setServiceId(10L);
        updateDTO.setProblemDescription("Severe leakage");
        updateDTO.setStatus("IN_PROGRESS");

        when(bookingRepository.findById(100L)).thenReturn(Optional.of(booking));
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(serviceRepository.findById(10L)).thenReturn(Optional.of(serviceEntity));
        when(bookingRepository.save(any(ServiceBooking.class))).thenReturn(booking);

        BookingDTO updated = bookingService.updateBooking(100L, updateDTO);

        assertNotNull(updated);
        assertEquals("IN_PROGRESS", booking.getStatus());
        assertEquals("Severe leakage", booking.getProblemDescription());
    }
}
