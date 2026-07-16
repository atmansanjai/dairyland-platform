package com.atman.server.VendorModule.Repository;

import com.atman.server.VendorModule.Entity.StreetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StreetRepository extends JpaRepository<StreetEntity, UUID> {

}
