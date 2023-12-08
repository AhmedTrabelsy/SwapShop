package com.example.orderservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class order {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Long trackId;
    private Long productId;
    private Date createdAt;
    private Date updatedAt;
}
