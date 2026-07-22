package com.atman.server.CustomerModule.Service.Impl;

import com.atman.server.Admin.Enum.AccountStatus;
import com.atman.server.CustomerModule.DTO.CustomerCreationDTO;
import com.atman.server.CustomerModule.Entity.CustomerEntity;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

public interface CustomerService {

    CustomerEntity createCustomer(CustomerCreationDTO customerCreationDTO);

    void updateBillingDetails(UUID customerId, LocalDateTime currentBilled);

    ConnectionResponseDTO<CustomerEntity> getAllCustomers(ConnectionRequestDTO  connectionRequestDTO);

    CustomerEntity getCustomerById(UUID customerId);

    CustomerEntity getCustomerByContactNumber(String contactNumber);

    CustomerEntity deleteCustomerById(UUID customerId);

    CustomerEntity updateAccountStatus(UUID customerId, AccountStatus accountStatus);
}
