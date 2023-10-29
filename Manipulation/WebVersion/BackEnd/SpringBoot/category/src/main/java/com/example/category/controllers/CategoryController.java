package com.example.category.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.category.Repositories.CategoryRepository;
import com.example.category.entites.Category;
import com.example.category.requests.CreateCategoryRequest;
import com.example.category.requests.UpdateCategoryRequest;

import io.micrometer.core.ipc.http.HttpSender.Response;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.UnexpectedTypeException;
import jakarta.validation.Valid;

@RestController
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    @GetMapping("/categories")
    public List<Category> index() {
        return categoryRepository.findAll();
    }

    @PostMapping("/categories")
    public Category create(@Valid CreateCategoryRequest request) throws IOException {

        Category category = new Category();
        category.setName(request.getName());
        category.setIcon(uploadCategoryImage(request.getIcon()));

        if (request.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(request.getParentCategoryId()).orElseThrow();
            category.setParentCategory(parentCategory);
        }

        return categoryRepository.save(category);
    }

    @GetMapping("/uploads/{imageName}")
    public void getImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        Path imagePath = Paths.get(UPLOAD_DIRECTORY, imageName);

        if (Files.exists(imagePath)) {
            Files.copy(imagePath, response.getOutputStream());
            response.getOutputStream().flush();
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @PutMapping("/categories/{id}")
    public Category update(@PathVariable Long id, @Valid UpdateCategoryRequest request) throws IOException {

        System.out.println(request.getIcon());

        Category category = categoryRepository.findById(id).orElseThrow();

        if (request.getName() != null) {
            category.setName(request.getName());
        }

        if (request.getIcon() != null) {
            category.setIcon(uploadCategoryImage(request.getIcon()));
        }

        if (request.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(request.getParentCategoryId()).orElseThrow();
            category.setParentCategory(parentCategory);
        } else {
            category.setParentCategory(null);
        }

        return categoryRepository.save(category);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        categoryRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    private String uploadCategoryImage(MultipartFile image) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        String randomFilename = generateRandomFilename(image.getOriginalFilename());

        System.out.println("file size is ");
        System.out.println(image.getSize());

        if (image.getSize() > 500 * 1024) {
            throw new FileSizeLimitExceededException("File size exceeds the allowed limit", 0, 0);
        }

        if (!isValidFileExtension(getFileExtension(image.getOriginalFilename()))) {
            throw new UnexpectedTypeException("Unsupported file type");
        }

        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, randomFilename);

        fileNames.append(randomFilename);

        Files.write(fileNameAndPath, image.getBytes());

        return randomFilename;
    }

    private String generateRandomFilename(String originalFilename) {
        String randomString = UUID.randomUUID().toString().replace("-", "");

        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        return randomString + fileExtension;
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    private boolean isValidFileExtension(String fileExtension) {
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");
        return allowedExtensions.contains(fileExtension.toLowerCase());
    }
}
