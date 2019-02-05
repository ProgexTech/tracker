package com.progex.tracker.category.entity;

import com.progex.tracker.item.entity.Item;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author indunil
 */
@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private @NonNull String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Item> items;

    public void addItem(Item item) {
        if (Objects.isNull(items)){
            items = new ArrayList<>();
        }
        items.add(item);
    }
}
