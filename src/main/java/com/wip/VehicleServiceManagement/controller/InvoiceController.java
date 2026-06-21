package com.wip.VehicleServiceManagement.controller;

import com.wip.VehicleServiceManagement.dto.InvoiceDTO;
import com.wip.VehicleServiceManagement.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing invoices and billing records.
 */
@RestController
@RequestMapping("/api/invoice")
@Tag(name = "Invoice Management", description = "APIs for generating and managing invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    /**
     * Generate a new invoice.
     *
     * @param dto the invoice data transfer object details
     * @return the created InvoiceDTO details
     */
    @PostMapping("/create")
    @Operation(summary = "Generate Invoice", description = "Generates a new invoice for a completed service history record.")
    public InvoiceDTO createInvoice(@Valid @RequestBody InvoiceDTO dto) {
        return invoiceService.createInvoice(dto);
    }

    /**
     * Retrieve all invoices.
     *
     * @return a list of all InvoiceDTO records
     */
    @GetMapping("/all")
    @Operation(summary = "Get All Invoices", description = "Retrieves all generated invoices from the system.")
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    /**
     * Retrieve a specific invoice by its ID.
     *
     * @param id the invoice identifier
     * @return the InvoiceDTO details
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get Invoice by ID", description = "Retrieves details of a single invoice by its ID.")
    public InvoiceDTO getInvoiceById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id);
    }

    /**
     * Update an existing invoice record.
     *
     * @param id the invoice identifier
     * @param dto the updated invoice details
     * @return the updated InvoiceDTO details
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "Update Invoice", description = "Updates details of an existing invoice record.")
    public InvoiceDTO updateInvoice(@PathVariable Long id,
                                    @Valid @RequestBody InvoiceDTO dto) {
        return invoiceService.updateInvoice(id, dto);
    }

    /**
     * Delete an invoice record by its ID.
     *
     * @param id the invoice identifier
     * @return a success confirmation message
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete Invoice", description = "Removes an invoice record from the system.")
    public String deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return "Invoice deleted successfully";
    }
}