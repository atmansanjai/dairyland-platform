package com.atman.server.Batch.VendorInvoiceBatch;

import com.atman.server.Batch.DTO.VendorInvoiceReaderDTO;
import com.atman.server.Batch.DTO.VendorInvoiceWriterDTO;
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
public class VendorInvoiceBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job generateVendorInvoiceJob(Step generateVendorInvoiceStep) {
        return new JobBuilder("vendorInvoiceJob", jobRepository).start(generateVendorInvoiceStep)
                                                                .build();
    }

    @Bean
    public Step generateVendorInvoiceStep(JdbcPagingItemReader<VendorInvoiceReaderDTO> orderReader, ItemProcessor<VendorInvoiceReaderDTO, VendorInvoiceWriterDTO> processor, ItemWriter<VendorInvoiceWriterDTO> writer) {
        return new StepBuilder("invoiceStep", jobRepository).<VendorInvoiceReaderDTO, VendorInvoiceWriterDTO>chunk(100)
                                                            .transactionManager(transactionManager)
                                                            .reader(orderReader)
                                                            .processor(processor)
                                                            .writer(writer)
                                                            .build();
    }
}