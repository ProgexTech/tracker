package com.progex.tracker.category.repo;

import com.progex.tracker.category.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    @Query(value = "SELECT * FROM categoryEntity LIMIT ?2 OFFSET ?1", nativeQuery = true)
    List<CategoryEntity> findAllCategories(int offset, int limit);
}
