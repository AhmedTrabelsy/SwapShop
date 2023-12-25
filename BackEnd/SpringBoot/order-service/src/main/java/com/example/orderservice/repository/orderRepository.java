package com.example.orderservice.repository;

import com.example.orderservice.entity.order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface orderRepository extends JpaRepository<order,Long> {
    @Query("SELECT COUNT(*) FROM order o")
    int getOrderCount();
}
