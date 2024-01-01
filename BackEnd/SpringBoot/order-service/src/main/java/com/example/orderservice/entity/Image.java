package com.example.orderservice.entity;
import lombok.Data;

@Data
public class Image {
    private Long id;
    private String name;
    private product product;
}
