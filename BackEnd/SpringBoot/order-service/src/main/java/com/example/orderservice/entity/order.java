package com.example.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "orders")
@Data
public class order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String billingAdress;
    @Transient
    private product product;
    private Long productId;
    private Date createdAt;
    private Date updatedAt;
}
