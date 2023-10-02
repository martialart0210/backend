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
public enum ChatType {

    @JsonProperty("DOJO")
    DOJO(0),
    @JsonProperty("PRIVATE")
    PRIVATE(1),
    ;

    private static Map<Integer, ChatType> lookup;
    private Integer value;


    static {
        try {
            ChatType[] vals = ChatType.values();
            lookup = new HashMap<Integer, ChatType>(vals.length);

            for (ChatType rpt : vals)
                lookup.put(rpt.getValue(), rpt);
        } catch (Exception ignored) {

        }
    }

    public static ChatType fromValue(Integer value) {
        return lookup.get(value);
    }

    public static Integer getStatusEnum(String val) {
        return switch (val) {
            case "DOJO" -> 0;
            case "PRIVATE" -> 1;
            default -> null;
        };
    }

    public Integer getValue() {
        return this.value;
    }
}
