package com.atman.server.OrderModule.Resolver;

import com.atman.server.OrderModule.Entity.InvoiceEntity;
import com.atman.server.OrderModule.Service.Impl.InvoiceService;
import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;
import com.atman.server.Specification.DTO.MapDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class InvoiceResolver {

    private final InvoiceService invoiceService;

    @QueryMapping
    public ConnectionResponseDTO<InvoiceEntity> invoices(@Argument Integer first, @Argument String after, @Argument Integer last, @Argument String before, @Argument MapDTO search, @Argument List<MapDTO> filter, @Argument List<MapDTO> sort) {
        ConnectionRequestDTO request = ConnectionRequestDTO.builder()
                                                           .after(after)
                                                           .before(before)
                                                           .first(first)
                                                           .last(last)
                                                           .search(search)
                                                           .filter(filter)
                                                           .sort(sort)
                                                           .build();
        return invoiceService.getAllInvoices(request);
    }

    @QueryMapping
    public InvoiceEntity invoiceByID(@Argument(name = "id") UUID id) {
        return invoiceService.getInvoiceById(id);
    }

    @MutationMapping
    public InvoiceEntity generateInvoiceForCustomer(@Argument(name = "customer") UUID customerID) {
        return invoiceService.generateInvoiceForCustomer(customerID);
    }

    @MutationMapping
    public InvoiceEntity generateInvoiceForVendor(@Argument(name = "vendor") UUID vendorID) {
        return invoiceService.generateInvoiceForVendor(vendorID);
    }

    @MutationMapping
    public InvoiceEntity deleteInvoice(@Argument(name = "id") UUID id) {
        return invoiceService.deleteInvoice(id);
    }

    @MutationMapping
    public InvoiceEntity updateInvoicePayment(@Argument(name = "id") UUID id, @Argument(name = "amountPaid") BigDecimal amountPaid) {
        return invoiceService.updateInvoicePayment(id, amountPaid);
    }
}
