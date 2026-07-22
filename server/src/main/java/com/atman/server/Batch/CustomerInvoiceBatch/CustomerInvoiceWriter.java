package com.atman.server.Batch.CustomerInvoiceBatch;

import com.atman.server.CustomerModule.Entity.CustomerEntity;
import com.atman.server.Batch.DTO.CustomerInvoiceWriterDTO;
import com.atman.server.OrderModule.Entity.InvoiceEntity;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CustomerInvoiceWriter implements ItemWriter<CustomerInvoiceWriterDTO> {
    private final JdbcBatchItemWriter<InvoiceEntity> invoiceWriter;
    private final JdbcBatchItemWriter<CustomerEntity> customerWriter;

    @Override
    public void write(@NonNull Chunk<? extends CustomerInvoiceWriterDTO> chunk) throws Exception {
        var invoices = chunk.getItems()
                            .stream()
                            .map(CustomerInvoiceWriterDTO::getInvoice)
                            .toList();
        var customers = chunk.getItems()
                             .stream()
                             .map(CustomerInvoiceWriterDTO::getCustomer)
                             .toList();
        invoiceWriter.write(new Chunk<>(invoices));
        customerWriter.write(new Chunk<>(customers));
    }
}