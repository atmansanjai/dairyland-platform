package com.atman.server.Admin.Repository;

import com.atman.server.Admin.Entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<AdminEntity, UUID> {

    boolean existsByContactNumber(String contactNumber);

    Optional<AdminEntity> findByContactNumber(String contactNumber);
}
