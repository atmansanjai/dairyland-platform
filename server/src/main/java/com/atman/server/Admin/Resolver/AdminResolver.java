package com.atman.server.Admin.Resolver;

import com.atman.server.Admin.DTO.AdminCreationDTO;
import com.atman.server.Admin.Entity.AdminEntity;
import com.atman.server.Admin.Service.AdminService;
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
public class AdminResolver {

    private final AdminService adminService;

    @QueryMapping
    public ConnectionResponseDTO<AdminEntity> admins(@Argument Integer first, @Argument String after, @Argument Integer last, @Argument String before, @Argument MapDTO search, @Argument List<MapDTO> filter, @Argument List<MapDTO> sort) {
        ConnectionRequestDTO request = ConnectionRequestDTO.builder()
                                                           .first(first)
                                                           .after(after)
                                                           .last(last)
                                                           .before(before)
                                                           .search(search)
                                                           .filter(filter)
                                                           .sort(sort)
                                                           .build();
        return adminService.getAllAdmins(request);
    }

    @QueryMapping
    public AdminEntity adminById(@Argument(name = "id") UUID id) {
        return adminService.getAdminById(id);
    }

    @MutationMapping
    public AdminEntity deleteAdmin(@Argument(name = "id") UUID id) {
        return adminService.deleteAdminById(id);
    }

    @MutationMapping
    public AdminEntity saveAdmin(@Argument(name = "admin") AdminCreationDTO adminCreationDTO) {
        return adminService.saveAdmin(adminCreationDTO);
    }

    @MutationMapping
    public AdminEntity updateAdmin(@Argument(name = "id") UUID id, @Argument(name = "admin") AdminCreationDTO adminCreationDTO) {
        return adminService.updateAdmin(id, adminCreationDTO);
    }
}
