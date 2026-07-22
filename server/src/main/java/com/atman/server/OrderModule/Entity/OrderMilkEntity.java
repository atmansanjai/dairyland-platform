package com.atman.server.OrderModule.Entity;

import com.atman.server.Admin.Entity.BaseEntity;
import com.atman.server.OrderModule.Enum.DeliverySession;
import com.atman.server.OrderModule.Enum.MilkType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "order_milk", indexes = @Index(name = "idx_order_id", columnList = ("order_id")))
@SuperBuilder
public class OrderMilkEntity extends BaseEntity {


    @Enumerated(EnumType.STRING)
    @Column(name = "order_milk_type", nullable = false)
    private MilkType orderMilkType;

    @Column(name = "order_quantity", nullable = false)
    private BigDecimal orderQuantity;

    @Column(name = "price_per_quantity", nullable = false)
    private BigDecimal pricePerQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_session", nullable = false)
    private DeliverySession orderSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;
}
