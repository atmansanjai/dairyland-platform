package com.atman.server.VendorModule.Repository;

import com.atman.server.Admin.Enum.AccountStatus;
import com.atman.server.VendorModule.Entity.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VendorRepository extends JpaRepository<VendorEntity, UUID>, JpaSpecificationExecutor<VendorEntity> {
    Optional<VendorEntity> findByContactNumber(String contactNumber);

    @Modifying
    @Query("""
                UPDATE VendorEntity v
                SET v.accountStatus = :accountStatus 
                WHERE v.contactNumber = :contactNumber
            """)
    void updateStatusByContactNumber(@Param("contactNumber") String contactNumber, @Param("accountStatus") AccountStatus accountStatus);

}
