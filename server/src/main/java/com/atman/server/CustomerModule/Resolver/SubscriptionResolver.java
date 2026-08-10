package com.atman.server.CustomerModule.Resolver;

import com.atman.server.CustomerModule.DTO.SubscriptionDTO;
import com.atman.server.CustomerModule.Entity.SubscriptionEntity;
import com.atman.server.CustomerModule.Service.Impl.SubscriptionService;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;
import com.atman.server.Specification.DTO.MapDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class SubscriptionResolver {

    private final SubscriptionService subscriptionService;

    @QueryMapping
    public ConnectionResponseDTO<SubscriptionEntity> subscriptions(@Argument Integer first, @Argument String after, @Argument Integer last, @Argument String before, @Argument MapDTO search, @Argument List<MapDTO> filter, @Argument List<MapDTO> sort) {
        ConnectionRequestDTO request = ConnectionRequestDTO.builder()
                                                           .first(first)
                                                           .after(after)
                                                           .last(last)
                                                           .search(search)
                                                           .filter(filter)
                                                           .sort(sort)
                                                           .before(before)
                                                           .build();
        return subscriptionService.getAllSubscriptions(request);
    }

    @QueryMapping
    public SubscriptionEntity subscriptionById(@Argument(name = "id") UUID id) {
        return subscriptionService.getSubscriptionById(id);
    }

    @MutationMapping
    public SubscriptionEntity saveSubscription(@Argument(name = "subscription") SubscriptionDTO subscriptionDTO) {
        return subscriptionService.saveSubscription(subscriptionDTO);
    }

    @MutationMapping
    public SubscriptionEntity deleteSubscription(@Argument(name = "id") UUID id) {
        return subscriptionService.deleteSubscription(id);
    }

    @MutationMapping
    public SubscriptionEntity updateSubscription(@Argument(name = "id") UUID id, @Argument(name = "subscription") SubscriptionDTO subscriptionDTO) {
        return subscriptionService.updateSubscription(id, subscriptionDTO);
    }
}
