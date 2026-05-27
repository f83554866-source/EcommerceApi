package com.ws101.fformaran.services;

import com.ws101.fformaran.model.Category;
import com.ws101.fformaran.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private List<Product> productList = new ArrayList<>();

    public ProductService() {

        Category electronics = new Category();
        electronics.setId(1L);
        electronics.setName("Electronics");

        productList.add(new Product(
                1L,
                "Mouse",
                "Gaming Mouse",
                500.0,
                "mouse.jpg",
                electronics
        ));

        productList.add(new Product(
                2L,
                "Keyboard",
                "Mechanical Keyboard",
                1500.0,
                "keyboard.jpg",
                electronics
        ));
    }

    public List<Product> getAllProducts() {
        return productList;
    }

    public Product getProductById(Long id) {
        return productList.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(Product product) {
        productList.add(product);
        return product;
    }

    public Product updateProduct(Long id, Product updatedProduct) {

        Product existing = getProductById(id);

        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        existing.setImageUrl(updatedProduct.getImageUrl());
        existing.setCategory(updatedProduct.getCategory());

        return existing;
    }

    public Product patchProduct(Long id, Map<String, Object> updates) {

        Product product = getProductById(id);

        updates.forEach((key, value) -> {
            switch (key) {

                case "name" -> product.setName((String) value);

                case "description" -> product.setDescription((String) value);

                case "price" -> product.setPrice(Double.valueOf(value.toString()));

                case "imageUrl" -> product.setImageUrl((String) value);

                case "category" -> {
                    Category category = new Category();
                    category.setName((String) value);
                    product.setCategory(category);
                }
            }
        });

        return product;
    }

    public void deleteProduct(Long id) {

        Product product = getProductById(id);

        productList.remove(product);
    }

    public List<Product> filterProducts(String type, String value) {

        return switch (type.toLowerCase()) {

            case "category" -> productList.stream()
                    .filter(p -> p.getCategory().getName().equalsIgnoreCase(value))
                    .toList();

            case "name" -> productList.stream()
                    .filter(p -> p.getName().equalsIgnoreCase(value))
                    .toList();

            default -> new ArrayList<>();
        };
    }
}