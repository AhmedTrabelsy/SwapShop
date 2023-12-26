package com.example.orderservice.repository;

import com.example.orderservice.entity.order;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface orderRepository extends JpaRepository<order,Long> {
    @Query("SELECT COUNT(*) FROM order o")
    int getOrderCount();

    @Query("SELECT o FROM order o WHERE YEAR(o.createdAt) = :year")
    List<order> findByYear(@Param("year") int year);
}
