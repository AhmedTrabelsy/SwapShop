package com.example.productsservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String Description;
    @ElementCollection
    private List<String> image;
    private double price;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long CategoryID;
    private Date created_at;
    private Date updated_at;

    public product(String name, String description, List<String> image, double price, Date created_at, Date updated_at) {
        this.name = name;
        this.Description = description;
        this.image = image;
        this.price = price;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }


}
