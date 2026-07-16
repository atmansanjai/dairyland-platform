package com.atman.server.OrderModule.Entity;

import com.atman.server.Admin.Entity.BaseEntity;
import com.atman.server.Admin.Enum.UserRole;
import com.atman.server.OrderModule.Enum.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "invoices", indexes = {@Index(name = "idx_invoice_user_id", columnList = "user_id")})
@SuperBuilder
public class InvoiceEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "balance_amount", nullable = false)
    private BigDecimal amountToPay;

    @Column(name = "paid_amount", nullable = false)
    private BigDecimal amountPaid;

    private LocalDateTime fromDate;

    private LocalDateTime toDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

}
