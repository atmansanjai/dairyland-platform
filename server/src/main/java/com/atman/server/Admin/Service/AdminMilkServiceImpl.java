package com.atman.server.Admin.Service;

import com.atman.server.OrderModule.Enum.MilkType;
import com.atman.server.Admin.Entity.AdminMilkEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminMilkServiceImpl implements AdminMilkService{
    @Override
    public List<AdminMilkEntity> getAllMilk() {
        return List.of();
    }

    @Override
    public AdminMilkEntity getMilkById(UUID id) {
        return null;
    }

    @Override
    public AdminMilkEntity getMilkByMilkType(MilkType milkType) {
        return null;
    }

    @Override
    public BigDecimal getPricePerQuantity(MilkType milkType) {
        return null;
    }

    @Override
    public AdminMilkEntity addMilk(AdminMilkEntity milk) {
        return null;
    }

    @Override
    public AdminMilkEntity updateMilk(UUID id, AdminMilkEntity milk) {
        return null;
    }

    @Override
    public AdminMilkEntity deleteMilk(UUID id) {
        return null;
    }
}
