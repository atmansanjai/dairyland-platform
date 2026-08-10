package com.atman.server.OrderModule.Service;

import com.atman.server.Admin.Service.AdminMilkService;
import com.atman.server.OrderModule.DTO.OrderMilkDTO;
import com.atman.server.OrderModule.Entity.OrderEntity;
import com.atman.server.OrderModule.Entity.OrderMilkEntity;
import com.atman.server.OrderModule.Enum.DeliverySession;
import com.atman.server.OrderModule.Enum.MilkType;
import com.atman.server.OrderModule.Repository.OrderMilkRepository;
import com.atman.server.OrderModule.Service.Impl.OrderMilkService;
import com.atman.server.Specification.ConnectionResponseBuilder;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;
import com.atman.server.Specification.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderedMilkServiceImpl implements OrderMilkService {

    private final OrderMilkRepository orderMilkRepository;
    private final AdminMilkService adminMilkService;
    private SpecificationBuilder<OrderMilkEntity> specificationBuilder;
    private ConnectionResponseBuilder connectionResponseBuilder;

    @Override
    public ConnectionResponseDTO<OrderMilkEntity> getAllOrderedMilk(ConnectionRequestDTO connectionRequestDTO) {
        return connectionResponseBuilder.build(orderMilkRepository, specificationBuilder, connectionRequestDTO);
    }

    @Override
    public OrderMilkEntity saveOrderMilk(OrderMilkDTO orderMilkDTO) {
        BigDecimal pricePerQuantity = adminMilkService.getPricePerQuantity(orderMilkDTO.getMilkType());
        OrderMilkEntity orderMilkEntity = buildOrderedMilk(orderMilkDTO.getOrderId(), orderMilkDTO.getDeliverySession(), orderMilkDTO.getMilkType(), orderMilkDTO.getQuantity(), pricePerQuantity);
        return orderMilkRepository.save(orderMilkEntity);
    }

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
    public OrderMilkEntity deleteOrderMilk(UUID orderedMilkId) {
        OrderMilkEntity orderedMilkById = getOrderedMilkById(orderedMilkId);
        orderMilkRepository.delete(orderedMilkById);
        return orderedMilkById;
    }

    @Override
    public OrderMilkEntity updateOrderedMilk(UUID orderedMilkId, OrderMilkDTO orderMilkDTO) {
        BigDecimal pricePerQuantity = adminMilkService.getPricePerQuantity(orderMilkDTO.getMilkType());
        OrderMilkEntity orderedMilkById = getOrderedMilkById(orderedMilkId);
        orderedMilkById.setOrderQuantity(orderMilkDTO.getQuantity());
        orderedMilkById.setOrderSession(orderMilkDTO.getDeliverySession());
        orderedMilkById.setOrderMilkType(orderMilkDTO.getMilkType());
        orderedMilkById.setPricePerQuantity(pricePerQuantity);
        return orderMilkRepository.save(orderedMilkById);
    }

    @Override
    public OrderMilkEntity getOrderedMilkById(UUID orderedMilkId) {
        return orderMilkRepository.findById(orderedMilkId)
                                  .orElseThrow(() -> new RuntimeException("Milk not found" + orderedMilkId));
    }


}
