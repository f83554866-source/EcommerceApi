package com.ws101.fformaran.controller;

import com.ws101.fformaran.dto.CreateProductDto;
import com.ws101.fformaran.model.Product;
import com.ws101.fformaran.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@RequestMapping("/api/products") // Note: The lab instructions use /api/v1/products, you may want to update this to match!
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // PUBLIC: Anyone can view products
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = service.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // PUBLIC: Anyone can view a specific product
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        Product product = service.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // ADMIN ONLY: Task 4 Bean Validation applied here
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> create(@Valid @RequestBody CreateProductDto dto) {
        // We removed the manual 'if' checks! Spring handles it automatically now.
        
        // Map the DTO to your entity
        Product product = new Product();
        product.setName(dto.name());
        product.setPrice(dto.price());
        // product.setDescription(dto.description()); // If you have this field
        
        Product createdProduct = service.createProduct(product);
        return ResponseEntity.status(201).body(createdProduct);
    }

    // ADMIN ONLY: Updating requires privileges
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody CreateProductDto dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setPrice(dto.price());

        Product updatedProduct = service.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    // ADMIN ONLY
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> patch(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Product patchedProduct = service.patchProduct(id, updates);
        return ResponseEntity.ok(patchedProduct);
    }

    // ADMIN ONLY: Task 3 Explicit Requirement
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // PUBLIC: Filtering is usually public
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