package com.atman.server.CustomerModule.Entity;


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

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@SuperBuilder
@Table(name = "subscription_milk", indexes = @Index(name = "idx_subscripiton_customer_id", columnList = "customer_id"))
public class SubscriptionMilk extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @Column(name = "subscribed_milk_type")
    @Enumerated(EnumType.STRING)
    private MilkType milkType;

    @Column(name = "subscribed_quantity")
    private BigDecimal milkQuantity;

    @Column(name = "subscribed_session")
    @Enumerated(EnumType.STRING)
    private DeliverySession deliverySession;
}
