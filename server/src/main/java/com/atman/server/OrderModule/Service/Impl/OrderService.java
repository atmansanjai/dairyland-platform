package com.atman.server.OrderModule.Service.Impl;

import com.atman.server.OrderModule.Entity.OrderEntity;
import com.atman.server.OrderModule.Enum.OrderStatus;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

public interface OrderService {

    OrderEntity generateOrder(UUID customerId);

    OrderEntity deleteOrder(UUID orderId);

    OrderEntity updateOrderStatus(UUID orderId, OrderStatus orderStatus);

    OrderEntity getOrderById(UUID orderId);

    ConnectionResponseDTO<OrderEntity> getAllOrders(ConnectionRequestDTO  connectionRequestDTO);

    BigDecimal getOrderTotalForCustomer(UUID customerId, LocalDateTime lastBilledDate, LocalDateTime currentDate);

    BigDecimal getOrderTotalForVendor(UUID vendor, LocalDateTime lastBilledDate, LocalDateTime currentDate);
}

