package com.example.portafolio_backend.domain.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CakeResponse {

    private Long id;

    private String brandDescription;

    private String flavor;

    private String image;

    private BigDecimal price;

    private String name;

    private Long stock;

    private Long idBrand;
}
