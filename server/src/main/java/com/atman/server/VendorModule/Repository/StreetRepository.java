package com.atman.server.VendorModule.Repository;

import com.atman.server.VendorModule.Entity.StreetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface StreetRepository extends JpaRepository<StreetEntity, UUID> , JpaSpecificationExecutor<StreetEntity> {

    Collection<StreetEntity> findAllByStreetNameContainingIgnoreCase(String streetName);

    @Modifying
    @Query(value = """
        UPDATE streets 
        SET vendor_id = :vendorId 
        WHERE id IN (:streets)
        """, nativeQuery = true)
    void assignVendorToStreets(@Param("vendorId") UUID vendorId, @Param("streets") Collection<UUID> streets);
}
