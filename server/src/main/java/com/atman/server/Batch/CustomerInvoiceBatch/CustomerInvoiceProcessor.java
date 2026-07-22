package com.atman.server.Batch.CustomerInvoiceBatch;

import com.atman.server.Admin.Enum.UserRole;
import com.atman.server.CustomerModule.Entity.CustomerEntity;
import com.atman.server.Batch.DTO.CustomerInvoiceReaderDTO;
import com.atman.server.Batch.DTO.CustomerInvoiceWriterDTO;
import com.atman.server.OrderModule.Entity.InvoiceEntity;
import com.atman.server.OrderModule.Enum.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CustomerInvoiceProcessor implements ItemProcessor<CustomerInvoiceReaderDTO, CustomerInvoiceWriterDTO> {
    @Override
    public @Nullable CustomerInvoiceWriterDTO process(@NonNull CustomerInvoiceReaderDTO item) throws Exception {
        InvoiceEntity invoice = InvoiceEntity.builder()
                                             .userRole(UserRole.CUSTOMER)
                                             .userId(item.getCustomerId())
                                             .paymentStatus(PaymentStatus.PENDING)
                                             .totalAmount(item.getTotalAmount())
                                             .amountToPay(item.getTotalAmount())
                                             .amountPaid(BigDecimal.ZERO)
                                             .fromDate(item.getLastBilledDate())
                                             .toDate(item.getCurrentBilledDate())
                                             .build();


        CustomerEntity customer = CustomerEntity.builder()
                                                .id(item.getCustomerId())
                                                .lastBilledDate(invoice.getToDate())
                                                .build();

        return CustomerInvoiceWriterDTO.builder()
                                       .invoice(invoice)
                                       .customer(customer)
                                       .build();
    }
}
