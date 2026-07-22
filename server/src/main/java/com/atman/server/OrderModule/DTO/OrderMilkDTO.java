package com.atman.server.OrderModule.DTO;

import com.atman.server.OrderModule.Enum.DeliverySession;
import com.atman.server.OrderModule.Enum.MilkType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderMilkDTO {
    private UUID orderId;
    private MilkType milkType;
    private BigDecimal quantity;
    private DeliverySession deliverySession;
}
