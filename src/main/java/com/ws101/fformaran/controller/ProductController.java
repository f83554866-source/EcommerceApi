package com.ws101.fformaran.controller;

import com.ws101.fformaran.model.Product;
import com.ws101.fformaran.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Product Management.
 * Handles all product-related HTTP requests and returns appropriate responses.
 * All data is persisted to the database via the ProductService and ProductRepository.
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    /**
     * Get all products from the database.
     * 
     * @return ResponseEntity with list of all products (200 OK)
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = service.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Get a product by ID from the database.
     * 
     * @param id the product ID
     * @return ResponseEntity with the product (200 OK)
     * @throws RuntimeException if product not found (404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        Product product = service.getProductById(id);
        return ResponseEntity.ok(product);
    }

    /**
     * Create a new product and save to database.
     * 
     * @param product the product data
     * @return ResponseEntity with created product (201 Created)
     * @throws IllegalArgumentException if product data is invalid (400)
     */
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (product.getPrice() == null || product.getPrice() < 0) {
            throw new IllegalArgumentException("Product price must be valid and non-negative");
        }
        
        Product createdProduct = service.createProduct(product);
        return ResponseEntity.status(201).body(createdProduct);
    }

    /**
     * Update an existing product in the database.
     * 
     * @param id the product ID
     * @param product the updated product data
     * @return ResponseEntity with updated product (200 OK)
     * @throws RuntimeException if product not found (404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = service.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Partially update a product in the database (PATCH).
     * 
     * @param id the product ID
     * @param updates the map of fields to update
     * @return ResponseEntity with updated product (200 OK)
     * @throws RuntimeException if product not found (404)
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Product> patch(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Product patchedProduct = service.patchProduct(id, updates);
        return ResponseEntity.ok(patchedProduct);
    }

    /**
     * Delete a product from the database.
     * 
     * @param id the product ID
     * @return ResponseEntity with no content (204 No Content)
     * @throws RuntimeException if product not found (404)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Filter products by type and value using database queries.
     * Supports filtering by:
     * - "category": Filter by category name
     * - "name": Filter by product name
     * 
     * @param filterType the type of filter (category, name)
     * @param filterValue the value to filter by
     * @return ResponseEntity with list of filtered products (200 OK)
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filter(
            @RequestParam String filterType,
            @RequestParam String filterValue) {
        
        if (filterType == null || filterType.isEmpty()) {
            throw new IllegalArgumentException("filterType parameter is required");
        }
        if (filterValue == null || filterValue.isEmpty()) {
            throw new IllegalArgumentException("filterValue parameter is required");
        }
        
        List<Product> filteredProducts = service.filterProducts(filterType, filterValue);
        return ResponseEntity.ok(filteredProducts);
    }
}