package com.atman.server.Batch.OrderBatch;

import com.atman.server.CustomerModule.Entity.CustomerEntity;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.infrastructure.item.database.JpaPagingItemReader;
import org.springframework.batch.infrastructure.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OrderReader {

    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public JpaPagingItemReader<CustomerEntity> orderReaderForCustomer() {
        return new JpaPagingItemReaderBuilder<CustomerEntity>().name("customerSubscriptionReader")
                                                               .entityManagerFactory(entityManagerFactory)
                                                               .pageSize(100)
                                                               .queryString("SELECT c FROM CustomerEntity c WHERE c.accountStatus = 'ACTIVE'")
                                                               .build();
    }


}
