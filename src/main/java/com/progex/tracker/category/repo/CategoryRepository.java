package com.progex.tracker.category.repo;

import com.progex.tracker.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "SELECT * FROM category LIMIT ?2 OFFSET ?1", nativeQuery = true)
    List<Category> findAllCategories(int offset, int limit);
}
