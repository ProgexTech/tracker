package com.progex.tracker.controller;

import com.progex.tracker.dto.ItemDTO;
import com.progex.tracker.entity.Category;
import com.progex.tracker.entity.Item;
import com.progex.tracker.exceptions.RestControllerEntityNotFoundException;
import com.progex.tracker.service.ItemService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

/**
 * @author indunil
 */
@RestController
@RequestMapping("/api")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
    private static final String BASE_URL_STR = "/api/items/";

    @PostMapping("/items")
    public ResponseEntity<ItemDTO> insertItem(@Validated @RequestBody ItemDTO itemDTO) {
        Optional<Category> optionalCategory = itemService.getCategoryById(itemDTO.getCategory().getId());
        if (optionalCategory.isPresent()) {
            Item item = modelMapper.map(itemDTO, Item.class);
            item.setCategory(optionalCategory.get());
            Optional<Item> optionalItem = itemService.insert(item);
            if (optionalItem.isPresent()) {
                return ResponseEntity.created(URI.create(BASE_URL_STR +
                        optionalItem.get().getId())).body(modelMapper.map(optionalItem.get(), ItemDTO.class));
            }
        } else {
            LOGGER.warn("Given Category is not presented, categoryId = {} ", itemDTO.getCategory().getId());
            throw new RestControllerEntityNotFoundException
                    ("Invalid Category with categoryId = "+itemDTO.getCategory().getId());
        }
        LOGGER.warn("Failed to create given item, name = {} ", itemDTO.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/items/{itemId}")
    public ResponseEntity<ItemDTO> retrieveItem(@PathVariable int itemId) {
        Optional<Item> optionalItem = itemService.getItemById(itemId);
        if (optionalItem.isPresent()) {
            return ResponseEntity.ok().body(modelMapper.map(optionalItem.get(), ItemDTO.class));
        }
        LOGGER.warn("No Item found for the given id = {}", itemId);
        throw new RestControllerEntityNotFoundException("Cannot find the Item with the Id = " + itemId);
    }
}
