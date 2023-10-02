package com.mar.meta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class RoomDto extends BaseDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("width")
    private Integer width;

    @JsonProperty("length")
    private Integer length;

    @JsonProperty("height")
    private Integer height;

    @JsonProperty("level")
    private Integer level;
}
