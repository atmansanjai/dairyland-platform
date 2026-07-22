package com.atman.server.Batch.DTO;

import com.atman.server.CustomerModule.Entity.CustomerEntity;
import com.atman.server.OrderModule.Entity.InvoiceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerInvoiceWriterDTO {
    private CustomerEntity customer;
    private InvoiceEntity invoice;
}
