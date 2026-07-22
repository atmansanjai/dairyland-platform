package com.atman.server.Batch.CustomerInvoiceBatch;

import com.atman.server.CustomerModule.Entity.CustomerEntity;
import com.atman.server.OrderModule.Entity.InvoiceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class CustomerWriterConfig {

    private final DataSource dataSource;

    @Bean
    public JdbcBatchItemWriter<InvoiceEntity> invoiceCustomerWriter() {
        return new JdbcBatchItemWriterBuilder<InvoiceEntity>().dataSource(dataSource)
                                                              .sql("""
                                                                      INSERT INTO invoices (user_id, user_role, from_date, to_date, total_amount, paid_amount , balance_amount, payment_status)
                                                                      VALUES (:userId, :userRole, :totalAmount, :fromDate, :toDate, :amountPaid, :amountToPay, :paymentStatus)
                                                                      """)
                                                              .beanMapped()
                                                              .build();
    }

    @Bean
    public JdbcBatchItemWriter<CustomerEntity> customerWriter() {
        return new JdbcBatchItemWriterBuilder<CustomerEntity>().dataSource(dataSource)
                                                               .sql("UPDATE customers SET last_billed_date = :lastBilledDate WHERE id = :id")
                                                               .beanMapped()
                                                               .build();
    }
}
