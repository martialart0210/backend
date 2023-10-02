package com.m2l.meta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class QuestProgressDto extends BaseDto {
    @JsonProperty("questId")
    private Long questId;
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
