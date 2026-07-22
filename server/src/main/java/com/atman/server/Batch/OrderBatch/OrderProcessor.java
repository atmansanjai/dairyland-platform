package com.atman.server.Batch.OrderBatch;

import com.atman.server.Admin.Service.AdminMilkService;
import com.atman.server.CustomerModule.Entity.CustomerEntity;
import com.atman.server.OrderModule.Entity.OrderEntity;
import com.atman.server.OrderModule.Entity.OrderMilkEntity;
import com.atman.server.OrderModule.Enum.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class OrderProcessor implements ItemProcessor<CustomerEntity, OrderEntity> {

    private final AdminMilkService adminMilkService;

    @Override
    public @Nullable OrderEntity process(CustomerEntity item) throws Exception {
        OrderEntity order = OrderEntity.builder()
                                       .id(UUID.randomUUID())
                                       .deliveredTo(item.getId())
                                       .street(item.getStreet())
                                       .orderStatus(OrderStatus.PENDING)
                                       .build();
        Set<OrderMilkEntity> orderedMilks = item.getSubscription()
                                                .stream()
                                                .map(subscriptionEntity -> {
                                                    return OrderMilkEntity.builder()
                                                                          .orderMilkType(subscriptionEntity.getMilkType())
                                                                          .orderQuantity(subscriptionEntity.getMilkQuantity())
                                                                          .orderSession(subscriptionEntity.getDeliverySession())
                                                                          .pricePerQuantity(adminMilkService.getPricePerQuantity(subscriptionEntity.getMilkType()))
                                                                          .order(order)
                                                                          .build();

                                                })
                                                .collect(Collectors.toSet());
        order.setOrderMilks(orderedMilks);
        return order;
    }
}
