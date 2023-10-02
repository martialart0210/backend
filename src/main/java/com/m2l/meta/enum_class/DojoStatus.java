package com.m2l.meta.enum_class;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class DojoStatus.
 *
 * @author <a href="mailto:an@dxplus.io">an</a>
 */
@Getter
public enum DojoStatus {
    @JsonProperty("IN_OPERATION")
    IN_OPERATION(0),
    @JsonProperty("CLOSED")
    CLOSED(1),
    ;

    private static Map<Integer, DojoStatus> lookup;
    private Integer value;

    DojoStatus(Integer value) {
        this.value = value;
    }

    static {
        try {
            DojoStatus[] vals = DojoStatus.values();
            lookup = new HashMap<Integer, DojoStatus>(vals.length);

            for (DojoStatus rpt : vals)
                lookup.put(rpt.getValue(), rpt);
        } catch (Exception ignored) {

        }
    }

    public static DojoStatus fromValue(Integer value) {
        return lookup.get(value);
    }

    public static Integer getStatusEnum(String val) {
        return switch (val) {
            case "IN_OPERATION" -> 0;
            case "CLOSED" -> 1;
            default -> null;
        };
    }

    public Integer getValue() {
        return this.value;
    }

}
