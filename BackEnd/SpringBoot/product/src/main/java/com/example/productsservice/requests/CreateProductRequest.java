package com.example.productsservice.requests;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateProductRequest {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private List<MultipartFile> images;

    @NotNull
    @Min(1)
    private Long price;

    @NotNull
    @Min(1)
    private Long categoryID;

    @NotNull
    @NotBlank
    private String sellerID;
}
