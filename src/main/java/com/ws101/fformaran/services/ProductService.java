package com.ws101.fformaran.EcommerceApi.service;

import com.ws101.fformaran.EcommerceApi.model.Product;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private List<Product> productList = new ArrayList<>();
    private Long idCounter = 1L;

    public ProductService() {
        // sample data (10 products)
        for (int i = 1; i <= 10; i++) {
            productList.add(new Product(
                    idCounter++,
                    "Product " + i,
                    "Description " + i,
                    100.0 * i,
                    "Category " + (i % 3),
                    i * 5,
                    ""
            ));
        }
    }

    public List<Product> getAllProducts() {
        return productList;
    }

    public Product getProductById(Long id) {
        return productList.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(Product product) {
        product.setId(idCounter++);
        productList.add(product);
        return product;
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = getProductById(id);

        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        existing.setCategory(updatedProduct.getCategory());
        existing.setStockQuantity(updatedProduct.getStockQuantity());
        existing.setImageUrl(updatedProduct.getImageUrl());

        return existing;
    }

    public Product patchProduct(Long id, Map<String, Object> updates) {
        Product product = getProductById(id);

        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> product.setName((String) value);
                case "price" -> product.setPrice(Double.parseDouble(value.toString()));
                case "category" -> product.setCategory((String) value);
            }
        });

        return product;
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productList.remove(product);
    }

    public List<Product> filterProducts(String type, String value) {
        return switch (type) {
            case "category" -> productList.stream()
                    .filter(p -> p.getCategory().equalsIgnoreCase(value))
                    .collect(Collectors.toList());

            case "name" -> productList.stream()
                    .filter(p -> p.getName().toLowerCase().contains(value.toLowerCase()))
                    .collect(Collectors.toList());

            default -> new ArrayList<>();
        };
    }
}