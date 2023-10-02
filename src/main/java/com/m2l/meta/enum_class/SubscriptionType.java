package com.m2l.meta.enum_class;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum SubscriptionType {

    @JsonProperty("FREE")
    FREE(0),
    @JsonProperty("APPROVED")
    APPROVED(1),
    ;

    private static Map<Integer, SubscriptionType> lookup;
    private Integer value;

    SubscriptionType(Integer value) {
        this.value = value;
    }

    static {
        try {
            SubscriptionType[] vals = SubscriptionType.values();
            lookup = new HashMap<Integer, SubscriptionType>(vals.length);

            for (SubscriptionType rpt : vals)
                lookup.put(rpt.getValue(), rpt);
        } catch (Exception ignored) {

        }
    }

    public static SubscriptionType fromValue(Integer value) {
        return lookup.get(value);
    }

    public static Integer getStatusEnum(String val) {
        return switch (val) {
            case "FREE" -> 0;
            case "APPROVED" -> 1;
            default -> null;
        };
    }

    public Integer getValue() {
        return this.value;
    }
}
