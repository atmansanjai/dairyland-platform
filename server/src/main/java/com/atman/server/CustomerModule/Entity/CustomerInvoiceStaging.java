package com.atman.server.CustomerModule.Entity;

import com.atman.server.Admin.Entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "customer_invoice_staging", indexes = {@Index(name = "idx_customer_staging_customer_id", columnList = "customer_id")})
public class CustomerInvoiceStaging  extends BaseEntity {
    @Column(name = "customer_id")
    private UUID customerId;
    @Column(name = "invoice_id")
    private UUID invoiceId;
    @Column(name = "invoice_created_at")
    private LocalDateTime invoiceCreatedAt;
}
