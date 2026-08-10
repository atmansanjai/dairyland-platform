package com.atman.server.Admin.Service;

import com.atman.server.Admin.DTO.AdminCreationDTO;
import com.atman.server.Admin.Entity.AdminEntity;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;

import java.util.UUID;

public interface AdminService {
    AdminEntity saveAdmin(AdminCreationDTO adminCreationDTO);

    AdminEntity getAdminByContactNumber(String contactNumber);

    AdminEntity updateAdmin(UUID id, AdminCreationDTO adminCreationDTO);

    AdminEntity getAdminById(UUID id);

    AdminEntity deleteAdminById(UUID id);

    ConnectionResponseDTO<AdminEntity> getAllAdmins(ConnectionRequestDTO connectionRequestDTO);
}
