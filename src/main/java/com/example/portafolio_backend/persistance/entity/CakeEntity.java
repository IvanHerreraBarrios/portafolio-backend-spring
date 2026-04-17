package com.example.portafolio_backend.persistance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cakes")
public class CakeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String flavor;

    private String image;

    private BigDecimal price;

    private Long stock;

    @Column(name = "id_brand")
    private Long idBrand;

    @ManyToOne
    @JoinColumn(name = "id_brand", insertable = false, updatable = false)
    private BrandEntity brand;

    @OneToMany(mappedBy = "cake", fetch = FetchType.EAGER)
    private List<OrderDetailEntity> orderDetails;


}
