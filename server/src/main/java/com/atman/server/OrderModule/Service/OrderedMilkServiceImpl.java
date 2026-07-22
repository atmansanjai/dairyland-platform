package com.atman.server.OrderModule.Service;

import com.atman.server.CustomerModule.Entity.SubscriptionEntity;
import com.atman.server.OrderModule.DTO.OrderMilkDTO;
import com.atman.server.OrderModule.Entity.OrderEntity;
import com.atman.server.OrderModule.Entity.OrderMilkEntity;
import com.atman.server.OrderModule.Enum.DeliverySession;
import com.atman.server.OrderModule.Enum.MilkType;
import com.atman.server.OrderModule.Repository.OrderedMilkRepository;
import com.atman.server.OrderModule.Service.Impl.OrderMilkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderedMilkServiceImpl implements OrderMilkService {

    private final OrderedMilkRepository orderedMilkRepository;


    private static OrderMilkEntity buildOrderedMilk(UUID orderId, DeliverySession deliverySession, MilkType milkType, BigDecimal quantity, BigDecimal pricePerQuantity) {
        return OrderMilkEntity.builder()
                              .orderSession(deliverySession)
                              .orderMilkType(milkType)
                              .orderQuantity(quantity)
                              .pricePerQuantity(pricePerQuantity)
                              .order(OrderEntity.builder()
                                                .id(orderId)
                                                .build())
                              .build();
    }

    @Override
    public OrderMilkEntity addMilkToOrder(OrderMilkDTO orderMilkDTO) {
        OrderMilkEntity orderMilkEntity = buildOrderedMilk(orderMilkDTO.getOrderId(), orderMilkDTO.getDeliverySession(), orderMilkDTO.getMilkType(), orderMilkDTO.getQuantity(), BigDecimal.ZERO);
        return orderedMilkRepository.save(orderMilkEntity);
    }

    @Override
    public OrderMilkEntity removeMilkFromOrder(UUID orderedMilkId) {
        OrderMilkEntity orderedMilkById = getOrderedMilkById(orderedMilkId);
        orderedMilkRepository.delete(orderedMilkById);
        return orderedMilkById;
    }

    @Override
    public OrderMilkEntity updateOrderedMilk(UUID orderedMilkId, OrderMilkDTO orderMilkDTO) {
        OrderMilkEntity orderedMilkById = getOrderedMilkById(orderedMilkId);
        orderedMilkById.setOrderQuantity(orderMilkDTO.getQuantity());
        orderedMilkById.setOrderSession(orderMilkDTO.getDeliverySession());
        orderedMilkById.setOrderMilkType(orderMilkDTO.getMilkType());
        orderedMilkById.setPricePerQuantity(BigDecimal.ZERO);
        return orderedMilkRepository.save(orderedMilkById);
    }

    @Override
    public OrderMilkEntity getOrderedMilkById(UUID orderedMilkId) {
        return orderedMilkRepository.findById(orderedMilkId)
                                    .orElseThrow(() -> new RuntimeException("Milk not found" + orderedMilkId));
    }


}
