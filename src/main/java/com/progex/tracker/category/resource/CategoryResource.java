package com.progex.tracker.category.resource;

import com.progex.tracker.category.dto.CategoryDTO;
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
import java.util.Optional;
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
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        if (Objects.nonNull(category)) {
            Optional<Category> optionalCategory = categoryService.createCategory(category);
            if (optionalCategory.isPresent()) {
                return ResponseEntity.created(URI.create(BASE_URL_STR +
                        optionalCategory.get().getId())).body(modelMapper.
                        map(optionalCategory.get(), CategoryDTO.class));
            }
        }
        LOGGER.warn("Failed to insert given category, name = {} ", categoryDTO.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable int categoryId) {
        return categoryService.getCategoryById(categoryId).map(savedCategory -> {
                    CategoryDTO categoryDto = modelMapper.map(savedCategory, CategoryDTO.class);
                    return ResponseEntity.ok().body(categoryDto);
                }
        ).orElseThrow(() -> {
            LOGGER.warn("No Category found for the given id = {}", categoryId);
            return Exceptions.getCategoryNotFoundException(categoryId);
        });
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(@RequestParam(value = "offset", required = true) int offset,
                                                              @RequestParam(value = "limit", required = true) int limit) {
        List<Category> allCategories = categoryService.getAllCategories(offset, limit);
        if (!allCategories.isEmpty()) {
            List<CategoryDTO> categoryDTOS = allCategories.stream().filter(Objects::nonNull)
                    .map(category ->
                            modelMapper.map(category, CategoryDTO.class)
                    ).collect(Collectors.toList());

            return ResponseEntity.ok(categoryDTOS);
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
    ResponseEntity<CategoryDTO> replaceCategory(@RequestBody CategoryDTO categoryDTO,
                                                @PathVariable int categoryId) {
        Category newCategory = modelMapper.map(categoryDTO, Category.class);
        return categoryService.getCategoryById(categoryId)
                .map(category -> {
                    category.setName(newCategory.getName());
                    category.setItems(newCategory.getItems());
                    return categoryService.update(category)
                            .map(updatedCategory ->
                                    ResponseEntity.ok(modelMapper.map(updatedCategory, CategoryDTO.class))
                            ).orElseGet(() -> {
                                LOGGER.warn("Unable to save the given category with the id={}", categoryId);
                                return ResponseEntity.noContent().build();
                            });
                })
                .orElseGet(() -> {
                    Exceptions.getCategoryNotFoundException(categoryId);
                    LOGGER.warn("No Category found for the given id={}", categoryId);
                    throw Exceptions.getCategoryNotFoundException(categoryId);
                });
    }

}
