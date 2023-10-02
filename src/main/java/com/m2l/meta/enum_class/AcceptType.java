package com.m2l.meta.enum_class;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public enum AcceptType {
    @JsonProperty("INSTRUCTOR")
    INSTRUCTOR(0),
    @JsonProperty("SUB_INSTRUCTOR")
    SUB_INSTRUCTOR(1),
    @JsonProperty("TRAINEE")
    TRAINEE(2),
    ;

    private static Map<Integer, AcceptType> lookup;
    private Integer value;

    AcceptType(Integer value) {
        this.value = value;
    }

    static {
        try {
            AcceptType[] vals = AcceptType.values();
            lookup = new HashMap<Integer, AcceptType>(vals.length);

            for (AcceptType rpt : vals)
                lookup.put(rpt.getValue(), rpt);
        } catch (Exception ignored) {

        }
    }

    public static AcceptType fromValue(Integer value) {
        return lookup.get(value);
    }

    public static Integer getStatusEnum(String val) {
        return switch (val) {
            case "INSTRUCTOR" -> 0;
            case "SUB_INSTRUCTOR" -> 1;
            case "TRAINEE" -> 2;
            default -> null;
        };
    }

    public Integer getValue() {
        return this.value;
    }
}
