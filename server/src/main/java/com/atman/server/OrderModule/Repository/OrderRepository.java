package com.atman.server.OrderModule.Repository;

import com.atman.server.OrderModule.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {}
