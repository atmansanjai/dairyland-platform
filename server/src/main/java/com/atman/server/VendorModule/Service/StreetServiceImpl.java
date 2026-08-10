package com.atman.server.VendorModule.Service;

import com.atman.server.Specification.ConnectionResponseBuilder;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;
import com.atman.server.Specification.SpecificationBuilder;
import com.atman.server.VendorModule.Entity.StreetEntity;
import com.atman.server.VendorModule.Repository.StreetRepository;
import com.atman.server.VendorModule.Service.Impl.StreetService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class StreetServiceImpl implements StreetService {

    private final StreetRepository streetRepository;
    private final SpecificationBuilder<StreetEntity> specificationBuilder;
    private final ConnectionResponseBuilder connectionResponseBuilder;

    @Override
    public StreetEntity saveStreet(String StreetName) {
        StreetEntity streetEntity = new StreetEntity();
        streetEntity.setStreetName(StreetName);
        return streetRepository.save(streetEntity);
    }

    @Override
    public StreetEntity getStreetById(UUID id) {
        return streetRepository.findById(id)
                               .orElseThrow(() -> new EntityNotFoundException("Street not found" + id));
    }

    @Override
    public StreetEntity deleteStreetById(UUID id) {
        StreetEntity streetById = getStreetById(id);
        streetRepository.delete(streetById);
        return streetById;
    }

    @Override
    public StreetEntity updateStreetName(UUID id, String streetName) {
        StreetEntity streetById = getStreetById(id);
        streetById.setStreetName(streetName);
        return streetRepository.save(streetById);
    }

    @Override
    public ConnectionResponseDTO<StreetEntity> getAllStreets(ConnectionRequestDTO connectionRequestDTO) {
        return connectionResponseBuilder.build(streetRepository, specificationBuilder, connectionRequestDTO);
    }

    @Override
    public Boolean assignVendorForStreets(UUID vendorId, List<UUID> streets) {
        streetRepository.assignVendorToStreet(vendorId, streets);
        return true;
    }
}