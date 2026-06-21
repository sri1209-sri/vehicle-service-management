package com.wip.VehicleServiceManagement.controller;

import com.wip.VehicleServiceManagement.dto.BookingDTO;
import com.wip.VehicleServiceManagement.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing service bookings.
 */
@RestController
@RequestMapping("/api/booking")
@Tag(name = "Booking Management", description = "APIs for managing vehicle service bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    /**
     * Create a new vehicle service booking.
     *
     * @param dto the booking data transfer object
     * @return the created BookingDTO details
     */
    @PostMapping("/create")
    @Operation(summary = "Create Service Booking", description = "Creates a new vehicle service booking record.")
    public BookingDTO createBooking(@Valid @RequestBody BookingDTO dto) {
        return bookingService.createBooking(dto);
    }

    /**
     * Retrieve all booking records.
     *
     * @return a list of all BookingDTO records
     */
    @GetMapping("/all")
    @Operation(summary = "Get All Bookings", description = "Retrieves all vehicle service bookings from the database.")
    public List<BookingDTO> getAllBookings() {
        return bookingService.getAllBookings();
    }

    /**
     * Retrieve a specific booking record by its ID.
     *
     * @param id the booking identifier
     * @return the BookingDTO details
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get Booking by ID", description = "Retrieves details of a single booking by its ID.")
    public BookingDTO getBooking(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    /**
     * Update an existing service booking.
     *
     * @param id the booking identifier
     * @param dto the updated booking data transfer object
     * @return the updated BookingDTO details
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "Update Booking", description = "Updates details of an existing booking by its ID.")
    public BookingDTO updateBooking(@PathVariable Long id,
                                    @Valid @RequestBody BookingDTO dto) {
        return bookingService.updateBooking(id, dto);
    }

    /**
     * Delete a service booking by its ID.
     *
     * @param id the booking identifier
     * @return a success confirmation message
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete Booking", description = "Removes a vehicle service booking from the database.")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "Booking Deleted Successfully";
    }
}