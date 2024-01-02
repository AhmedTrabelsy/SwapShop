package com.example.productsservice.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.productsservice.entity.Image;
import com.example.productsservice.entity.product;
import com.example.productsservice.repository.CategoryServiceClient;
import com.example.productsservice.repository.ImageRepository;
import com.example.productsservice.repository.productRepository;
import com.example.productsservice.requests.CreateProductRequest;
import com.example.productsservice.requests.UpdateProductRequest;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.UnexpectedTypeException;
import jakarta.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    productRepository productRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    CategoryServiceClient categoryServiceClient;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    @GetMapping("/products")
    public List<product> index(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<product> products = productRepository.findAll(pageable);
        products.forEach(product -> {
            product.setCategory(categoryServiceClient.findCategoryById(product.getCategoryID()));
        });

        return products.getContent();
    }

    @GetMapping("/deletedProducts")
    public List<product> findDeletedProducts(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<product> products = productRepository.findDeleted(pageable);
        products.forEach(product -> {
            product.setCategory(categoryServiceClient.findCategoryById(product.getCategoryID()));
        });

        return products.getContent();
    }

    @GetMapping("/products/{id}")
    public product findById(@PathVariable Long id) {
        product p = productRepository.findById(id).get();
        p.setCategory(categoryServiceClient.findCategoryById(p.getCategoryID()));
        return p;
    }

    @PostMapping("/products")
    public product create(@Valid CreateProductRequest request) {
        product p = new product();
        p.setName(request.getName());
        p.setSellerID(request.getSellerID());
        p.setDescription(request.getDescription());

        List<Image> images = new ArrayList<Image>();
        request.getImages().forEach(image -> {
            try {
                Image imageEntity = new Image();
                imageEntity.setName(uploadImage(image));
                images.add(imageEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        p.setPrice(request.getPrice());
        p.setCategoryID(request.getCategoryID());
        p.setCreated_at(new Date());
        p.setUpdated_at(new Date());
        product savedProduct = productRepository.save(p);

        images.forEach(image -> {
            image.setProduct(savedProduct);
            imageRepository.save(image);
        });

        return productRepository.findById(savedProduct.getId()).get();
    }

    @Transactional
    @PutMapping("/products/{id}")
    public product update(@PathVariable Long id, @Valid UpdateProductRequest request) {
        product p = productRepository.findById(id).orElseThrow();

        if (request.getName() != null) {
            p.setName(request.getName());
        }

        if (request.getDescription() != null) {
            p.setDescription(request.getDescription());
        }

        if (request.getPrice() != null) {
            p.setPrice(request.getPrice());
        }

        if (request.getCategoryID() != null) {
            p.setCategoryID(request.getCategoryID());
        }

        p.setUpdated_at(new Date());

        if (request.getImages() != null && request.getImages().size() != 0) {

            imageRepository.deleteByProduct_Id(p.getId());

            List<Image> images = new ArrayList<Image>();
            request.getImages().forEach(image -> {
                try {
                    Image imageEntity = new Image();
                    imageEntity.setProduct(p);
                    imageEntity.setName(uploadImage(image));
                    images.add(imageEntity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            images.forEach(image -> {
                image.setProduct(p);
                imageRepository.save(image);
            });
        }

        product savedProduct = productRepository.save(p);

        return productRepository.findById(savedProduct.getId()).get();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            productRepository.softDelete(id);
            return ResponseEntity.ok("Product deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete product.");
        }
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

    private String uploadImage(MultipartFile image) throws IOException {
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

    @GetMapping("/lastUpdatedProduct")
    public List<product> getLastUpdatedProduct(){
        List<product> products = productRepository.findLastThree();
        products.forEach(product -> {
            product.setCategory(categoryServiceClient.findCategoryById(product.getCategoryID()));
        });
        return products;
    }
    @GetMapping("/countProductInMounths")
    public List<Long> getCountProductInMounth(){
        return productRepository.getProductsUpdatedCountForEveryMonth();
    }
    @GetMapping("/productCount")
    public int getProductCount(){
        return productRepository.getProductCount();
    }
}
