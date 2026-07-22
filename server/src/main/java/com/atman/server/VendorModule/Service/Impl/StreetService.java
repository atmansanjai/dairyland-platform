package com.atman.server.VendorModule.Service.Impl;


import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;
import com.atman.server.VendorModule.Entity.StreetEntity;

import java.util.Collection;
import java.util.UUID;

public interface StreetService {

    StreetEntity createStreet(String StreetName);

    StreetEntity getStreetById(UUID id);

    StreetEntity deleteStreetById(UUID id);

    StreetEntity updateStreetName(UUID id, String streetName);

    ConnectionResponseDTO<StreetEntity> getAllStreets(ConnectionRequestDTO connectionRequestDTO);

    void assignVendorForStreets(UUID vendorId, Collection<UUID> streets);

}
