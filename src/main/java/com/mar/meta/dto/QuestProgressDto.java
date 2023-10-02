package com.mar.meta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class QuestProgressDto extends BaseDto {
    @JsonProperty("isAccept")
    private boolean isAccept;
    @JsonProperty("isCompleted")
    private boolean isCompleted;
    @JsonProperty("isFinished")
    private boolean isFinished;
    @JsonProperty("performedCount")
    private int performedCount;
    @JsonProperty("maxPerformed")
    private int maxPerformed;
    @JsonProperty("questDescription")
    private String questDescription;
    @JsonProperty("questName")
    private String questName;
}
