package com.atman.server.Batch.OrderBatch;

import com.atman.server.CustomerModule.Entity.CustomerEntity;
import com.atman.server.OrderModule.Entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.JdbcPagingItemReader;
import org.springframework.batch.infrastructure.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class OrderBatchConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job generateOrderJob(Step generateOrderStep) {
        return new JobBuilder("generateOrderJob", jobRepository).start(generateOrderStep)
                                                                .build();
    }

    @Bean
    public Step generateOrderStep(JpaPagingItemReader<CustomerEntity> orderReaderForCustomer, ItemProcessor<CustomerEntity, OrderEntity> processor, ItemWriter<OrderEntity> orderWriterForCustomer) {
        return new StepBuilder("generateOrderStep", jobRepository).<CustomerEntity, OrderEntity>chunk(100)
                                                                  .transactionManager(transactionManager)
                                                                  .reader(orderReaderForCustomer)
                                                                  .processor(processor)
                                                                  .writer(orderWriterForCustomer)
                                                                  .build();
    }
}
