package com.m2l.meta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GameDto {
    @JsonProperty
    private Long gameId;
    @JsonProperty
    private String gameName;
    @JsonProperty
    private Integer dailyRecord;
    @JsonProperty
    private Integer bestRecord;
    @JsonProperty
    private Boolean isClaimed;
    @JsonProperty
    private Integer reward;
    @JsonProperty
    private Integer recordReward;
}
