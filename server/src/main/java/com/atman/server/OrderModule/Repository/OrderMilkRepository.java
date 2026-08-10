package com.atman.server.OrderModule.Repository;

import com.atman.server.OrderModule.Entity.OrderMilkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderMilkRepository extends JpaRepository<OrderMilkEntity, UUID> , JpaSpecificationExecutor<OrderMilkEntity> {
}
