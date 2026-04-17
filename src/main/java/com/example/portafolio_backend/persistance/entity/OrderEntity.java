package com.example.portafolio_backend.persistance.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    @CreationTimestamp
    private LocalDateTime creationDate;

    private BigDecimal total;
    private String status;

    @Column(name = "customer_id")
    private Long customerId;

    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private CustomerEntity customer;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<OrderDetailEntity> orderDetails;

}
