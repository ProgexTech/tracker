package com.progex.tracker.category.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.progex.tracker.item.dto.Item;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Id;
import java.util.List;

/**
 * @author indunil
 */
@Getter
@Setter
public class Category {
    @Id
    private int id;

    private @NonNull String name;

    @JsonProperty("items")
    @JsonIgnoreProperties("categoryEntity")
    private List<Item> itemEntities;

}
