package com.wip.VehicleServiceManagement.service;

import com.wip.VehicleServiceManagement.dto.BookingDTO;

import java.util.List;
/**
 * BookingService.
 *
 * @author Sridevi Srikumar
 */

public interface BookingService {

    BookingDTO createBooking(BookingDTO bookingDTO);

    List<BookingDTO> getAllBookings();

    BookingDTO getBookingById(Long id);

    BookingDTO updateBooking(Long id, BookingDTO bookingDTO);

    void deleteBooking(Long id);
}