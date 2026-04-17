package com.example.portafolio_backend.domain.dto.response;

import com.example.portafolio_backend.domain.dto.request.OrderDetailRequest;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private BigDecimal total;
    private String status;
    private Long customerId;
    private List<OrderDetailResponse> orderDetails;
    private LocalDateTime creationDate;
}
