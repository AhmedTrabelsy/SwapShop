package com.example.category;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.category.Repositories.CategoryRepository;
import com.example.category.entites.Category;

@SpringBootApplication
public class CategoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoryApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CategoryRepository categoryRepository) {
		return args -> {
			categoryRepository.save(new Category(null, "Vehicules"));

			categoryRepository.save(new Category(null, "Immobilier"));

			categoryRepository.save(new Category(null, "Informatique et multimedias"));

			categoryRepository.save(new Category(null, "Pour la maison et jardin"));

			categoryRepository.save(new Category(null, "Habillement et Bien etre"));

			categoryRepository.save(new Category(null, "Loisirs et Divertissement"));

		};
	}

}
