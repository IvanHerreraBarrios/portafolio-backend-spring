package com.example.portafolio_backend.domain.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private String name;
    private String email;
    private String role;
    private String address;
    private String numberCellphone;
}
