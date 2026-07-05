package com.atman.server.OrderModule;

import com.atman.server.UserModule.Entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "order_milk", indexes = @Index(name = "idx_order_id", columnList = ("order_id")))
@SuperBuilder
public class OrderedMilkEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private MilkType orderedMilkType;

    private Float orderedQuantity;
    private Float orderedPrice;

    @Enumerated(EnumType.STRING)
    private DeliverySession orderedSession;

    @ManyToOne()
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}
