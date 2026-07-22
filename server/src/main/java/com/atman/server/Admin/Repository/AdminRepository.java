package com.atman.server.Admin.Repository;

import com.atman.server.Admin.Entity.AdminEntity;
import com.atman.server.Admin.Enum.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, UUID> , JpaSpecificationExecutor<AdminEntity> {

    Optional<AdminEntity> findByContactNumber(String contactNumber);

    @Modifying
    @Query("""
                UPDATE AdminEntity a
                SET a.accountStatus = :accountStatus
                WHERE a.contactNumber = :contactNumber
            """)
    void updateStatusByContactNumber(@Param("contactNumber") String contactNumber, @Param("accountStatus") AccountStatus accountStatus);

}
