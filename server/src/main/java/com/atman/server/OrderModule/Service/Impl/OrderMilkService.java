package com.atman.server.OrderModule.Service.Impl;

import com.atman.server.CustomerModule.Entity.SubscriptionEntity;
import com.atman.server.OrderModule.DTO.OrderMilkDTO;
import com.atman.server.OrderModule.Entity.OrderMilkEntity;

import java.util.Collection;
import java.util.UUID;

public interface OrderMilkService {

    OrderMilkEntity addMilkToOrder(OrderMilkDTO orderMilkDTO);

    OrderMilkEntity removeMilkFromOrder(UUID orderedMilkId);

    OrderMilkEntity updateOrderedMilk(UUID orderedMilkId, OrderMilkDTO orderMilkDTO);

    OrderMilkEntity getOrderedMilkById(UUID orderedMilkId);
}
