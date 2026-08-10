package com.atman.server.OrderModule.Service.Impl;

import com.atman.server.CustomerModule.Entity.SubscriptionEntity;
import com.atman.server.OrderModule.DTO.OrderMilkDTO;
import com.atman.server.OrderModule.Entity.OrderMilkEntity;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;

import java.util.Collection;
import java.util.UUID;

public interface OrderMilkService {

    ConnectionResponseDTO<OrderMilkEntity> getAllOrderedMilk(ConnectionRequestDTO connectionRequestDTO);

    OrderMilkEntity saveOrderMilk(OrderMilkDTO orderMilkDTO);

    OrderMilkEntity deleteOrderMilk(UUID orderedMilkId);

    OrderMilkEntity updateOrderedMilk(UUID orderedMilkId, OrderMilkDTO orderMilkDTO);

    OrderMilkEntity getOrderedMilkById(UUID orderedMilkId);

}
