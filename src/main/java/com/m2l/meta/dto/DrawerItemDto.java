package com.m2l.meta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DrawerItemDto {
    @JsonProperty
    private Long itemId;
    @JsonProperty
    private String itemName;
    @JsonProperty
    private Integer itemPrice;
    @JsonProperty
    private int length;
    @JsonProperty
    private int width;
    @JsonProperty
    private int height;
    @JsonProperty
    private String type;
    @JsonProperty
    private String description;
}
