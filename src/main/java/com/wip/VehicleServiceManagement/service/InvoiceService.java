package com.wip.VehicleServiceManagement.service;

import com.wip.VehicleServiceManagement.dto.InvoiceDTO;

import java.util.List;
/**
 * InvoiceService.
 *
 * @author Sridevi Srikumar
 */

public interface InvoiceService {

    InvoiceDTO createInvoice(InvoiceDTO dto);

    List<InvoiceDTO> getAllInvoices();

    InvoiceDTO getInvoiceById(Long id);

    InvoiceDTO updateInvoice(Long id, InvoiceDTO dto);

    void deleteInvoice(Long id);
}