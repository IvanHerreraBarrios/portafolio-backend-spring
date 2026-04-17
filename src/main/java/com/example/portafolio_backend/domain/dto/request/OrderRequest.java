package com.example.portafolio_backend.domain.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private BigDecimal total;
    private Long customerId;
    private List<OrderDetailRequest> orderDetails;
}
