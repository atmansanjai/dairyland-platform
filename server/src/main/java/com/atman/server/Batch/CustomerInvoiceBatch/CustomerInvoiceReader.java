package com.atman.server.Batch.CustomerInvoiceBatch;

import com.atman.server.Batch.DTO.CustomerInvoiceReaderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.infrastructure.item.database.JdbcPagingItemReader;
import org.springframework.batch.infrastructure.item.database.Order;
import org.springframework.batch.infrastructure.item.database.PagingQueryProvider;
import org.springframework.batch.infrastructure.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.infrastructure.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CustomerInvoiceReader {

    private final DataSource dataSource;

    @Bean
    public JdbcPagingItemReader<CustomerInvoiceReaderDTO> getCustomerOrderReader() throws Exception {
        return new JdbcPagingItemReaderBuilder<CustomerInvoiceReaderDTO>().name("orderReader")
                                                                          .dataSource(dataSource)
                                                                          .queryProvider(queryProvider())
                                                                          .parameterValues(Map.of("orderStatus", "DELIVERED"))
                                                                          .rowMapper(new BeanPropertyRowMapper<>(CustomerInvoiceReaderDTO.class))
                                                                          .pageSize(100)
                                                                          .build();
    }

    private PagingQueryProvider queryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
        factory.setDataSource(dataSource);
        factory.setSelectClause("SELECT c.id as customerId, c.last_billed_date as lastBilledDate, CURRENT_DATE as currentBilledDate, SUM(oi.order_quantity * oi.price_per_quantity) as totalAmount");
        factory.setFromClause("FROM customers c JOIN orders o On o.delivered_to = c.id JOIN order_milk oi On oi.order_id = o.id");
        factory.setWhereClause("WHERE  o.order_status = :orderStatus AND o.created_at BETWEEN c.last_billed_date AND CURRENT_DATE");
        factory.setGroupClause("c.id");
        factory.setSortKeys(Map.of("c.id", Order.ASCENDING));
        return factory.getObject();
    }
}