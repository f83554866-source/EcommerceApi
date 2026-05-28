package com.ws101.fformaran.repository;

import com.ws101.fformaran.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Find category by name using method naming convention.
     * 
     * @param name the name of the category
     * @return optional containing the category if found
     */
    Optional<Category> findByName(String name);

    /**
     * Custom JPQL query to find categories with product count greater than a threshold.
     * 
     * @param minProductCount minimum number of products
     * @return list of categories with at least the specified number of products
     */
    @Query("SELECT c FROM Category c WHERE SIZE(c.products) >= :minProductCount")
    List<Category> findCategoriesWithMinProducts(@Param("minProductCount") int minProductCount);
}
