package com.progex.tracker.service;

import com.progex.tracker.entity.Item;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author indunil
 */
@Service
public interface ItemService {
    Item insert(Item item);

    Optional<Item> getItemById(int id);

    Item update(Item item);
}
