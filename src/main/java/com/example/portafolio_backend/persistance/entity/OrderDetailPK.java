package com.example.portafolio_backend.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class OrderDetailPK {
    @Column(name = "id_order")
    private Long idOrder;

    @Column(name = "id_cake")
    private Long idCake;
}
