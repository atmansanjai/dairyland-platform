package com.atman.server.VendorModule.Resolver;

import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;
import com.atman.server.Specification.DTO.MapDTO;
import com.atman.server.VendorModule.Entity.StreetEntity;
import com.atman.server.VendorModule.Service.Impl.StreetService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class StreetResolver {

    private final StreetService streetService;

    @QueryMapping
    public ConnectionResponseDTO<StreetEntity> streets(@Argument Integer first, @Argument String after, @Argument Integer last, @Argument String before, @Argument MapDTO search, @Argument List<MapDTO> filter, @Argument List<MapDTO> sort) {
        ConnectionRequestDTO request = ConnectionRequestDTO.builder()
                                                           .after(after)
                                                           .before(before)
                                                           .first(first)
                                                           .last(last)
                                                           .search(search)
                                                           .filter(filter)
                                                           .sort(sort)
                                                           .build();
        return streetService.getAllStreets(request);
    }

    @QueryMapping
    public StreetEntity streetByID(@Argument(name = "id") UUID id) {
        return streetService.getStreetById(id);
    }

    @MutationMapping
    public StreetEntity saveStreet(@Argument(name = "streetName") String streetName) {
        return streetService.saveStreet(streetName);
    }

    @MutationMapping
    public StreetEntity updateStreet(@Argument(name = "id") UUID id, @Argument(name = "streetName") String streetName) {
        return streetService.updateStreetName(id, streetName);
    }

    @MutationMapping
    public StreetEntity deleteStreet(@Argument(name = "id") UUID id) {
        return streetService.deleteStreetById(id);
    }

    @MutationMapping
    public void assignVendorForStreet(@Argument(name = "vendorId") UUID vendorId, @Argument(name = "streetIds") List<UUID> streetIds) {
        streetService.assignVendorForStreets(vendorId, streetIds);
    }
}
