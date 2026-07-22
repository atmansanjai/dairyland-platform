package com.atman.server.CustomerModule.Service;

import com.atman.server.CustomerModule.DTO.SubscriptionDTO;
import com.atman.server.CustomerModule.Entity.CustomerEntity;
import com.atman.server.CustomerModule.Entity.SubscriptionEntity;
import com.atman.server.CustomerModule.Repository.SubscriptionRepository;
import com.atman.server.CustomerModule.Service.Impl.SubscriptionService;
import com.atman.server.OrderModule.Enum.DeliverySession;
import com.atman.server.Specification.ConnectionResponseBuilder;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;
import com.atman.server.Specification.SpecificationBuilder;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SpecificationBuilder<SubscriptionEntity> specificationBuilder;
    private final ConnectionResponseBuilder connectionResponseBuilder;

    @Override
    public SubscriptionEntity createSubscription(SubscriptionDTO subscriptionDTO) {
        SubscriptionEntity subscriptionEntity = SubscriptionEntity.builder()
                                                                  .customer(CustomerEntity.builder()
                                                                                          .id(subscriptionDTO.getCustomerId())
                                                                                          .build())
                                                                  .milkType(subscriptionDTO.getMilkType())
                                                                  .deliverySession(subscriptionDTO.getDeliverySession())
                                                                  .milkQuantity(subscriptionDTO.getQuantity())
                                                                  .build();
        return subscriptionRepository.save(subscriptionEntity);
    }

    @Override
    public SubscriptionEntity updateSubscription(UUID subscriptionId, BigDecimal quantity, DeliverySession session) {
        SubscriptionEntity subscriptionById = getSubscriptionById(subscriptionId);
        subscriptionById.setMilkQuantity(quantity);
        subscriptionById.setDeliverySession(session);
        return subscriptionRepository.save(subscriptionById);
    }

    @Override
    public SubscriptionEntity deleteSubscription(UUID subscriptionId) {
        SubscriptionEntity subscriptionById = getSubscriptionById(subscriptionId);
        subscriptionRepository.delete(subscriptionById);
        return subscriptionById;
    }

    @Override
    public SubscriptionEntity getSubscriptionById(UUID subscriptionId) {
        return subscriptionRepository.findById(subscriptionId)
                                     .orElseThrow(() -> new EntityNotFoundException("subscription not found" + subscriptionId));
    }


    @Override
    public ConnectionResponseDTO<SubscriptionEntity> getAllSubscriptions(ConnectionRequestDTO connectionRequestDTO) {
        return connectionResponseBuilder.build(subscriptionRepository, specificationBuilder, connectionRequestDTO);
    }
}
