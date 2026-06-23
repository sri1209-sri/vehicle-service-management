package com.wip.VehicleServiceManagement.controller;

import com.wip.VehicleServiceManagement.entity.Invoice;
import com.wip.VehicleServiceManagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * HomeController.
 *
 * @author Devadarshini M
 */

@Controller
public class HomeController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ServiceHistoryRepository serviceHistoryRepository;

    @GetMapping("favicon.ico")
    @ResponseBody
    public void favicon() {
        // Return 200 OK with no content to prevent 500/404 errors for favicon requests
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalCustomers", customerRepository.count());
        model.addAttribute("totalVehicles", vehicleRepository.count());
        model.addAttribute("totalBookings", bookingRepository.count());
        
        double totalRevenue = invoiceRepository.findAll().stream()
                .mapToDouble(Invoice::getAmount)
                .sum();
        model.addAttribute("totalRevenue", totalRevenue);
        
        model.addAttribute("recentBookings", bookingRepository.findAll());
        return "dashboard";
    }

    @GetMapping("/customers")
    public String customers(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "customers";
    }

    @GetMapping("/vehicles")
    public String vehicles(Model model) {
        model.addAttribute("vehicles", vehicleRepository.findAll());
        model.addAttribute("customers", customerRepository.findAll());
        return "vehicles";
    }

    @GetMapping("/bookings")
    public String bookings(Model model) {
        model.addAttribute("bookings", bookingRepository.findAll());
        model.addAttribute("vehicles", vehicleRepository.findAll());
        model.addAttribute("services", serviceRepository.findAll());
        return "booking";
    }

    @GetMapping("/services")
    public String services(Model model) {
        model.addAttribute("services", serviceRepository.findAll());
        return "services";
    }

    @GetMapping("/invoices")
    public String invoices(Model model) {
        model.addAttribute("invoices", invoiceRepository.findAll());
        return "invoices";
    }

    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("history", serviceHistoryRepository.findAll());
        return "servicehistory";
    }
}