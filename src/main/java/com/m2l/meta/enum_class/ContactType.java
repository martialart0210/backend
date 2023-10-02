package com.m2l.meta.enum_class;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ContactType {
    @JsonProperty("BLOCK")
    BLOCK(0),
    @JsonProperty("FRIEND")
    FRIEND(1),
    @JsonProperty("ALL")
    ALL(2),
    ;

    private static Map<Integer, ContactType> lookup;
    private Integer value;


    static {
        try {
            ContactType[] vals = ContactType.values();
            lookup = new HashMap<Integer, ContactType>(vals.length);

            for (ContactType rpt : vals)
                lookup.put(rpt.getValue(), rpt);
        } catch (Exception ignored) {

        }
    }

    public static ContactType fromValue(Integer value) {
        return lookup.get(value);
    }

    public static Integer getStatusEnum(String val) {
        return switch (val) {
            case "BLOCK" -> 0;
            case "FRIEND" -> 1;
            case "ALL" -> 2;
            default -> null;
        };
    }

    public Integer getValue() {
        return this.value;
    }
}
