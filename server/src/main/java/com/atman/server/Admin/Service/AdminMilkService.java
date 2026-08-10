package com.atman.server.Admin.Service;

import com.atman.server.Admin.DTO.AdminMilkCreationDTO;
import com.atman.server.Admin.Entity.AdminMilkEntity;
import com.atman.server.OrderModule.Enum.MilkType;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;

import java.math.BigDecimal;
import java.util.UUID;

public interface AdminMilkService {
    ConnectionResponseDTO<AdminMilkEntity> getAllMilk(ConnectionRequestDTO connectionRequestDTO);

    AdminMilkEntity getMilkById(UUID id);

    BigDecimal getPricePerQuantity(MilkType milkType);

    AdminMilkEntity saveAdminMilk(AdminMilkCreationDTO adminMilkCreationDTO);

    AdminMilkEntity updateMilk(UUID id, AdminMilkCreationDTO adminMilkCreationDTO);

    AdminMilkEntity deleteMilk(UUID id);
}
