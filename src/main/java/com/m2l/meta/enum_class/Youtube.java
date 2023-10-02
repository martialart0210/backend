package com.m2l.meta.enum_class;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum Youtube {

    @JsonProperty("RECOMMEND")
    RECOMMEND(0),
    @JsonProperty("PLAYLIST")
    PLAYLIST(1),
    @JsonProperty("VIDEOS")
    VIDEOS(2),
    ;

    private Integer value;

    Youtube(Integer value) {
        this.value = value;
    }
}
