package com.atman.server.OrderModule.Resolver;


import com.atman.server.OrderModule.DTO.OrderMilkDTO;
import com.atman.server.OrderModule.Entity.OrderMilkEntity;
import com.atman.server.OrderModule.Service.Impl.OrderMilkService;
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
public class OrderMilkResolver {

    private final OrderMilkService orderMilkService;

    @QueryMapping
    public ConnectionResponseDTO<OrderMilkEntity> orderMilks(@Argument Integer first, @Argument String after, @Argument Integer last, @Argument String before, @Argument MapDTO search, @Argument List<MapDTO> filter, @Argument List<MapDTO> sort) {
        ConnectionRequestDTO request = ConnectionRequestDTO.builder()
                                                           .after(after)
                                                           .before(before)
                                                           .first(first)
                                                           .last(last)
                                                           .search(search)
                                                           .filter(filter)
                                                           .sort(sort)
                                                           .build();
        return orderMilkService.getAllOrderedMilk(request);
    }


    @QueryMapping
    public OrderMilkEntity orderMilkById(@Argument(name = "id") UUID id) {
        return orderMilkService.getOrderedMilkById(id);
    }

    @MutationMapping
    public OrderMilkEntity saveOrderMilk(@Argument(name = "orderMilk") OrderMilkDTO orderMilkDTO) {
        return orderMilkService.saveOrderMilk(orderMilkDTO);
    }

    @MutationMapping
    public OrderMilkEntity updateOrderMilk(@Argument(name = "id") UUID id, @Argument(name = "orderMilk") OrderMilkDTO orderMilkDTO) {
        return orderMilkService.updateOrderedMilk(id, orderMilkDTO);
    }

    @MutationMapping
    public OrderMilkEntity deleteOrderMilk(@Argument(name = "id") UUID id) {
        return orderMilkService.deleteOrderMilk(id);
    }

}
