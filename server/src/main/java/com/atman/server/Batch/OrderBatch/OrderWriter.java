package com.atman.server.Batch.OrderBatch;

import com.atman.server.OrderModule.Entity.OrderEntity;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OrderWriter {

    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public JpaItemWriter<OrderEntity> orderWriterForCustomer() {
        return new JpaItemWriterBuilder<OrderEntity>().entityManagerFactory(entityManagerFactory)
                                                      .build();
    }
}