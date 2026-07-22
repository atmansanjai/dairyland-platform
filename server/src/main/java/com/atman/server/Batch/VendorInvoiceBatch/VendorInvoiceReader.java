package com.atman.server.Batch.VendorInvoiceBatch;

import com.atman.server.Batch.DTO.VendorInvoiceReaderDTO;
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
public class VendorInvoiceReader {

    private final DataSource dataSource;

    @Bean
    public JdbcPagingItemReader<VendorInvoiceReaderDTO> getOrderReader() throws Exception {
        return new JdbcPagingItemReaderBuilder<VendorInvoiceReaderDTO>().name("orderReader")
                                                                        .dataSource(dataSource)
                                                                        .queryProvider(queryProvider())
                                                                        .parameterValues(Map.of("orderStatus", "DELIVERED"))
                                                                        .rowMapper(new BeanPropertyRowMapper<>(VendorInvoiceReaderDTO.class))
                                                                        .pageSize(100)
                                                                        .build();
    }

    private PagingQueryProvider queryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
        factory.setDataSource(dataSource);
        factory.setSelectClause("SELECT v.id as vendorId, v.last_billed_date as lastBilledDate, CURRENT_DATE as currentBilledDate, SUM(oi.order_quantity * oi.price_per_quantity) as totalAmount");
        factory.setFromClause("FROM vendors v JOIN orders o On o.delivered_to = v.id JOIN order_milk oi On oi.order_id = o.id");
        factory.setWhereClause("WHERE o.order_status = :orderStatus AND o.created_at BETWEEN v.last_billed_date AND CURRENT_DATE");
        factory.setGroupClause("v.id");
        factory.setSortKeys(Map.of("v.id", Order.ASCENDING));
        return factory.getObject();
    }
}