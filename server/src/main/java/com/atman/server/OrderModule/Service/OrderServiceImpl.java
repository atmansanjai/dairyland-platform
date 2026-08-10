package com.atman.server.OrderModule.Service;


import com.atman.server.Admin.Enum.AccountStatus;
import com.atman.server.Admin.Service.AdminMilkService;
import com.atman.server.CustomerModule.Entity.CustomerEntity;
import com.atman.server.CustomerModule.Service.Impl.CustomerService;
import com.atman.server.CustomerModule.Service.Impl.SubscriptionService;
import com.atman.server.OrderModule.DTO.OrderRequestDTO;
import com.atman.server.OrderModule.Entity.OrderEntity;
import com.atman.server.OrderModule.Entity.OrderMilkEntity;
import com.atman.server.OrderModule.Enum.OrderStatus;
import com.atman.server.OrderModule.Repository.OrderRepository;
import com.atman.server.OrderModule.Service.Impl.OrderMilkService;
import com.atman.server.OrderModule.Service.Impl.OrderService;
import com.atman.server.Specification.ConnectionResponseBuilder;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;
import com.atman.server.Specification.SpecificationBuilder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final AdminMilkService adminMilkService;
    private SpecificationBuilder<OrderEntity> specificationBuilder;
    private ConnectionResponseBuilder connectionResponseBuilder;

    @Override
    public OrderEntity generateOrder(UUID customerId) {
        CustomerEntity customerById = customerService.getCustomerById(customerId);
        if(!customerById.getAccountStatus()
                        .equals(AccountStatus.ACTIVE)) {return null;}

        OrderEntity orderEntity = OrderEntity.builder()
                                             .deliveredTo(customerId)
                                             .street(customerById.getStreet())
                                             .orderStatus(OrderStatus.PENDING)
                                             .build();
        Set<OrderMilkEntity> orderedMilks = customerById.getSubscription()
                                                        .stream()
                                                        .map(subscriptionEntity -> {
                                                            return OrderMilkEntity.builder()
                                                                                  .orderSession(subscriptionEntity.getDeliverySession())
                                                                                  .orderQuantity(subscriptionEntity.getMilkQuantity())
                                                                                  .orderMilkType(subscriptionEntity.getMilkType())
                                                                                  .pricePerQuantity(adminMilkService.getPricePerQuantity(subscriptionEntity.getMilkType()))
                                                                                  .order(orderEntity)
                                                                                  .build();
                                                        })
                                                        .collect(Collectors.toSet());
        orderEntity.setOrderMilks(orderedMilks);
        return orderRepository.save(orderEntity);
    }

    @Override
    public OrderEntity deleteOrder(UUID orderId) {
        OrderEntity orderEntity = getOrderById(orderId);
        orderRepository.delete(orderEntity);
        return orderEntity;
    }

    @Override
    public OrderEntity updateOrder(UUID orderId, OrderRequestDTO orderRequestDTO) {
        OrderEntity orderById = getOrderById(orderId);
        orderById.setDeliveredTo(orderRequestDTO.getDeliveredTo());
        orderById.setDeliveredBy(orderRequestDTO.getDeliveredBy());
        return orderRepository.save(orderById);
    }

    @Override
    public OrderEntity updateOrderStatus(UUID orderId, OrderStatus orderStatus) {
        OrderEntity order = getOrderById(orderId);
        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    @Override
    public OrderEntity getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                              .orElseThrow(() -> new EntityNotFoundException("Order" + orderId + "not found"));
    }

    @Override
    public ConnectionResponseDTO<OrderEntity> getAllOrders(ConnectionRequestDTO connectionRequestDTO) {
        return connectionResponseBuilder.build(orderRepository, specificationBuilder, connectionRequestDTO);
    }

    @Override
    public BigDecimal getOrderTotalForCustomer(UUID customerId, LocalDateTime lastBilledDate, LocalDateTime currentDate) {
        return orderRepository.getTotalAmountForCustomer(customerId, lastBilledDate, currentDate);
    }

    @Override
    public BigDecimal getOrderTotalForVendor(UUID vendor, LocalDateTime lastBilledDate, LocalDateTime currentDate) {
        return orderRepository.getTotalAmountForVendor(vendor, lastBilledDate, currentDate);
    }
}

