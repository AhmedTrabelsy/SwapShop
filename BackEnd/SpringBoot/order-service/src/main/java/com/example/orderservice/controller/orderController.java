package com.example.orderservice.controller;

import Requests.setOrderRequest;
import com.example.orderservice.entity.order;
import com.example.orderservice.repository.orderRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class orderController {
    @Autowired
    orderRepository orderRepository;

    @GetMapping("/orders")
    public List<order> getOrder() {
        return orderRepository.findAll();
    }

    @PostMapping("/neworder")
    public void createOrder(@Valid setOrderRequest request) {
        order o=new order();
        o.setUserId(request.getUserId());
        o.setProductId(request.getProudctId());
        o.setBillingAdress(request.getBillingAdress());
        o.setCreatedAt(new Date());
        o.setUpdatedAt(new Date());
        orderRepository.save(o);
    }


    @PostMapping("/setorder/{id}")
    public void updateOrder(@PathVariable Long id, @Valid setOrderRequest request) {
        order o = orderRepository.findById(id).orElseThrow();
        if (request.getUserId() != null) {
            o.setUserId(request.getUserId());
        }
        if (request.getProudctId() != null) {
           o.setProductId(request.getProudctId());
        }
        if (request.getTrackId() != null) {
            o.setBillingAdress(request.getBillingAdress());
        }
        o.setUpdatedAt(new Date());
        orderRepository.save(o);
    }

}
