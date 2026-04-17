package com.example.portafolio_backend.persistance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_details")
public class OrderDetailEntity {
    @EmbeddedId
    private OrderDetailPK id;

    private Long quantity;
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "id_order", insertable = false, updatable = false)
    @ToString.Exclude
    @MapsId("idOrder")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "id_cake", insertable = false, updatable = false)
    private CakeEntity cake;




}
