package com.progex.tracker.category.resource;

import com.progex.tracker.category.dto.CategoryDto;
import com.progex.tracker.category.entity.Category;
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
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        if (Objects.nonNull(category)) {
            Category savedCategory = categoryService.insert(category);
            return ResponseEntity.created(URI.create(BASE_URL_STR +
                    savedCategory.getId())).body(modelMapper.
                    map(savedCategory, CategoryDto.class));
        }
        LOGGER.warn("Failed to insert given category, name = {} ", categoryDto.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable int categoryId) {
        return categoryService.getCategoryById(categoryId).map(savedCategory -> {
                    CategoryDto categoryDto = modelMapper.map(savedCategory, CategoryDto.class);
                    return ResponseEntity.ok().body(categoryDto);
                }
        ).orElseThrow(() -> {
            LOGGER.warn("No Category found for the given id = {}", categoryId);
            return Exceptions.getCategoryNotFoundException(categoryId);
        });
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories(@RequestParam(value = "offset", required = true) int offset,
                                                              @RequestParam(value = "limit", required = true) int limit) {
        List<Category> allCategories = categoryService.getAllCategories(offset, limit);
        if (!allCategories.isEmpty()) {
            List<CategoryDto> categories = allCategories.stream().filter(Objects::nonNull)
                    .map(category ->
                            modelMapper.map(category, CategoryDto.class)
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
                            LOGGER.warn("No Category found for the given id={}", categoryId);
                            throw Exceptions.getCategoryNotFoundException(categoryId);
                        }
                );
    }

    @PutMapping("/categories/{categoryId}")
    ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDTO,
                                               @PathVariable int categoryId) {
        if(categoryService.getCategoryById(categoryId).isEmpty()) {
            LOGGER.warn("No CategoryDto found for the given id={}", categoryId);
            return ResponseEntity.notFound().build();
        }
        Category newCategory = modelMapper.map(categoryDTO, Category.class);
        CategoryDto category = modelMapper.map(categoryService.update(categoryId, newCategory), CategoryDto.class);
        return ResponseEntity.ok(category);
    }
}
