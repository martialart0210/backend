package com.m2l.meta.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema
public class DojoRequestDto {
    @JsonProperty
    private Long requestId;
    @JsonProperty
    private String requestStatus;
    @JsonProperty
    private Long characterId;
    @JsonProperty
    private String characterName;
    @JsonProperty
    private String gender;
    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Date Pattern : yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
