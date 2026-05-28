package com.ws101.fformaran.services;

import com.ws101.fformaran.model.Category;
import com.ws101.fformaran.model.Product;
import com.ws101.fformaran.repository.CategoryRepository;
import com.ws101.fformaran.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        initializeSampleData();
    }

    /**
     * Initialize sample data if the database is empty.
     */
    private void initializeSampleData() {
        if (productRepository.count() == 0) {
            Category electronics = new Category();
            electronics.setName("Electronics");
            electronics = categoryRepository.save(electronics);

            productRepository.save(new Product(
                    null,
                    "Mouse",
                    "Gaming Mouse",
                    500.0,
                    "mouse.jpg",
                    electronics
            ));

            productRepository.save(new Product(
                    null,
                    "Keyboard",
                    "Mechanical Keyboard",
                    1500.0,
                    "keyboard.jpg",
                    electronics
            ));
        }
    }

    /**
     * Retrieve all products from the database.
     * 
     * @return list of all products
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieve a product by ID from the database.
     * 
     * @param id the product ID
     * @return the product if found
     * @throws RuntimeException if product not found
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    /**
     * Create a new product in the database.
     * 
     * @param product the product to create
     * @return the saved product
     */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Update an existing product in the database.
     * 
     * @param id the product ID
     * @param updatedProduct the updated product data
     * @return the updated product
     */
    public Product updateProduct(Long id, Product updatedProduct) {

        Product existing = getProductById(id);

        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        existing.setImageUrl(updatedProduct.getImageUrl());
        existing.setCategory(updatedProduct.getCategory());

        return productRepository.save(existing);
    }

    /**
     * Patch update a product in the database.
     * 
     * @param id the product ID
     * @param updates the map of fields to update
     * @return the updated product
     */
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

        return productRepository.save(product);
    }

    /**
     * Delete a product from the database.
     * 
     * @param id the product ID
     */
    public void deleteProduct(Long id) {

        Product product = getProductById(id);

        productRepository.delete(product);
    }

    /**
     * Filter products by type and value using repository queries.
     * 
     * @param type the filter type (category, name, priceRange)
     * @param value the filter value
     * @return list of filtered products
     */
    public List<Product> filterProducts(String type, String value) {

        return switch (type.toLowerCase()) {

            case "category" -> productRepository.findByCategoryName(value);

            case "name" -> productRepository.findByName(value);

            default -> new ArrayList<>();
        };
    }
}