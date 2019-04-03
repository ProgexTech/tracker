package com.progex.tracker.item.resource;

import com.progex.tracker.exceptions.Exceptions;
import com.progex.tracker.item.dto.Item;
import com.progex.tracker.item.entity.ItemEntity;
import com.progex.tracker.item.service.ItemService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
    public ResponseEntity<Item> createItem(@Validated @RequestBody Item item) {
         return  itemService.getCategoryById(item.getCategoryEntity()
                .getId()).map(categoryEntity -> {
            ItemEntity itemEntity = modelMapper.map(item, ItemEntity.class);
            itemEntity.setCategoryEntity(categoryEntity);
            ItemEntity savedItem = itemService.insert(itemEntity);
            return ResponseEntity.created(URI.create(BASE_URL_STR +
                    savedItem.getId())).body(modelMapper.map(savedItem, Item.class));
        }).orElseThrow(() -> {
            LOGGER.warn("Given Category is not presented, categoryId = {} ", item.getCategoryEntity().getId());
            throw Exceptions.getCategoryNotFoundException(item.getCategoryEntity().getId());
        });
    }

    @GetMapping("/items/{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable int itemId) {

        return itemService.getItemById(itemId).map(
                item -> ResponseEntity.ok().body(modelMapper.map(item, Item.class))
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
