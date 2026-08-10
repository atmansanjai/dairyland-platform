package com.atman.server.VendorModule.Resolver;

import com.atman.server.Admin.Enum.AccountStatus;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;
import com.atman.server.Specification.DTO.MapDTO;
import com.atman.server.VendorModule.DTO.VendorCreationDTO;
import com.atman.server.VendorModule.Entity.VendorEntity;
import com.atman.server.VendorModule.Service.Impl.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VendorResolver {

    private final VendorService vendorService;

    @QueryMapping
    public ConnectionResponseDTO<VendorEntity> vendors(@Argument Integer first, @Argument String after, @Argument Integer last, @Argument String before, @Argument MapDTO search, @Argument List<MapDTO> filter, @Argument List<MapDTO> sort) {
        ConnectionRequestDTO request = ConnectionRequestDTO.builder()
                                                           .after(after)
                                                           .before(before)
                                                           .first(first)
                                                           .last(last)
                                                           .search(search)
                                                           .filter(filter)
                                                           .sort(sort)
                                                           .build();
        return vendorService.getAllVendors(request);
    }

    @QueryMapping
    public VendorEntity vendorByID(@Argument(name = "id") UUID id) {
        return vendorService.getVendorById(id);
    }

    @MutationMapping
    public VendorEntity updateVendor(@Argument(name = "id") UUID id, @Argument(name = "vendor") VendorCreationDTO vendorCreationDTO) {
        return vendorService.updateVendor(id, vendorCreationDTO);
    }

    @MutationMapping
    public VendorEntity saveVendor(@Argument(name = "vendor") VendorCreationDTO vendorCreationDTO) {
        return vendorService.saveVendor(vendorCreationDTO);
    }

    @MutationMapping
    public VendorEntity deleteVendor(@Argument(name = "id") UUID id) {
        return vendorService.deleteVendorById(id);
    }

    @MutationMapping
    public VendorEntity updateVendorAccountStatus(@Argument(name = "id") UUID id, @Argument(name = "accountStatus") AccountStatus accountStatus) {
        return vendorService.updateAccountStatus(id, accountStatus);
    }
}
