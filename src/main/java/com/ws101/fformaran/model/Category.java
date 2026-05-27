package com.ws101.fformaran.model;

import com.fasterxml.jackson.annotation.JsonIgnore; // 1. ADD THIS IMPORT
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * Category entity.
 * One category can have many products.
 */
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    /**
     * One category has many products.
     */
    @OneToMany(
            mappedBy = "category",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore // 2. ADD THIS ANNOTATION HERE
    private List<Product> products = new ArrayList<>();
}