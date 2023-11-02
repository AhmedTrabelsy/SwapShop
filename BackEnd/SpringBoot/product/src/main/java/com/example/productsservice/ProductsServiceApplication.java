package com.example.productsservice;

import com.example.productsservice.entity.product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ProductsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(com.example.productsservice.repository.productRepository productRepository,
                            RepositoryRestConfiguration restConfiguration){
        List<String> list = new ArrayList<>();
        list.add("image1.jpg");
        list.add("Image2.jpg");
        list.add("image3.jpg");
       product pc = new com.example.productsservice.entity.product("PC Gamer", "16GB RAM / GTX 1660 / I5 10006F", list, 3000.0, new Date(), new Date());
        product pants = new com.example.productsservice.entity.product("Jean Blue", "Jean Blue Size 38", list, 70.0, new Date(), new Date());
        return args->{
            restConfiguration.exposeIdsFor(com.example.productsservice.entity.product.class);
            productRepository.save(pc);
            productRepository.save(pants);
        };
    }
}
