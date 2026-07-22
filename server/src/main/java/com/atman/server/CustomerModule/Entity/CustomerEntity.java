package com.atman.server.CustomerModule.Entity;

import com.atman.server.Admin.Entity.BaseEntity;
import com.atman.server.Admin.Enum.AccountStatus;
import com.atman.server.Admin.Enum.UserRole;
import com.atman.server.CustomerModule.Enum.BillingCycle;
import com.atman.server.Security.Config.AuthUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@SuperBuilder
@Table(name = "customers", indexes = {@Index(name = "idx_customer_contact_number", columnList = "contact_number")})
public class CustomerEntity extends BaseEntity implements AuthUser {

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "contact_number", nullable = false, length = 10, unique = true)
    private String contactNumber;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus;

    private BigDecimal deliveryOrderNumber;

    @Column(name = "street_id")
    private UUID street;

    @Column(name = "subscription_billing_cycle", nullable = false)
    @Enumerated(EnumType.STRING)
    private BillingCycle billingCycle;

    @Column(name = "last_billed_date")
    private LocalDateTime lastBilledDate;

    @Column(name = "next_billing_date")
    private LocalDateTime nextBillingDate;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SubscriptionEntity> subscription;


    @PrePersist
    @PreUpdate
    public void calculateNextBillingDate() {
        if(this.lastBilledDate != null && this.billingCycle != null) {
            switch(this.billingCycle) {
                case DAILY -> this.nextBillingDate = this.lastBilledDate.plusDays(1);
                case WEEKLY -> this.nextBillingDate = this.lastBilledDate.plusWeeks(1);
                case MONTHLY -> this.nextBillingDate = this.lastBilledDate.plusMonths(1);
                default -> throw new IllegalArgumentException("Unknown billing cycle: " + this.billingCycle);
            }
        }
    }

}
