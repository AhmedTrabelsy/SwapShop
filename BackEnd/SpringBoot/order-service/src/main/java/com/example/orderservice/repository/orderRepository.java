package com.example.orderservice.repository;

import com.example.orderservice.entity.order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface orderRepository extends JpaRepository<order,Long> {
}
