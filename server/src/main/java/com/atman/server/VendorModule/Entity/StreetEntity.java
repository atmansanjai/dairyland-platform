package com.atman.server.VendorModule.Entity;

import com.atman.server.Admin.Entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "streets", indexes = {@Index(name = "idx_street_name", columnList = "street_name"), @Index(name = "idx_street_vendor_id", columnList = "vendor_id")})
@SuperBuilder
public class StreetEntity extends BaseEntity {
    @Column(name = "street_name", nullable = false, unique = true)
    private String streetName;

    @Column(name = "vendor_id")
    private UUID vendor;
}
