package com.progex.tracker.item.resource;

import com.progex.tracker.category.entity.Category;
import com.progex.tracker.exceptions.Exceptions;
import com.progex.tracker.item.dto.ItemDTO;
import com.progex.tracker.item.entity.Item;
import com.progex.tracker.item.service.ItemService;
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
public class ItemResource {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemResource.class);
    private static final String BASE_URL_STR = "/api/items/";

    @PostMapping("/items")
    public ResponseEntity<ItemDTO> createItem(@Validated @RequestBody ItemDTO itemDTO) {
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
            throw Exceptions.getCategoryNotFoundException(itemDTO.getCategory().getId());
        }
        LOGGER.warn("Failed to create given item, name = {} ", itemDTO.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/items/{itemId}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable int itemId) {

        return itemService.getItemById(itemId).map(
                item -> ResponseEntity.ok().body(modelMapper.map(item, ItemDTO.class))
        ).orElseThrow(
                () -> {
                    LOGGER.warn("No Item found for the given id = {}", itemId);
                    throw Exceptions.getItemNotFoundException(itemId);
                }
        );
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Object> deleteItemById(@PathVariable int itemId) {
        return itemService.getItemById(itemId).map(item -> {
            itemService.deleteById(itemId);
            return ResponseEntity.ok().build();
        }).orElseThrow(
                () -> {
                    LOGGER.warn("No Item found for the given id={}", itemId);
                    throw Exceptions.getItemNotFoundException(itemId);
                }
        );
    }
}
