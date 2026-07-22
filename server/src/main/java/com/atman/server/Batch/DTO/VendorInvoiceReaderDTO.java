package com.atman.server.Batch.DTO;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@SqlResultSetMapping(name = "VendorInvoiceDTOMapping", classes = @ConstructorResult(targetClass = VendorInvoiceReaderDTO.class, columns = {@ColumnResult(name = "vendorId", type = UUID.class), @ColumnResult(name = "lastBilledDate", type = java.time.LocalDateTime.class), @ColumnResult(name = "currentBilledDate", type = java.time.LocalDateTime.class), @ColumnResult(name = "totalAmount", type = BigDecimal.class)}))
public class VendorInvoiceReaderDTO {
    private UUID vendorId;
    private LocalDateTime lastBilledDate;
    private LocalDateTime currentBilledDate;
    private BigDecimal totalAmount;
}
