package com.example.portafolio_backend.domain.dto.response;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerUpdateResponse {
    private String jwt;
    private String message;
}
