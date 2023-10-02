package com.m2l.meta.enum_class;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum FinisherType {

    @JsonProperty("WALLPAPER")
    WALLPAPER(0),
    @JsonProperty("FLOORING")
    FLOORING(1),
    ;

    private static Map<Integer, FinisherType> lookup;
    private Integer value;

    FinisherType(Integer value) {
        this.value = value;
    }

    static {
        try {
            FinisherType[] vals = FinisherType.values();
            lookup = new HashMap<Integer, FinisherType>(vals.length);

            for (FinisherType rpt : vals)
                lookup.put(rpt.getValue(), rpt);
        } catch (Exception ignored) {

        }
    }

    public static FinisherType fromValue(Integer value) {
        return lookup.get(value);
    }

    public static Integer getStatusEnum(String val) {
        return switch (val) {
            case "WALLPAPER" -> 0;
            case "FLOORING" -> 1;
            default -> null;
        };
    }

    public Integer getValue() {
        return this.value;
    }
}
