package com.m2l.meta.enum_class;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * The enum AccountType.
 *
 * @author <a href="mailto:phuoc@dxplus.io">phuoc</a>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum AccountType {

    @JsonProperty("NORMAL")
    NORMAL(0),
    @JsonProperty("PRO")
    PRO(1),
    ;

    private static Map<Integer, AccountType> lookup;
    private Integer value;


    static {
        try {
            AccountType[] vals = AccountType.values();
            lookup = new HashMap<Integer, AccountType>(vals.length);

            for (AccountType rpt : vals)
                lookup.put(rpt.getValue(), rpt);
        } catch (Exception ignored) {

        }
    }

    public static AccountType fromValue(Integer value) {
        return lookup.get(value);
    }

    public static Integer getStatusEnum(String val) {
        return switch (val) {
            case "NORMAL" -> 0;
            case "PRO" -> 1;
            default -> null;
        };
    }

    public Integer getValue() {
        return this.value;
    }
}
