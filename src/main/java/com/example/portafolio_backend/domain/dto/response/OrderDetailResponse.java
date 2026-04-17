package com.example.portafolio_backend.domain.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private Long subId;
    private Long quantity;
    private BigDecimal total;

    private String nameCake;
}
