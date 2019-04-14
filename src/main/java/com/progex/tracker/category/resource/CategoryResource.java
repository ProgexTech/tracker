package com.progex.tracker.category.resource;

import com.progex.tracker.category.dto.Category;
import com.progex.tracker.category.entity.CategoryEntity;
import com.progex.tracker.category.service.CategoryService;
import com.progex.tracker.exceptions.Exceptions;
import com.progex.tracker.item.resource.ItemResource;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author indunil
 */
@RestController
@RequestMapping("/api")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemResource.class);
    private static final String BASE_URL_STR = "/api/categories/";

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        CategoryEntity categoryEntity = modelMapper.map(category, CategoryEntity.class);
        if (Objects.nonNull(categoryEntity)) {
            CategoryEntity savedCategoryEntity = categoryService.insert(categoryEntity);
            return ResponseEntity.created(URI.create(BASE_URL_STR +
                    savedCategoryEntity.getId())).body(modelMapper.
                    map(savedCategoryEntity, Category.class));
        }
        LOGGER.warn("Failed to insert given categoryEntity, name = {} ", category.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int categoryId) {
        return categoryService.getCategoryById(categoryId).map(savedCategory -> {
                    Category category = modelMapper.map(savedCategory, Category.class);
                    return ResponseEntity.ok().body(category);
                }
        ).orElseThrow(() -> {
            LOGGER.warn("No CategoryEntity found for the given id = {}", categoryId);
            return Exceptions.getCategoryNotFoundException(categoryId);
        });
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories(@RequestParam(value = "offset", required = true) int offset,
                                                           @RequestParam(value = "limit", required = true) int limit) {
        List<CategoryEntity> allCategories = categoryService.getAllCategories(offset, limit);
        if (!allCategories.isEmpty()) {
            List<Category> categories = allCategories.stream().filter(Objects::nonNull)
                    .map(category ->
                            modelMapper.map(category, Category.class)
                    ).collect(Collectors.toList());

            return ResponseEntity.ok(categories);
        }
        LOGGER.warn("No Categories found for the given offset =[{}] , limit=[{}]", offset, limit);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Object> deleteCategoryById(@PathVariable int categoryId) {
        return categoryService.getCategoryById(categoryId)
                .map(category -> {
                    categoryService.deleteById(categoryId);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> {
                            LOGGER.warn("No CategoryEntity found for the given id={}", categoryId);
                            throw Exceptions.getCategoryNotFoundException(categoryId);
                        }
                );
    }

    @PutMapping("/categories/{categoryId}")
    ResponseEntity<Category> updateCategory(@RequestBody Category categoryDTO,
                                            @PathVariable int categoryId) {
        if(categoryService.getCategoryById(categoryId).isEmpty()) {
            LOGGER.warn("No Category found for the given id={}", categoryId);
            return ResponseEntity.notFound().build();
        }
        CategoryEntity newCategoryEntity = modelMapper.map(categoryDTO, CategoryEntity.class);
        Category category = modelMapper.map(categoryService.update(categoryId, newCategoryEntity), Category.class);
        return ResponseEntity.ok(category);
    }
}
