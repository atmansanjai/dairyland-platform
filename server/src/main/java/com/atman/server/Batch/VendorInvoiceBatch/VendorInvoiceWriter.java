package com.atman.server.Batch.VendorInvoiceBatch;

import com.atman.server.Batch.DTO.VendorInvoiceWriterDTO;
import com.atman.server.OrderModule.Entity.InvoiceEntity;
import com.atman.server.VendorModule.Entity.VendorEntity;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class VendorInvoiceWriter implements ItemWriter<VendorInvoiceWriterDTO> {
    private final JdbcBatchItemWriter<InvoiceEntity> invoiceWriter;
    private final JdbcBatchItemWriter<VendorEntity> vendorWriter;

    @Override
    public void write(@NonNull Chunk<? extends VendorInvoiceWriterDTO> chunk) throws Exception {
        var invoices = chunk.getItems()
                            .stream()
                            .map(VendorInvoiceWriterDTO::getInvoice)
                            .toList();
        var vendors = chunk.getItems()
                           .stream()
                           .map(VendorInvoiceWriterDTO::getVendor)
                           .toList();
        invoiceWriter.write(new Chunk<>(invoices));
        vendorWriter.write(new Chunk<>(vendors));
    }
}