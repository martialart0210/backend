package com.m2l.meta.enum_class;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ShopType {

    @JsonProperty("COSTUME_SHOP")
    COSTUME_SHOP(0),
    @JsonProperty("INTERIOR_SHOP")
    INTERIOR_SHOP(1),
    ;

    private static Map<Integer, ShopType> lookup;
    private Integer value;

    ShopType(Integer value) {
        this.value = value;
    }

    static {
        try {
            ShopType[] vals = ShopType.values();
            lookup = new HashMap<Integer, ShopType>(vals.length);

            for (ShopType rpt : vals)
                lookup.put(rpt.getValue(), rpt);
        } catch (Exception ignored) {

        }
    }

    public static ShopType fromValue(Integer value) {
        return lookup.get(value);
    }

    public static Integer getStatusEnum(String val) {
        return switch (val) {
            case "COSTUME_SHOP" -> 0;
            case "INTERIOR_SHOP" -> 1;
            default -> null;
        };
    }

    public Integer getValue() {
        return this.value;
    }
}
