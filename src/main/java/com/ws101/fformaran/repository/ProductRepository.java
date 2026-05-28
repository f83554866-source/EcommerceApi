package com.ws101.fformaran.repository;

import com.ws101.fformaran.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Find products by category name using method naming convention.
     * 
     * @param categoryName the name of the category
     * @return list of products matching the category
     */
    List<Product> findByCategoryName(String categoryName);

    /**
     * Find products by name using method naming convention.
     * 
     * @param name the name of the product
     * @return list of products matching the name
     */
    List<Product> findByName(String name);

    /**
     * Custom JPQL query to find products within a price range.
     * 
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return list of products within the price range
     */
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findProductsByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    /**
     * Custom JPQL query to find products by category ID.
     * 
     * @param categoryId the ID of the category
     * @return list of products in the category
     */
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId);
}
