package com.ws101.fformaran.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // 1. ADD THIS IMPORT
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * Represents a Product entity in the database.
 */
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double price;

    private String imageUrl;

    /**
     * Many products belong to one category.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // 2. ADD THIS ANNOTATION HERE
    private Category category;
}