package com.progex.tracker.service;

import com.progex.tracker.entity.Item;

import java.util.Optional;

/**
 * @author indunil
 */
public interface ItemService {
    Optional<Item> insert(Item item);

    Optional<Item> getItemById(int id);

    Item update(Item item);
}
