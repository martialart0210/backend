package com.m2l.meta.enum_class;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum PropType {

    @JsonProperty("WALL_MOUNTED")
    WALL_MOUNTED(0),
    @JsonProperty("FLOOR_MOUNTED")
    FLOOR_MOUNTED(1),
    ;

    private static Map<Integer, PropType> lookup;
    private Integer value;

    PropType(Integer value) {
        this.value = value;
    }

    static {
        try {
            PropType[] vals = PropType.values();
            lookup = new HashMap<Integer, PropType>(vals.length);

            for (PropType rpt : vals)
                lookup.put(rpt.getValue(), rpt);
        } catch (Exception ignored) {

        }
    }

    public static PropType fromValue(Integer value) {
        return lookup.get(value);
    }

    public static Integer getStatusEnum(String val) {
        return switch (val) {
            case "WALL_MOUNTED" -> 0;
            case "FLOOR_MOUNTED" -> 1;
            default -> null;
        };
    }

    public Integer getValue() {
        return this.value;
    }
}
