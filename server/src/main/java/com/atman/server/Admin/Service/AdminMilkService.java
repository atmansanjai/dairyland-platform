package com.atman.server.Admin.Service;

import com.atman.server.OrderModule.Enum.MilkType;
import com.atman.server.Admin.Entity.AdminMilkEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AdminMilkService {
    List<AdminMilkEntity> getAllMilk();

    AdminMilkEntity getMilkById(UUID id);

    AdminMilkEntity getMilkByMilkType(MilkType milkType);

    BigDecimal getPricePerQuantity(MilkType milkType);

    AdminMilkEntity addMilk(AdminMilkEntity milk);

    AdminMilkEntity updateMilk(UUID id, AdminMilkEntity milk);

    AdminMilkEntity deleteMilk(UUID id);
}
