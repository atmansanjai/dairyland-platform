package com.atman.server.OrderModule.Entity;

import com.atman.server.Admin.Entity.BaseEntity;
import com.atman.server.OrderModule.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set; // Use Set interface
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders", indexes = {@Index(name = "idx_order_customer_id", columnList = "delivered_to"), @Index(name = "idx_order_vendor_id", columnList = "delivered_by"), @Index(name = "idx_order_street_id", columnList = "street_id")})
@SuperBuilder
public class OrderEntity extends BaseEntity {

    @Column(name = "street_id")
    private UUID street;

    @Column(name = "delivered_to")
    private UUID deliveredTo;

    @Column(name = "delivered_by")
    private UUID deliveredBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderMilkEntity> orderedMilk = new HashSet<>();
}