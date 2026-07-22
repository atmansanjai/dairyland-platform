package com.atman.server.OrderModule.Service;

import com.atman.server.Admin.Enum.UserRole;
import com.atman.server.CustomerModule.Entity.CustomerEntity;
import com.atman.server.CustomerModule.Service.Impl.CustomerService;
import com.atman.server.OrderModule.Entity.InvoiceEntity;
import com.atman.server.OrderModule.Enum.PaymentStatus;
import com.atman.server.OrderModule.Repository.InvoiceRepository;
import com.atman.server.OrderModule.Service.Impl.InvoiceService;
import com.atman.server.OrderModule.Service.Impl.OrderService;
import com.atman.server.Specification.ConnectionResponseBuilder;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;
import com.atman.server.Specification.SpecificationBuilder;
import com.atman.server.VendorModule.Entity.VendorEntity;
import com.atman.server.VendorModule.Service.Impl.VendorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final OrderService orderService;
    private final VendorService vendorService;
    private final CustomerService customerService;
    private final SpecificationBuilder<InvoiceEntity> specificationBuilder;
    private final ConnectionResponseBuilder connectionResponseBuilder;

    @Override
    public InvoiceEntity generateInvoiceForCustomer(UUID customerId) {
        CustomerEntity customerById = customerService.getCustomerById(customerId);
        LocalDateTime currentDate = LocalDateTime.now();
        BigDecimal orderAmount = orderService.getOrderTotalForCustomer(customerId, customerById.getLastBilledDate(), currentDate);
        InvoiceEntity savedInvoice = saveInvoice(customerById.getId(), customerById.getUserRole(), orderAmount, currentDate, customerById.getLastBilledDate());
        customerService.updateBillingDetails(customerId, savedInvoice.getToDate());
        return savedInvoice;
    }

    private InvoiceEntity saveInvoice(UUID userId, UserRole userRole, BigDecimal orderAmount, LocalDateTime currentDate, LocalDateTime lastBilledDate) {
        InvoiceEntity invoiceEntity = InvoiceEntity.builder()
                                                   .userId(userId)
                                                   .userRole(userRole)
                                                   .totalAmount(orderAmount)
                                                   .paymentStatus(PaymentStatus.PENDING)
                                                   .fromDate(lastBilledDate)
                                                   .toDate(currentDate)
                                                   .amountPaid(BigDecimal.ZERO)
                                                   .amountToPay(orderAmount)
                                                   .build();
        return invoiceRepository.save(invoiceEntity);
    }

    @Override
    public InvoiceEntity generateInvoiceForVendor(UUID vendorId) {
        VendorEntity vendorById = vendorService.getVendorById(vendorId);
        LocalDateTime currentDate = LocalDateTime.now();
        BigDecimal orderAmount = orderService.getOrderTotalForVendor(vendorId, vendorById.getLastBilledDate(), currentDate);
        InvoiceEntity savedInvoiceEntity = saveInvoice(vendorById.getId(), vendorById.getUserRole(), orderAmount, currentDate, vendorById.getLastBilledDate());
        vendorService.updateBillingDetails(vendorId, savedInvoiceEntity.getToDate());
        return savedInvoiceEntity;
    }

    @Override
    public InvoiceEntity deleteInvoice(UUID invoiceId) {
        InvoiceEntity invoiceById = getInvoiceById(invoiceId);
        invoiceRepository.delete(invoiceById);
        return invoiceById;
    }

    @Override
    public InvoiceEntity getInvoiceById(UUID invoiceId) {
        return invoiceRepository.findById(invoiceId)
                                .orElseThrow(() -> new EntityNotFoundException("invoice not found" + invoiceId));
    }

    @Override
    public ConnectionResponseDTO<InvoiceEntity> getAllInvoices(ConnectionRequestDTO connectionRequestDTO) {
        return connectionResponseBuilder.build(invoiceRepository, specificationBuilder, connectionRequestDTO);
    }
}
