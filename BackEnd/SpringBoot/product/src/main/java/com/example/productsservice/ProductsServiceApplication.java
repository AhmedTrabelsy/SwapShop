package com.example.productsservice;

import com.example.productsservice.entity.Image;
import com.example.productsservice.entity.product;
import com.example.productsservice.repository.ImageRepository;
import com.example.productsservice.repository.productRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class ProductsServiceApplication {

    @Autowired
    private productRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProductsServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(RepositoryRestConfiguration restConfiguration) {
        return args -> {

            if (productRepository.count() == 0) {

                product p = new product();
                p.setName("PC HP");
                p.setDescription("PC HP 2021");
                p.setPrice(1700);
                p.setCreated_at(new Date());
                p.setUpdated_at(new Date());
                p.setCategoryID(1L);

                p = productRepository.save(p);

                Image m = new Image();
                m.setName("pc1.jpg");
                m.setProduct(p);

                imageRepository.save(m);

                Image m2 = new Image();
                m2.setName("pc2.jpg");
                m2.setProduct(p);

                imageRepository.save(m2);

            }

        };
    }
}
