package com.atman.server.CustomerModule.DTO;

import com.atman.server.CustomerModule.Enum.BillingCycle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerCreationDTO {
    private String username;
    private String password;
    private String contactNumber;
    private BillingCycle billingCycle;
    private UUID streetId;
    private SubscriptionDTO subscription;
}
