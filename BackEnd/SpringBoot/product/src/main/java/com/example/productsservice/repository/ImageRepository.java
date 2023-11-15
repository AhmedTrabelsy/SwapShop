package com.example.productsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.productsservice.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

    void deleteByProduct_Id(Long productId);

}
