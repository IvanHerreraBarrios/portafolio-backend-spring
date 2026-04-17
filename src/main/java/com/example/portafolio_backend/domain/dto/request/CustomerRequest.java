package com.example.portafolio_backend.domain.dto.request;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {
    private String name;
    private String email;
    private String password;
    private String address;
    private String numberCellphone;
}
