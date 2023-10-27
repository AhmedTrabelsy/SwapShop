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
			if (categoryRepository.count() == 0) {
				Category infoEtMultimedia = categoryRepository.save(new Category("Informatique et multimedias", null));

				categoryRepository.save(new Category("Ordinateurs Portables", "laptop.png", infoEtMultimedia));

				categoryRepository.save(new Category("Ordinateurs de bureau", "pc bureau.png", infoEtMultimedia));

				categoryRepository.save(new Category("Console de jeux", "ps-logo-of-games.png", infoEtMultimedia));

				categoryRepository.save(new Category("Perepheriques et accessoires", null, infoEtMultimedia));

				Category telephoneEtTablette = categoryRepository.save(new Category("Telephone & Tablette", null));

				categoryRepository.save(new Category("Telephone", "smartphone-call.png", telephoneEtTablette));

				categoryRepository.save(new Category("Tablette", null, telephoneEtTablette));

				categoryRepository.save(new Category("Aceessoires Telephonie", null, telephoneEtTablette));

				Category mode = categoryRepository.save(new Category("Mode", null));

				categoryRepository.save(new Category("Vetement Homme", null, mode));

				categoryRepository.save(new Category("Vetement Femme", null, mode));

				categoryRepository.save(new Category("Vetement Enfants", null, mode));

				categoryRepository.save(new Category("Accessoires Homme", null, mode));

				categoryRepository.save(new Category("Accessoires Femme", null, mode));

			}

		};
	}

}
