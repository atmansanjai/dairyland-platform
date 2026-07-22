package com.atman.server.Batch.VendorInvoiceBatch;

import com.atman.server.Admin.Enum.UserRole;
import com.atman.server.Batch.DTO.VendorInvoiceReaderDTO;
import com.atman.server.Batch.DTO.VendorInvoiceWriterDTO;
import com.atman.server.OrderModule.Entity.InvoiceEntity;
import com.atman.server.OrderModule.Enum.PaymentStatus;
import com.atman.server.VendorModule.Entity.VendorEntity;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class VendorInvoiceProcessor implements ItemProcessor<VendorInvoiceReaderDTO, VendorInvoiceWriterDTO> {
    @Override
    public @Nullable VendorInvoiceWriterDTO process(@NonNull VendorInvoiceReaderDTO item) throws Exception {
        InvoiceEntity invoice = InvoiceEntity.builder()
                                             .userRole(UserRole.VENDOR)
                                             .userId(item.getVendorId())
                                             .paymentStatus(PaymentStatus.PENDING)
                                             .totalAmount(item.getTotalAmount())
                                             .amountToPay(item.getTotalAmount())
                                             .amountPaid(BigDecimal.ZERO)
                                             .fromDate(item.getLastBilledDate())
                                             .toDate(item.getCurrentBilledDate())
                                             .build();


        VendorEntity vendor = VendorEntity.builder()
                                          .id(item.getVendorId())
                                          .lastBilledDate(invoice.getToDate())
                                          .build();

        return VendorInvoiceWriterDTO.builder()
                                     .invoice(invoice)
                                     .vendor(vendor)
                                     .build();
    }
}
