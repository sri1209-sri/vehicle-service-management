package com.wip.VehicleServiceManagement.service;

import com.wip.VehicleServiceManagement.entity.*;
import com.wip.VehicleServiceManagement.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomerDeletionIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ServiceHistoryRepository serviceHistoryRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Test
    public void testCustomerDeletionCascadesSuccessfully() {
        // 1. Create and save Admin (needed for ServiceHistory and Invoice)
        Admin admin = new Admin();
        admin.setUsername("testadmin");
        admin.setPassword("admin123");
        admin.setEmail("testadmin@test.com");
        admin = adminRepository.save(admin);

        // 2. Create and save Customer
        Customer customer = new Customer();
        customer.setName("Jane Doe");
        customer.setEmail("jane@example.com");
        customer.setPhone("1234567890");
        customer.setAddress("456 Oak Avenue");
        customer = customerRepository.save(customer);

        // 3. Create and save Vehicle
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("Toyota");
        vehicle.setModel("Corolla");
        vehicle.setVehicleNumber("XYZ-1234");
        vehicle.setYear(2022);
        vehicle.setCustomer(customer);
        vehicle = vehicleRepository.save(vehicle);

        // 4. Create and save ServiceEntity
        ServiceEntity service = new ServiceEntity();
        service.setServiceName("Full Inspection");
        service.setDescription("Comprehensive vehicle diagnosis");
        service.setBaseCost(150.0);
        service.setCreatedBy(admin);
        service = serviceRepository.save(service);

        // 5. Create and save ServiceBooking
        ServiceBooking booking = new ServiceBooking();
        booking.setVehicle(vehicle);
        booking.setService(service);
        booking.setProblemDescription("Routine checkup");
        booking.setStatus("BOOKED");
        booking = bookingRepository.save(booking);

        // 6. Create and save ServiceHistory
        ServiceHistory history = new ServiceHistory();
        history.setServiceDate(LocalDate.now());
        history.setServiceStatus("COMPLETED");
        history.setRemarks("All systems clear");
        history.setTotalCost(175.0);
        history.setBooking(booking);
        history.setUpdatedBy(admin);
        history = serviceHistoryRepository.save(history);

        // 7. Create and save Invoice
        Invoice invoice = new Invoice();
        invoice.setAmount(175.0);
        invoice.setPaymentStatus("PAID");
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setHistory(history);
        invoice.setGeneratedBy(admin);
        invoice = invoiceRepository.save(invoice);

        // Verify they are saved
        Long customerId = customer.getCustomerId();
        Long vehicleId = vehicle.getVehicleId();
        Long bookingId = booking.getBookingId();
        Long historyId = history.getHistoryId();
        Long invoiceId = invoice.getInvoiceId();

        assertTrue(customerRepository.findById(customerId).isPresent());
        assertTrue(vehicleRepository.findById(vehicleId).isPresent());
        assertTrue(bookingRepository.findById(bookingId).isPresent());
        assertTrue(serviceHistoryRepository.findById(historyId).isPresent());
        assertTrue(invoiceRepository.findById(invoiceId).isPresent());

        // 8. Delete the Customer
        customerRepository.deleteById(customerId);

        // 9. Assert everything has cascaded and been deleted successfully
        assertFalse(customerRepository.findById(customerId).isPresent());
        assertFalse(vehicleRepository.findById(vehicleId).isPresent());
        assertFalse(bookingRepository.findById(bookingId).isPresent());
        assertFalse(serviceHistoryRepository.findById(historyId).isPresent());
        assertFalse(invoiceRepository.findById(invoiceId).isPresent());
    }
}
