package com.wip.VehicleServiceManagement.service;

import com.wip.VehicleServiceManagement.dto.InvoiceDTO;

import java.util.List;

public interface InvoiceService {

    InvoiceDTO createInvoice(InvoiceDTO dto);

    List<InvoiceDTO> getAllInvoices();

    InvoiceDTO getInvoiceById(Long id);

    InvoiceDTO updateInvoice(Long id, InvoiceDTO dto);

    void deleteInvoice(Long id);
}