package com.example.productsservice.repository;

import com.example.productsservice.entity.product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface productRepository extends JpaRepository<product, Long> {

    public Page<product> findAll(Pageable pageable);

}
