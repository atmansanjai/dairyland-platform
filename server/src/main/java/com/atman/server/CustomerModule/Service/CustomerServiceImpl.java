package com.atman.server.CustomerModule.Service;

import com.atman.server.Admin.Enum.AccountStatus;
import com.atman.server.Admin.Enum.UserRole;
import com.atman.server.CustomerModule.DTO.CustomerCreationDTO;
import com.atman.server.CustomerModule.Entity.CustomerEntity;
import com.atman.server.CustomerModule.Repository.CustomerRepository;
import com.atman.server.CustomerModule.Service.Impl.CustomerService;
import com.atman.server.Specification.ConnectionResponseBuilder;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;
import com.atman.server.Specification.SpecificationBuilder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final SpecificationBuilder<CustomerEntity> specificationBuilder;
    private final ConnectionResponseBuilder connectionResponseBuilder;

    @Override
    public CustomerEntity createCustomer(CustomerCreationDTO customerCreationDTO) {
        CustomerEntity customer = CustomerEntity.builder()
                                                .contactNumber(customerCreationDTO.getContactNumber())
                                                .accountStatus(AccountStatus.ACTIVE)
                                                .username(customerCreationDTO.getUsername())
                                                .userRole(UserRole.CUSTOMER)
                                                .build();
        return customerRepository.save(customer);
    }

    @Override
    public void updateBillingDetails(UUID customerId, LocalDateTime currentBilled) {
        CustomerEntity customerById = getCustomerById(customerId);
        customerById.setLastBilledDate(currentBilled);
        customerRepository.save(customerById);
    }

    @Override
    public ConnectionResponseDTO<CustomerEntity> getAllCustomers(ConnectionRequestDTO connectionRequestDTO) {
        return connectionResponseBuilder.build(customerRepository, specificationBuilder, connectionRequestDTO);
    }

    @Override
    public CustomerEntity getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                                 .orElseThrow(() -> new RuntimeException("Customer not found" + customerId));
    }

    @Override
    public CustomerEntity getCustomerByContactNumber(String contactNumber) {
        return customerRepository.findByContactNumber(contactNumber)
                                 .orElseThrow(() -> new EntityNotFoundException("Customer not found" + contactNumber));
    }

    @Override
    public CustomerEntity deleteCustomerById(UUID customerId) {
        CustomerEntity customerById = getCustomerById(customerId);
        customerRepository.delete(customerById);
        return customerById;
    }

    @Override
    public CustomerEntity updateAccountStatus(UUID customerId, AccountStatus accountStatus) {
        CustomerEntity customerById = getCustomerById(customerId);
        customerById.setAccountStatus(accountStatus);
        return customerRepository.save(customerById);
    }
}
