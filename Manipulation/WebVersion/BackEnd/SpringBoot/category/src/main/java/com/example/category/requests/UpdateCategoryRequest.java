package com.example.category.requests;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCategoryRequest {
    @NotNull
    @NotBlank
    private String name;

    private MultipartFile icon;

    private Long parentCategoryId;
}
