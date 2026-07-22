package com.atman.server.Batch.DTO;

import com.atman.server.OrderModule.Entity.InvoiceEntity;
import com.atman.server.VendorModule.Entity.VendorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VendorInvoiceWriterDTO {
    private InvoiceEntity invoice;
    private VendorEntity vendor;
}
