package com.example.productsservice.requests;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateProductRequest {
    private String name;

    private String description;

    private List<MultipartFile> images;

    @Min(1)
    private Long price;

    @Min(1)
    private Long categoryID;
}
