package com.m2l.meta.enum_class;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public enum GenderEnum {

    @JsonProperty("MALE")
    MALE(0),
    @JsonProperty("FEMALE")
    FEMALE(1),
    @JsonProperty("OTHER")
    OTHER(2),
    ;

    private static Map<Integer, GenderEnum> lookup;
    private Integer value;

    GenderEnum(Integer value) {
        this.value = value;
    }

    static {
        try {
            GenderEnum[] vals = GenderEnum.values();
            lookup = new HashMap<Integer, GenderEnum>(vals.length);

            for (GenderEnum rpt : vals)
                lookup.put(rpt.getValue(), rpt);
        } catch (Exception ignored) {

        }
    }

    public static GenderEnum fromValue(Integer value) {
        return lookup.get(value);
    }

    public static Integer getStatusEnum(String val) {
        return switch (val) {
            case "MALE" -> 0;
            case "FEMALE" -> 1;
            case "OTHER" -> 2;
            default -> null;
        };
    }

    public Integer getValue() {
        return this.value;
    }
}
