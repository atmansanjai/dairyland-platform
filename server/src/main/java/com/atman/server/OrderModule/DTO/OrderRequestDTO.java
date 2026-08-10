package com.atman.server.OrderModule.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderRequestDTO {
    private UUID deliveredTo;
    private UUID deliveredBy;
}
