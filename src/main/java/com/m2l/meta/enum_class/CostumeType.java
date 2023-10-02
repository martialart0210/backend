package com.m2l.meta.enum_class;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum CostumeType {
    @JsonProperty("HAIR")
    HAIR(0),
    @JsonProperty("TOP")
    TOP(1),
    @JsonProperty("BOTTOM")
    BOTTOM(2),
    @JsonProperty("SHOE")
    SHOE(3),
    @JsonProperty("ACCESSORY")
    ACCESSORY(4)
    ;

    private static Map<Integer, CostumeType> lookup;
    private Integer value;

    CostumeType(Integer value) {
        this.value = value;
    }

    static {
        try {
            CostumeType[] vals = CostumeType.values();
            lookup = new HashMap<Integer, CostumeType>(vals.length);

            for (CostumeType rpt : vals)
                lookup.put(rpt.getValue(), rpt);
        } catch (Exception ignored) {

        }
    }

    public static CostumeType fromValue(Integer value) {
        return lookup.get(value);
    }

    public static Integer getStatusEnum(String val) {
        return switch (val) {
            case "HAIR" -> 0;
            case "TOP" -> 1;
            case "BOTTOM" -> 2;
            case "SHOE" -> 3;
            case "ACCESSORY" -> 4;
            default -> null;
        };
    }

    public Integer getValue() {
        return this.value;
    }
}
