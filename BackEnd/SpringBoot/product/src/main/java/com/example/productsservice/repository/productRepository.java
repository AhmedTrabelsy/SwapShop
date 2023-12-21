package com.example.productsservice.repository;

import com.example.productsservice.entity.product;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface productRepository extends JpaRepository<product, Long> {

    @Query("SELECT p FROM product p WHERE p.deleted_at IS NULL ORDER BY p.updated_at DESC LIMIT 3")
    List<product> findLastThree();
    @Query("SELECT p FROM product p WHERE p.deleted_at IS NULL")
    public Page<product> findAll(Pageable pageable);
    
    @Query("SELECT p FROM product p WHERE p.deleted_at IS NOT NULL")
    public Page<product> findDeleted(Pageable pageable);
    
    // Soft delete.
    @Transactional
    @Query("update product set deleted_at = CURRENT_TIMESTAMP where id=?1")
    @Modifying
    public void softDelete(Long id);

    @Query(value = "SELECT COALESCE(COUNT(p.id), 0) as count FROM (SELECT 1 as month_num UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12) m LEFT JOIN product p ON MONTH(p.updated_at) = m.month_num GROUP BY m.month_num WHERE p.deleted_at IS NULL ORDER BY m.month_num", nativeQuery = true)
    List<Long> getProductsUpdatedCountForEveryMonth();

    @Query("SELECT COUNT(*) FROM product p WHERE p.deleted_at IS NULL")
    int getProductCount();
}
