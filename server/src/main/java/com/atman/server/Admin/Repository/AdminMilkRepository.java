package com.atman.server.Admin.Repository;

import com.atman.server.Admin.Entity.AdminMilkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminMilkRepository extends JpaRepository<AdminMilkEntity, UUID> {}
