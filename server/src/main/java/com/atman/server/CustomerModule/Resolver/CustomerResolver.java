package com.atman.server.CustomerModule.Resolver;

import com.atman.server.Admin.Enum.AccountStatus;
import com.atman.server.CustomerModule.DTO.CustomerCreationDTO;
import com.atman.server.CustomerModule.Entity.CustomerEntity;
import com.atman.server.CustomerModule.Service.Impl.CustomerService;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;
import com.atman.server.Specification.DTO.MapDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CustomerResolver {

    private final CustomerService customerService;

    @QueryMapping
    public CustomerEntity customerByContactNumber(@Argument(name = "contactNumber") String contactNumber) {
        return customerService.getCustomerByContactNumber(contactNumber);
    }

    @QueryMapping
    public ConnectionResponseDTO<CustomerEntity> customers(@Argument Integer first, @Argument String after, @Argument Integer last, @Argument String before, @Argument MapDTO search, @Argument List<MapDTO> filter, @Argument List<MapDTO> sort) {
        ConnectionRequestDTO request = ConnectionRequestDTO.builder()
                                                           .after(after)
                                                           .before(before)
                                                           .first(first)
                                                           .last(last)
                                                           .search(search)
                                                           .filter(filter)
                                                           .sort(sort)
                                                           .build();
        return customerService.getAllCustomers(request);
    }

    @QueryMapping
    public CustomerEntity customerByID(@Argument(name = "id") UUID id) {
        return customerService.getCustomerById(id);
    }

    @MutationMapping
    public CustomerEntity saveCustomer(@Argument(name = "customer") CustomerCreationDTO customerCreationDTO) {
        return customerService.createCustomer(customerCreationDTO);
    }

    @MutationMapping
    public CustomerEntity deleteCustomer(@Argument(name = "id") UUID id) {
        return customerService.deleteCustomerById(id);
    }

    @MutationMapping
    public CustomerEntity updateCustomer(@Argument(name = "id") UUID id, @Argument(name = "customer") CustomerCreationDTO customerCreationDTO) {
        return customerService.updateCustomer(id, customerCreationDTO);
    }

    @MutationMapping
    public CustomerEntity updateCustomerAccountStatus(@Argument(name = "id") UUID id, @Argument(name = "accountStatus") AccountStatus accountStatus) {
        return customerService.updateAccountStatus(id, accountStatus);
    }

}
