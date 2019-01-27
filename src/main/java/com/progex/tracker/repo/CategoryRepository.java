package com.progex.tracker.repo;

import com.progex.tracker.entity.Category;
import com.progex.tracker.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
