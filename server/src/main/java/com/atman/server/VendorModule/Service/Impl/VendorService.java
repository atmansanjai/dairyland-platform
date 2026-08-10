package com.atman.server.VendorModule.Service.Impl;

import com.atman.server.Admin.Enum.AccountStatus;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;
import com.atman.server.VendorModule.DTO.VendorCreationDTO;
import com.atman.server.VendorModule.Entity.VendorEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public interface VendorService {

    VendorEntity saveVendor(VendorCreationDTO vendorCreationDTO);

    void updateBillingDetails(UUID vendorId, LocalDateTime currentBilled);

    ConnectionResponseDTO<VendorEntity> getAllVendors(ConnectionRequestDTO connectionRequestDTO);

    VendorEntity getVendorById(UUID vendorId);

    VendorEntity updateVendor(UUID vendorId, VendorCreationDTO vendorCreationDTO);

    VendorEntity getVendorByContactNumber(String contactNumber);

    VendorEntity deleteVendorById(UUID vendorId);

    VendorEntity updateAccountStatus(UUID vendorId, AccountStatus accountStatus);
}
