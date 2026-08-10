package com.atman.server.VendorModule.Service;

import com.atman.server.Admin.Enum.AccountStatus;
import com.atman.server.Admin.Enum.UserRole;
import com.atman.server.Specification.ConnectionResponseBuilder;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;
import com.atman.server.Specification.SpecificationBuilder;
import com.atman.server.VendorModule.DTO.VendorCreationDTO;
import com.atman.server.VendorModule.Entity.VendorEntity;
import com.atman.server.VendorModule.Repository.VendorRepository;
import com.atman.server.VendorModule.Service.Impl.VendorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final ConnectionResponseBuilder connectionResponseBuilder;
    private final SpecificationBuilder<VendorEntity> specificationBuilder;

    @Override
    public VendorEntity saveVendor(VendorCreationDTO vendorCreationDTO) {
        VendorEntity vendorEntity = VendorEntity.builder()
                                                .userRole(UserRole.VENDOR)
                                                .contactNumber(vendorCreationDTO.getContactNumber())
                                                .username(vendorCreationDTO.getUsername())
                                                .commissionPercentage(vendorCreationDTO.getCommissionPercentage())
                                                .billingCycle(vendorCreationDTO.getBillingCycle())
                                                .lastBilledDate(LocalDateTime.now())
                                                .accountStatus(AccountStatus.ACTIVE)
                                                .build();
        return vendorRepository.save(vendorEntity);
    }

    @Override
    public void updateBillingDetails(UUID vendorId, LocalDateTime currentBilled) {
        VendorEntity vendorById = getVendorById(vendorId);
        vendorById.setNextBillingDate(currentBilled);
        vendorRepository.save(vendorById);
    }

    @Override
    public ConnectionResponseDTO<VendorEntity> getAllVendors(ConnectionRequestDTO connectionRequestDTO) {
        return connectionResponseBuilder.build(vendorRepository, specificationBuilder, connectionRequestDTO);
    }

    @Override
    public VendorEntity getVendorById(UUID vendorId) {
        return vendorRepository.findById(vendorId)
                               .orElseThrow(() -> new EntityNotFoundException("Vendor not found" + vendorId));
    }

    @Override
    public VendorEntity updateVendor(UUID vendorId, VendorCreationDTO vendorCreationDTO) {
        VendorEntity vendorById = getVendorById(vendorId);
        vendorById.setContactNumber(vendorCreationDTO.getContactNumber());
        vendorById.setUsername(vendorCreationDTO.getUsername());
        vendorById.setCommissionPercentage(vendorCreationDTO.getCommissionPercentage());
        vendorById.setBillingCycle(vendorCreationDTO.getBillingCycle());
        return vendorRepository.save(vendorById);
    }

    @Override
    public VendorEntity getVendorByContactNumber(String contactNumber) {
        return vendorRepository.findByContactNumber(contactNumber)
                               .orElseThrow(() -> new EntityNotFoundException("Vendor not found" + contactNumber));
    }

    @Override
    public VendorEntity deleteVendorById(UUID vendorId) {
        VendorEntity vendorById = getVendorById(vendorId);
        vendorRepository.delete(vendorById);
        return vendorById;
    }

    @Override
    public VendorEntity updateAccountStatus(UUID vendorId, AccountStatus accountStatus) {
        VendorEntity vendorEntity = getVendorById(vendorId);
        vendorEntity.setAccountStatus(accountStatus);
        return vendorRepository.save(vendorEntity);
    }
}
