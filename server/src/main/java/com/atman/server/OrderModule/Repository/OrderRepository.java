package com.atman.server.OrderModule.Repository;

import com.atman.server.OrderModule.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> , JpaSpecificationExecutor<OrderEntity> {
    @Query(value = """
            SELECT COALESCE(SUM(oi.ordered_quantity * oi.price_per_litre),0) AS totalAmount
                        FROM orders o
                                            JOIN ordered_items oi ON oi.order_id = o.id
                                                         WHERE o.delivered_to = :customerId
                                                                     AND o.created_at BETWEEN :lastBilledDate AND :currentDate
            """, nativeQuery = true)
    BigDecimal getTotalAmountForCustomer(@Param("customerId") UUID customerId, @Param("lastBilledDate") LocalDateTime lastBilledDate, @Param("currentDate") LocalDateTime currentDate);


    @Query(value = """
            SELECT COALESCE(SUM(oi.ordered_quantity * oi.price_per_litre),0) AS totalAmount
                        FROM orders o
                                            JOIN ordered_items oi ON oi.order_id = o.id
                                                         WHERE o.delivered_to = :vendorId
                                                                     AND o.created_at BETWEEN :lastBilledDate AND :currentDate
            """, nativeQuery = true)
    BigDecimal getTotalAmountForVendor(@Param("vendorId") UUID vendorId, @Param("lastBilledDate") LocalDateTime lastBilledDate, @Param("currentDate") LocalDateTime currentDate);
}
