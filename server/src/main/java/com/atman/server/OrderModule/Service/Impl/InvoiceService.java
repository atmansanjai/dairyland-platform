package com.atman.server.OrderModule.Service.Impl;

import com.atman.server.OrderModule.Entity.InvoiceEntity;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;

import java.util.Collection;
import java.util.UUID;

public interface InvoiceService {

    InvoiceEntity generateInvoiceForCustomer(UUID customerId);

    InvoiceEntity generateInvoiceForVendor(UUID vendorId);

    InvoiceEntity deleteInvoice(UUID invoiceId);

    InvoiceEntity getInvoiceById(UUID invoiceId);

    ConnectionResponseDTO<InvoiceEntity> getAllInvoices(ConnectionRequestDTO connectionRequestDTO);

}
