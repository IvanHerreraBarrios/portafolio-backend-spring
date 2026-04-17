package com.example.portafolio_backend.domain.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CutomerRepsonseList {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String address;
}
