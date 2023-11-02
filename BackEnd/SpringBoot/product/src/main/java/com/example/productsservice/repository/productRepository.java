package com.example.productsservice.repository;

import com.example.productsservice.entity.product;
import com.example.productsservice.projection.productProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface productRepository extends JpaRepository<product,Long> {

}
