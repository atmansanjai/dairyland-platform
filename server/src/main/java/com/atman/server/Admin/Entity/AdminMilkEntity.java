package com.atman.server.Admin.Entity;

import com.atman.server.OrderModule.Enum.MilkType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "admin_milk" , indexes = @Index(name = "idx_milk_type", columnList = "milk_type"))
public class AdminMilkEntity extends BaseEntity {
    @Column(name = "milk_type", nullable = false)
    private MilkType milkType;
    @Column(name = "price_per_quantity", nullable = false)
    private BigDecimal pricePerQuantity;
}
