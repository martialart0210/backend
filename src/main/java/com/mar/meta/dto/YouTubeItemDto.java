package com.mar.meta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class YouTubeItemDto {
    @JsonProperty("url")
    private String url;
    @JsonProperty("channelTitle")
    private String channelTitle;

    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;

    @JsonProperty("videoId")
    private String videoId;

    @JsonProperty("playListId")
    private String playListId;

    @JsonProperty("channelId")
    private String channelId;


}
