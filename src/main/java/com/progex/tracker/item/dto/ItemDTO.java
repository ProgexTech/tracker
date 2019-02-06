package com.progex.tracker.item.dto;

import com.progex.tracker.category.entity.Category;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public class ItemDTO {
    @Id
    private int id;
    @NotNull
    private Category category;
    private @NonNull String name;
    private String calorie;
    private String origin;
    private String description;
    private String code;
    private boolean isAvailable;
    private int pax;
    private BigDecimal price;
    private byte[] picture;
}
