package com.example.portafolio_backend.domain.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailRequest {
    private Long subId;
    private Long quantity;
    private BigDecimal total;
}
