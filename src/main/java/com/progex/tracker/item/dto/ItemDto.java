package com.progex.tracker.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.progex.tracker.category.dto.CategoryDto;
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
public class ItemDto {
    @Id
    private int id;
    @JsonProperty("category")
    @NotNull
    private CategoryDto categoryDto;
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
