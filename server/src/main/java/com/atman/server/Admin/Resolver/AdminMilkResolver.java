package com.atman.server.Admin.Resolver;


import com.atman.server.Admin.DTO.AdminMilkCreationDTO;
import com.atman.server.Admin.Entity.AdminMilkEntity;
import com.atman.server.Admin.Service.AdminMilkService;
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
public class AdminMilkResolver {

    private final AdminMilkService adminMilkService;

    @QueryMapping
    public ConnectionResponseDTO<AdminMilkEntity> adminMilks(@Argument Integer first, @Argument String after, @Argument Integer last, @Argument String before, @Argument MapDTO search, @Argument List<MapDTO> filter, @Argument List<MapDTO> sort) {
        ConnectionRequestDTO request = ConnectionRequestDTO.builder()
                                                           .first(first)
                                                           .after(after)
                                                           .last(last)
                                                           .before(before)
                                                           .search(search)
                                                           .filter(filter)
                                                           .sort(sort)
                                                           .build();
        return adminMilkService.getAllMilk(request);
    }

    @QueryMapping
    public AdminMilkEntity adminMilkByID(@Argument(name = "id") UUID id) {
        return adminMilkService.getMilkById(id);
    }

    @MutationMapping
    public AdminMilkEntity saveAdminMilk(@Argument(name = "adminMilk") AdminMilkCreationDTO adminMilkCreationDTO) {
        return adminMilkService.saveAdminMilk(adminMilkCreationDTO);
    }

    @MutationMapping
    public AdminMilkEntity updateAdminMilk(@Argument(name = "id") UUID id, @Argument(name = "adminMilk") AdminMilkCreationDTO adminMilkCreationDTO) {
        return adminMilkService.updateMilk(id, adminMilkCreationDTO);
    }

    @MutationMapping
    public AdminMilkEntity deleteAdminMilk(@Argument(name = "id") UUID id) {
        return adminMilkService.deleteMilk(id);
    }

}
