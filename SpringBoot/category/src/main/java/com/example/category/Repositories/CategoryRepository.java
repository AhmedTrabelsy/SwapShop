package com.example.category.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.category.entites.Category;

@RepositoryRestResource
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
