package com.progex.tracker.category.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.progex.tracker.item.dto.ItemDTO;
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
public class CategoryDTO {
    @Id
    private int id;

    private @NonNull String name;

    @JsonIgnoreProperties("category")
    private List<ItemDTO> items;

}
