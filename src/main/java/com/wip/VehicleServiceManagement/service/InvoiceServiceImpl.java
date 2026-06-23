package com.wip.VehicleServiceManagement.service;

import com.wip.VehicleServiceManagement.dto.InvoiceDTO;
import com.wip.VehicleServiceManagement.entity.Admin;
import com.wip.VehicleServiceManagement.entity.Invoice;
import com.wip.VehicleServiceManagement.entity.ServiceHistory;
import com.wip.VehicleServiceManagement.repository.AdminRepository;
import com.wip.VehicleServiceManagement.repository.InvoiceRepository;
import com.wip.VehicleServiceManagement.repository.ServiceHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
/**
 * InvoiceServiceImpl.
 *
 * @author Sridevi Srikumar
 */

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ServiceHistoryRepository historyRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public InvoiceDTO createInvoice(InvoiceDTO dto) {

        ServiceHistory history = historyRepository.findById(dto.getHistoryId())
                .orElseThrow(() -> new RuntimeException("Service History not found"));

        Admin admin;
        if (dto.getAdminId() == null) {
            admin = adminRepository.findAll().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("No admin accounts exist to assign this action"));
        } else {
            admin = adminRepository.findById(dto.getAdminId())
                    .orElseThrow(() -> new RuntimeException("Admin not found"));
        }

        Invoice invoice = new Invoice();

        invoice.setAmount(dto.getAmount());
        invoice.setPaymentStatus("PENDING");
        invoice.setInvoiceDate(dto.getInvoiceDate());
        invoice.setHistory(history);
        invoice.setGeneratedBy(admin);

        Invoice saved = invoiceRepository.save(invoice);

        return convertToDTO(saved);
    }

    @Override
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDTO getInvoiceById(Long id) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        return convertToDTO(invoice);
    }

    @Override
    public InvoiceDTO updateInvoice(Long id, InvoiceDTO dto) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        ServiceHistory history = historyRepository.findById(dto.getHistoryId())
                .orElseThrow(() -> new RuntimeException("Service History not found"));

        Admin admin;
        if (dto.getAdminId() == null) {
            admin = adminRepository.findAll().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("No admin accounts exist to assign this action"));
        } else {
            admin = adminRepository.findById(dto.getAdminId())
                    .orElseThrow(() -> new RuntimeException("Admin not found"));
        }

        invoice.setAmount(dto.getAmount());
        invoice.setPaymentStatus(dto.getPaymentStatus());
        invoice.setInvoiceDate(dto.getInvoiceDate());
        invoice.setHistory(history);
        invoice.setGeneratedBy(admin);

        return convertToDTO(invoiceRepository.save(invoice));
    }

    @Override
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    private InvoiceDTO convertToDTO(Invoice invoice) {

        InvoiceDTO dto = new InvoiceDTO();

        dto.setInvoiceId(invoice.getInvoiceId());
        dto.setAmount(invoice.getAmount());
        dto.setPaymentStatus(invoice.getPaymentStatus());
        dto.setInvoiceDate(invoice.getInvoiceDate());

        if (invoice.getHistory() != null) {
            dto.setHistoryId(invoice.getHistory().getHistoryId());
            dto.setServiceRemarks(invoice.getHistory().getRemarks());
            
            if (invoice.getHistory().getBooking() != null) {
                if (invoice.getHistory().getBooking().getServices() != null && !invoice.getHistory().getBooking().getServices().isEmpty()) {
                    String serviceNames = invoice.getHistory().getBooking().getServices().stream()
                            .map(com.wip.VehicleServiceManagement.entity.ServiceEntity::getServiceName)
                            .collect(java.util.stream.Collectors.joining(", "));
                    dto.setServiceName(serviceNames);
                }
                if (invoice.getHistory().getBooking().getVehicle() != null) {
                    com.wip.VehicleServiceManagement.entity.Vehicle vehicle = invoice.getHistory().getBooking().getVehicle();
                    dto.setVehicleDetails(vehicle.getBrand() + " " + vehicle.getModel());
                    dto.setVehiclePlate(vehicle.getVehicleNumber());
                    dto.setVehicleYear(vehicle.getYear());
                    
                    if (vehicle.getCustomer() != null) {
                        com.wip.VehicleServiceManagement.entity.Customer customer = vehicle.getCustomer();
                        dto.setCustomerName(customer.getName());
                        dto.setCustomerEmail(customer.getEmail());
                        dto.setCustomerPhone(customer.getPhone());
                        dto.setCustomerAddress(customer.getAddress());
                    }
                }
            }
        }

        if (invoice.getGeneratedBy() != null)
            dto.setAdminId(invoice.getGeneratedBy().getAdminId());

        return dto;
    }
}