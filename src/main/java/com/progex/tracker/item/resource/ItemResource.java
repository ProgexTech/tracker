package com.progex.tracker.item.resource;

import com.progex.tracker.exceptions.Exceptions;
import com.progex.tracker.item.dto.ItemDto;
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
    public ResponseEntity<ItemDto> createItem(@Validated @RequestBody ItemDto itemDto) {
        return itemService.getCategoryById(itemDto.getCategoryDto()
                .getId()).map(categoryEntity -> {
            Item item = modelMapper.map(itemDto, Item.class);
            item.setCategory(categoryEntity);
            Item savedItem = itemService.insert(item);
            return ResponseEntity.created(URI.create(BASE_URL_STR +
                    savedItem.getId())).body(modelMapper.map(savedItem, ItemDto.class));
        }).orElseThrow(() -> {
            LOGGER.warn("Given CategoryDto is not presented, categoryId = {} ", itemDto.getCategoryDto().getId());
            throw Exceptions.getCategoryNotFoundException(itemDto.getCategoryDto().getId());
        });
    }

    @GetMapping("/items/{itemId}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable int itemId) {

        return itemService.getItemById(itemId).map(
                item -> ResponseEntity.ok().body(modelMapper.map(item, ItemDto.class))
        ).orElseThrow(
                () -> {
                    LOGGER.warn("No ItemDto found for the given id = {}", itemId);
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
                    LOGGER.warn("No ItemDto found for the given id={}", itemId);
                    throw Exceptions.getItemNotFoundException(itemId);
                }
        );
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<ItemDto> updateItem(@RequestBody ItemDto itemDto, @PathVariable int itemId) {
        if (itemService.getItemById(itemId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        itemDto.setId(itemId);
        Item item = modelMapper.map(itemDto, Item.class);
        item = itemService.update(item);
        return ResponseEntity.ok(modelMapper.map(item, ItemDto.class));
    }
}
