package com.progex.tracker.impl;

import com.progex.tracker.entity.Item;
import com.progex.tracker.repo.ItemRepository;
import com.progex.tracker.service.ItemService;
import com.progex.tracker.uttility.EntityNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @author indunil
 */
public class ItemServiceImpl implements ItemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Item insert(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Optional<Item> getItemById(int id) {

        Optional<Item> itemOptional = itemRepository.findById(id);
        if (itemOptional.isPresent()) {
            return itemOptional;
        } else {
            LOGGER.warn("Cannot find the Item with the id = {}", id);
            throw new EntityNotFound("Cannot find the Item with the given id =" + id);
        }
    }

    @Override
    public Item update(Item item) {
        return itemRepository.save(item);
    }
}
