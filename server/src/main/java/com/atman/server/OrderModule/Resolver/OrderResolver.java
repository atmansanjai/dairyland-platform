package com.atman.server.OrderModule.Resolver;

import com.atman.server.OrderModule.DTO.OrderRequestDTO;
import com.atman.server.OrderModule.Entity.OrderEntity;
import com.atman.server.OrderModule.Enum.OrderStatus;
import com.atman.server.OrderModule.Service.Impl.OrderService;
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
public class OrderResolver {

    private final OrderService orderService;


    @QueryMapping
    public ConnectionResponseDTO<OrderEntity> orders(@Argument Integer first, @Argument String after, @Argument Integer last, @Argument String before, @Argument MapDTO search, @Argument List<MapDTO> filter, @Argument List<MapDTO> sort) {
        ConnectionRequestDTO request = ConnectionRequestDTO.builder()
                                                           .after(after)
                                                           .before(before)
                                                           .first(first)
                                                           .last(last)
                                                           .search(search)
                                                           .filter(filter)
                                                           .sort(sort)
                                                           .build();
        return orderService.getAllOrders(request);
    }

    @QueryMapping
    public OrderEntity orderByID(@Argument(name = "id") UUID id) {
        return orderService.getOrderById(id);
    }

    @MutationMapping
    public OrderEntity generateOrder(@Argument(name = "customer") UUID customerID) {
        return orderService.generateOrder(customerID);
    }

    @MutationMapping
    public OrderEntity updateOrderStatus(@Argument(name = "id") UUID id, @Argument(name = "status") OrderStatus orderStatus) {
        return orderService.updateOrderStatus(id, orderStatus);
    }

    @MutationMapping
    public OrderEntity updateOrder(@Argument(name = "id") UUID id, @Argument(name = "order") OrderRequestDTO orderRequestDTO) {
        return orderService.updateOrder(id, orderRequestDTO);
    }

    @MutationMapping
    public OrderEntity deleteOrder(@Argument(name = "id") UUID id) {
        return orderService.deleteOrder(id);
    }


}
