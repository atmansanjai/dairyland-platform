package com.atman.server.Security.Config;

import com.atman.server.Admin.Enum.AccountStatus;
import com.atman.server.Admin.Enum.UserRole;
import com.atman.server.Admin.Repository.AdminRepository;
import com.atman.server.CustomerModule.Repository.CustomerRepository;
import com.atman.server.VendorModule.Repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdentityService {
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;
    private final AdminRepository adminRepository;

    public AuthUser loadUserByContactNumber(String contactNumber, UserRole userRole) {
        return switch(userRole) {
            case CUSTOMER -> customerRepository.findByContactNumber(contactNumber)
                                               .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

            case VENDOR -> vendorRepository.findByContactNumber(contactNumber)
                                           .orElseThrow(() -> new UsernameNotFoundException("Vendor not found"));

            case ADMIN -> adminRepository.findByContactNumber(contactNumber)
                                         .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
        };
    }

    public void logoutByContactNumber(String contactNumber, UserRole userRole) {
        switch(userRole) {
            case CUSTOMER -> customerRepository.updateStatusByContactNumber(contactNumber, AccountStatus.INACTIVE);
            case VENDOR -> vendorRepository.updateStatusByContactNumber(contactNumber, AccountStatus.INACTIVE);
            case ADMIN -> adminRepository.updateStatusByContactNumber(contactNumber, AccountStatus.INACTIVE);
        }
    }
}