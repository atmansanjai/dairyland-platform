package com.atman.server.Admin.Repository;

import com.atman.server.Admin.Entity.AdminMilkEntity;
import com.atman.server.OrderModule.Enum.MilkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminMilkRepository extends JpaRepository<AdminMilkEntity, UUID> , JpaSpecificationExecutor<AdminMilkEntity> {

    Optional<AdminMilkEntity> findByMilkType(MilkType milkType);
}
