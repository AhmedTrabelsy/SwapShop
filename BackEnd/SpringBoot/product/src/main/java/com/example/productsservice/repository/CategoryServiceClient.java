package com.example.productsservice.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.productsservice.entity.Category;

@FeignClient(name = "CATEGORY-SERVICE")

public interface CategoryServiceClient {
    @GetMapping("/categories/{id}")

    Category findCategoryById(@PathVariable Long id);
}
