package com.m2l.meta.enum_class;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum RequestStatus {
    @JsonProperty("WAITING")
    WAITING(0),
    @JsonProperty("APPROVED")
    APPROVED(1),
    @JsonProperty("REJECTED")
    REJECTED(2),
    ;

    private static Map<Integer, RequestStatus> lookup;
    private Integer value;

    RequestStatus(Integer value) {
        this.value = value;
    }

    static {
        try {
            RequestStatus[] vals = RequestStatus.values();
            lookup = new HashMap<Integer, RequestStatus>(vals.length);

            for (RequestStatus rpt : vals)
                lookup.put(rpt.getValue(), rpt);
        } catch (Exception ignored) {

        }
    }

    public static RequestStatus fromValue(Integer value) {
        return lookup.get(value);
    }

    public static Integer getStatusEnum(String val) {
        return switch (val) {
            case "WAITING" -> 0;
            case "APPROVED" -> 1;
            case "REJECTED" -> 2;
            default -> null;
        };
    }

    public Integer getValue() {
        return this.value;
    }
}
