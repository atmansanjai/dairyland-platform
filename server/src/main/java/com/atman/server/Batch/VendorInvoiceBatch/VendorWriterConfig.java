package com.atman.server.Batch.VendorInvoiceBatch;

import com.atman.server.OrderModule.Entity.InvoiceEntity;
import com.atman.server.VendorModule.Entity.VendorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class VendorWriterConfig {

    private final DataSource dataSource;

    @Bean
    public JdbcBatchItemWriter<InvoiceEntity> invoiceWriter() {
        return new JdbcBatchItemWriterBuilder<InvoiceEntity>().dataSource(dataSource)
                                                              .sql("""
                                                                      INSERT INTO invoices (user_id, user_role, from_date, to_date, total_amount, paid_amount , balance_amount, payment_status)
                                                                      VALUES (:userId, :userRole, :totalAmount, :fromDate, :toDate, :amountPaid, :amountToPay, :paymentStatus)
                                                                      """)
                                                              .beanMapped()
                                                              .build();
    }

    @Bean
    public JdbcBatchItemWriter<VendorEntity> vendorWriter() {
        return new JdbcBatchItemWriterBuilder<VendorEntity>().dataSource(dataSource)
                                                             .sql("UPDATE vendors SET last_billed_date = :lastBilledDate WHERE id = :id")
                                                             .beanMapped()
                                                             .build();
    }
}
