package com.atman.server.Batch.CustomerInvoiceBatch;

import com.atman.server.Batch.DTO.CustomerInvoiceReaderDTO;
import com.atman.server.Batch.DTO.CustomerInvoiceWriterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.JdbcPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class CustomerInvoiceBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job generateCustomerInvoiceJob(Step generateCustomerInvoiceStep) {
        return new JobBuilder("customerInvoiceJob", jobRepository).start(generateCustomerInvoiceStep)
                                                                  .build();
    }

    @Bean
    public Step generateCustomerInvoiceStep(JdbcPagingItemReader<CustomerInvoiceReaderDTO> orderReader, ItemProcessor<CustomerInvoiceReaderDTO, CustomerInvoiceWriterDTO> processor, ItemWriter<CustomerInvoiceWriterDTO> writer) {
        return new StepBuilder("invoiceGenerationForCustomerStep", jobRepository).<CustomerInvoiceReaderDTO, CustomerInvoiceWriterDTO>chunk(100)
                                                                                 .transactionManager(transactionManager)
                                                                                 .reader(orderReader)
                                                                                 .processor(processor)
                                                                                 .writer(writer)
                                                                                 .build();
    }
}