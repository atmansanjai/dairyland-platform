package com.atman.server.CustomerModule.Service.Impl;

import com.atman.server.CustomerModule.DTO.SubscriptionDTO;
import com.atman.server.CustomerModule.Entity.SubscriptionEntity;
import com.atman.server.OrderModule.Enum.DeliverySession;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

public interface SubscriptionService {
    SubscriptionEntity createSubscription(SubscriptionDTO subscriptionDTO);

    SubscriptionEntity updateSubscription(UUID subscriptionId, BigDecimal quantity, DeliverySession session);

    SubscriptionEntity deleteSubscription(UUID subscriptionId);

    SubscriptionEntity getSubscriptionById(UUID subscriptionId);

    ConnectionResponseDTO<SubscriptionEntity> getAllSubscriptions(ConnectionRequestDTO connectionRequestDTO);
}
