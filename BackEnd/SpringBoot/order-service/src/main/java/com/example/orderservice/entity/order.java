package com.example.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name = "`order`")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long trackId;
    private Long productId;
    private Date createdAt;
    private Date updatedAt;
}
