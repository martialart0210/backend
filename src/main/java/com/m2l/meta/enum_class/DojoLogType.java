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
public enum DojoLogType {


    @JsonProperty("OPEN")
    OPEN(0),
    @JsonProperty("JOIN")
    JOIN(1),
    @JsonProperty("LEFT")
    LEFT(2),
    @JsonProperty("APPOINTED")
    APPOINTED(3),
    @JsonProperty("RELEASED")
    RELEASED(4),
    ;

    private static Map<Integer, DojoLogType> lookup;
    private Integer value;


    static {
        try {
            DojoLogType[] vals = DojoLogType.values();
            lookup = new HashMap<Integer, DojoLogType>(vals.length);

            for (DojoLogType rpt : vals)
                lookup.put(rpt.getValue(), rpt);
        } catch (Exception ignored) {

        }
    }

    public static DojoLogType fromValue(Integer value) {
        return lookup.get(value);
    }

    public static Integer getStatusEnum(String val) {
        return switch (val) {
            case "OPEN" -> 0;
            case "JOIN" -> 1;
            case "LEFT" -> 2;
            case "APPOINTED" -> 3;
            case "RELEASED" -> 4;
            default -> null;
        };
    }

    public Integer getValue() {
        return this.value;
    }
}
