package com.atman.server.CustomerModule.Repository;

import com.atman.server.Admin.Enum.AccountStatus;
import com.atman.server.CustomerModule.Entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID>, JpaSpecificationExecutor<CustomerEntity> {

    Optional<CustomerEntity> findByContactNumber(String contactNumber);


    @Modifying
    @Query("""
                UPDATE CustomerEntity c 
                SET c.accountStatus = :accountStatus 
                WHERE c.contactNumber = :contactNumber
            """)
    void updateStatusByContactNumber(@Param("contactNumber") String contactNumber, @Param("accountStatus") AccountStatus accountStatus);

}
