package com.atman.server.CustomerModule.Repository;

import com.atman.server.CustomerModule.Entity.SubscriptionMilk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MilkRepository extends JpaRepository<SubscriptionMilk, UUID> {}
