package com.atman.server.VendorModule.DTO;

import com.atman.server.CustomerModule.Enum.BillingCycle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VendorCreationDTO {
    private String username;
    private String password;
    private String contactNumber;
    private BigDecimal commissionPercentage;
    private BillingCycle billingCycle;
}
