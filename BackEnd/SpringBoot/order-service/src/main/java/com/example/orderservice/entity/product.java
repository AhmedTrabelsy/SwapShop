package com.example.orderservice.entity;

import lombok.Data;

import java.util.List;
import java.util.Date;
@Data
public class product {
    private Long id;
    private Category category;
    private String name;
    private String description;
    private List<Image> images;
    private double price;
    private Long categoryID;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
}
