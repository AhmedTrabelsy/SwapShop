package com.example.orderservice.controller;

import com.example.orderservice.entity.order;
import com.example.orderservice.repository.orderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class orderController {
    @Autowired
    orderRepository orderRepository;

    @GetMapping("/orders")
    public List<order> getOrder() {
        return orderRepository.findAll();
    }

    

}
