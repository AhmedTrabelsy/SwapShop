package com.example.productsservice.repository;

import com.example.productsservice.entity.product;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface productRepository extends JpaRepository<product, Long> {

    
    @Query("SELECT p FROM product p WHERE p.deleted_at IS NULL")
    public Page<product> findAll(Pageable pageable);
    
    @Query("SELECT p FROM product p WHERE p.deleted_at IS NOT NULL")
    public Page<product> findDeleted(Pageable pageable);
    
    // Soft delete.
    @Transactional
    @Query("update product set deleted_at = CURRENT_TIMESTAMP where id=?1")
    @Modifying
    public void softDelete(Long id);

}
