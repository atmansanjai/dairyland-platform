package com.atman.server.Admin.Service;

import com.atman.server.Admin.DTO.AdminCreationDTO;
import com.atman.server.Admin.Entity.AdminEntity;
import com.atman.server.Admin.Enum.AccountStatus;
import com.atman.server.Admin.Enum.UserRole;
import com.atman.server.Admin.Repository.AdminRepository;
import com.atman.server.Security.Config.JwtService;
import com.atman.server.Specification.ConnectionResponseBuilder;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;
import com.atman.server.Specification.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final ConnectionResponseBuilder connectionResponseBuilder;
    private final SpecificationBuilder<AdminEntity> specificationBuilder;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminEntity saveAdmin(AdminCreationDTO adminCreationDTO) {
        AdminEntity adminEntity = AdminEntity.builder()
                                             .username(adminCreationDTO.getUsername())
                                             .password(passwordEncoder.encode(adminCreationDTO.getPassword()))
                                             .contactNumber(adminCreationDTO.getContactNumber())
                                             .userRole(UserRole.ADMIN)
                                             .accountStatus(AccountStatus.INACTIVE)
                                             .build();
        return adminRepository.save(adminEntity);
    }

    @Override
    public AdminEntity getAdminByContactNumber(String contactNumber) {
        return adminRepository.findByContactNumber(contactNumber)
                              .orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    @Override
    public AdminEntity updateAdmin(UUID id, AdminCreationDTO adminCreationDTO) {
        AdminEntity adminById = getAdminById(id);
        adminById.setUsername(adminCreationDTO.getUsername());
        adminById.setContactNumber(adminCreationDTO.getContactNumber());
        adminById.setPassword(passwordEncoder.encode(adminCreationDTO.getPassword()));
        return adminRepository.save(adminById);
    }

    @Override
    public AdminEntity getAdminById(UUID id) {
        return adminRepository.findById(id)
                              .orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    @Override
    public AdminEntity deleteAdminById(UUID id) {
        AdminEntity adminById = getAdminById(id);
        adminRepository.delete(adminById);
        return adminById;
    }

    @Override
    public ConnectionResponseDTO<AdminEntity> getAllAdmins(ConnectionRequestDTO connectionRequestDTO) {
        return connectionResponseBuilder.build(adminRepository, specificationBuilder, connectionRequestDTO);
    }
}
