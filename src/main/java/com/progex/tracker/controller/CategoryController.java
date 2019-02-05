package com.progex.tracker.controller;

import com.progex.tracker.dto.CategoryDTO;
import com.progex.tracker.entity.Category;
import com.progex.tracker.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

/**
 * @author indunil
 */
@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
    private static final String BASE_URL_STR = "/api/categories/";

    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> insertCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        if (Objects.nonNull(category)) {
            Optional<Category> optionalCategory = categoryService.save(category);
            if (optionalCategory.isPresent()) {
                return ResponseEntity.created(URI.create(BASE_URL_STR +
                        optionalCategory.get().getId())).body(modelMapper.
                        map(optionalCategory.get(), CategoryDTO.class));
            }
        }
        LOGGER.warn("Failed to insert given category, name = {} ", categoryDTO.getName());
        return ResponseEntity.noContent().build();
    }
}
