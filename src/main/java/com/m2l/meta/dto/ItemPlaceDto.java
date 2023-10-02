package com.m2l.meta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ItemPlaceDto extends BaseDto {
    @JsonProperty(namespace = "placeId")
    @Schema(description = "Set Null if place new item, set placeId to remove or move old item")
    private Long placeId;
    @JsonProperty(value = "itemId")
    private Long itemId;
    @JsonProperty(value = "itemName")
    private String itemName;
    @JsonProperty(value = "itemType")
    @Schema(description = "FURNITURE : 0 , FINISHER : 1 , PROP : 2")
    private Integer itemType;
    @JsonProperty(value = "position")
    @Schema(description = "FLOOR : 0 , WALL : 1 ")
    private Integer position;
    @JsonProperty(value = "action")
    @Schema(description = "PLACE / MOVE : 0 , REMOVE : 1 ")
    private Integer action;
    @JsonProperty(value = "posX")
    private Float posX;
    @JsonProperty(value = "posZ")
    private Float posZ;
    @JsonProperty(value = "posY")
    private Float posY;
    @JsonProperty(value = "rotX")
    private Float rotX = (float) 0;
    @JsonProperty(value = "rotZ")
    private Float rotZ = (float) 0;
    @JsonProperty(value = "rotY")
    private Float rotY = (float) 0;
    @JsonProperty(value = "itemLength")
    private Float itemLength = (float) 0;
    @JsonProperty(value = "itemWidth")
    private Float itemWidth = (float) 0;
    @JsonProperty(value = "itemHeight")
    private Float itemHeight = (float) 0;
    @JsonProperty(value = "rotateNumber")
    private Integer rotateNumber = 0;
}
