package com.example.orderservice.repository;

import com.example.orderservice.entity.product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="PRODUCT-SERVICE")
public interface ProductServiceClient {
    @GetMapping("/products/{id}")
    product findById(@PathVariable Long id);
}
