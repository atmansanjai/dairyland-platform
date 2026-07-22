package com.atman.server.CustomerModule.Repository;

import com.atman.server.CustomerModule.Entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, UUID>, JpaSpecificationExecutor<SubscriptionEntity> {


}
