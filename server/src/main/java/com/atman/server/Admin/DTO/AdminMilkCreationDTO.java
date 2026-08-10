package com.atman.server.Admin.DTO;

import com.atman.server.OrderModule.Enum.MilkType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AdminMilkCreationDTO {
    private MilkType milkType;
    private BigDecimal pricePerQuantity;
}
