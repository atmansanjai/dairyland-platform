package com.atman.server.Batch.DTO;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SqlResultSetMapping(name = "CustomerInvoiceReaderDTOMapping", classes = @ConstructorResult(targetClass = CustomerInvoiceReaderDTO.class, columns = {@ColumnResult(name = "customerId", type = UUID.class), @ColumnResult(name = "lastBilledDate", type = java.time.LocalDateTime.class), @ColumnResult(name = "currentBilledDate", type = java.time.LocalDateTime.class), @ColumnResult(name = "totalAmount", type = BigDecimal.class)}))
public class CustomerInvoiceReaderDTO {
    private UUID customerId;
    private LocalDateTime lastBilledDate;
    private LocalDateTime currentBilledDate;
    private BigDecimal totalAmount;
}
