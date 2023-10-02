package com.m2l.meta.enum_class;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ItemType {

    @JsonProperty("FURNITURE")
    FURNITURE(0),
    @JsonProperty("FINISHER")
    FINISHER(1),
    @JsonProperty("PROP")
    PROP(2),
    @JsonProperty("OTHER")
    OTHER(3),
    ;

    private static Map<Integer, ItemType> lookup;
    private Integer value;

    ItemType(Integer value) {
        this.value = value;
    }

    static {
        try {
            ItemType[] vals = ItemType.values();
            lookup = new HashMap<Integer, ItemType>(vals.length);

            for (ItemType rpt : vals)
                lookup.put(rpt.getValue(), rpt);
        } catch (Exception ignored) {

        }
    }

    public static ItemType fromValue(Integer value) {
        return lookup.get(value);
    }

    public static Integer getStatusEnum(String val) {
        return switch (val) {
            case "WALL_MOUNTED" -> 0;
            case "FLOOR_MOUNTED" -> 1;
            case "PROP" -> 2;
            case "OTHER" -> 3;
            default -> null;
        };
    }

    public Integer getValue() {
        return this.value;
    }
}
