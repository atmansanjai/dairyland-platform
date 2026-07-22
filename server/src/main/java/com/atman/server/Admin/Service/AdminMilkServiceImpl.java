package com.atman.server.Admin.Service;

import com.atman.server.Admin.Entity.AdminMilkEntity;
import com.atman.server.Admin.Repository.AdminMilkRepository;
import com.atman.server.OrderModule.Enum.MilkType;
import com.atman.server.Specification.ConnectionResponseBuilder;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;
import com.atman.server.Specification.SpecificationBuilder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminMilkServiceImpl implements AdminMilkService {

    private final AdminMilkRepository adminMilkRepository;
    private final SpecificationBuilder<AdminMilkEntity> specificationBuilder;
    private final ConnectionResponseBuilder connectionResponseBuilder;

    @Override
    public ConnectionResponseDTO<AdminMilkEntity> getAllMilk(ConnectionRequestDTO connectionRequestDTO) {
        return connectionResponseBuilder.build(adminMilkRepository, specificationBuilder, connectionRequestDTO);
    }

    @Override
    public AdminMilkEntity getMilkById(UUID id) {
        return adminMilkRepository.findById(id)
                                  .orElseThrow(() -> new EntityNotFoundException("Milk not found" + id));
    }


    @Override
    public BigDecimal getPricePerQuantity(MilkType milkType) {
        AdminMilkEntity adminMilk = adminMilkRepository.findByMilkType(milkType)
                                                       .orElseThrow(() -> new EntityNotFoundException("Milk not found" + milkType));
        return adminMilk.getPricePerQuantity();
    }

    @Override
    public AdminMilkEntity addMilk(AdminMilkEntity milk) {
        AdminMilkEntity build = AdminMilkEntity.builder()
                                               .milkType(milk.getMilkType())
                                               .pricePerQuantity(milk.getPricePerQuantity())
                                               .build();
        return adminMilkRepository.save(build);
    }

    @Override
    public AdminMilkEntity updateMilk(UUID id, AdminMilkEntity milk) {
        AdminMilkEntity milkById = getMilkById(id);
        milkById.setMilkType(milk.getMilkType());
        milkById.setPricePerQuantity(milk.getPricePerQuantity());
        return adminMilkRepository.save(milkById);
    }

    @Override
    public AdminMilkEntity deleteMilk(UUID id) {
        AdminMilkEntity milkById = getMilkById(id);
        adminMilkRepository.delete(milkById);
        return milkById;
    }
}
