package com.m2l.meta.enum_class;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public enum DojoPosition {
    @JsonProperty("사범")
    INSTRUCTOR(0),
    @JsonProperty("부 사범")
    SUB_INSTRUCTOR(1),
    @JsonProperty("수련생")
    TRAINEE(2),
    ;

    private static Map<Integer, DojoPosition> lookup;
    private Integer value;

    DojoPosition(Integer value) {
        this.value = value;
    }

    static {
        try {
            DojoPosition[] vals = DojoPosition.values();
            lookup = new HashMap<Integer, DojoPosition>(vals.length);

            for (DojoPosition rpt : vals)
                lookup.put(rpt.getValue(), rpt);
        } catch (Exception ignored) {

        }
    }

    public static DojoPosition fromValue(Integer value) {
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

    public String getNameInKor() {
        Integer nameEng = this.getValue();
        return switch (nameEng) {
            case 0 ->  "사범";
            case 1 ->  "부 사범";
            case 2 ->  "수련생";
            default -> "";
        };
    }
}
