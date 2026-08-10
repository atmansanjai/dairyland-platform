package com.atman.server.VendorModule.Repository;

import com.atman.server.VendorModule.Entity.StreetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface StreetRepository extends JpaRepository<StreetEntity, UUID>, JpaSpecificationExecutor<StreetEntity> {

    Collection<StreetEntity> findAllByStreetNameContainingIgnoreCase(String streetName);

    @Modifying
    @Query(value = """
            WITH update_street AS (
                UPDATE street 
                SET vendor_id = :vendorId 
                WHERE id IN (:streetIds)
                RETURNING id
            ),
            update_orders AS (
                UPDATE orders 
                SET delivered_to = :vendorId 
                FROM update_street
                WHERE orders.street_id = update_street.id
            )
            SELECT 1;
            """, nativeQuery = true)
    void assignVendorToStreet(@Param("vendorId") UUID vendorId, @Param("streetIds") List<UUID> streetIds);
}
