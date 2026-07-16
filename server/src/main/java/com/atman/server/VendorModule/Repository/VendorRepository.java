package com.atman.server.VendorModule.Repository;

import com.atman.server.VendorModule.Entity.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VendorRepository extends JpaRepository<VendorEntity, UUID> {

}
