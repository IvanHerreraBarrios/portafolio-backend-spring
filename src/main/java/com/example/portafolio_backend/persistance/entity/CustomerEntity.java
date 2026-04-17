package com.example.portafolio_backend.persistance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String role;
    private String address;

    @Column(name = "number_cellphone")
    private String numberCellphone;


    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<OrderEntity> orders;
}
