package com.m2l.meta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WardrobeItemDto {
    @JsonProperty
    private Long itemId;
    @JsonProperty
    private String gender;
    @JsonProperty
    private String itemName;
    @JsonProperty
    private String itemDescription;
    @JsonProperty
    private Integer price;
    @JsonProperty
    private boolean isEquiped;
    @JsonProperty
    private String itemType;
}
