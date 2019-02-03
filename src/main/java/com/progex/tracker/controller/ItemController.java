package com.progex.tracker.controller;

import com.progex.tracker.entity.Item;
import com.progex.tracker.exceptions.RestControllerEntityNotFoundException;
import com.progex.tracker.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
    private static final String BASE_URL_STR = "/api/items/";

    @PostMapping("/items")
    public ResponseEntity<?> insertItem(@RequestBody Item item) {
        Optional<Item> optionalItem = itemService.insert(item);
        if (optionalItem.isPresent()) {
            return ResponseEntity.created(URI.create(BASE_URL_STR +
                    optionalItem.get().getId())).body(optionalItem.get());
        }
        LOGGER.warn("Failed to create given item name = " + item.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/items/{itemId}")
    public ResponseEntity<?> retrieveItem(@PathVariable int itemId) {
        Optional<Item> optionalItem = itemService.getItemById(itemId);
        if (optionalItem.isPresent()) {
           return ResponseEntity.ok().body(optionalItem.get());
        }
        LOGGER.warn("No Item found for the given id = " + itemId);
        throw new RestControllerEntityNotFoundException("Cannot find the Item with the Id = " + itemId);
    }
}
