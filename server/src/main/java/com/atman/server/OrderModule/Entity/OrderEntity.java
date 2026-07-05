package com.atman.server.OrderModule;

import com.atman.server.UserModule.Entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders", indexes = @Index(name = "idx_customer_id", columnList = ("customer_id") , @Index(name = "idx_vendor_id" , columnList = ("vendor_id"))))
@SuperBuilder
public class OrderEntity extends BaseEntity {
    private UUID customerId;
    private UUID vendorId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private HashSet<OrderedMilkEntity> orderedMilk = new HashSet<>();
}
