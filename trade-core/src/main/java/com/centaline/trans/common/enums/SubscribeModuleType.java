package com.centaline.trans.common.enums;

/**
 * Created by caoyuan7 on 2016/10/18.
 */
public enum SubscribeModuleType {

    CASE("1001", "案例"),
    NOT_FOUND("0000", "未知的枚举类型");

    private String value;
    private String desc;


    SubscribeModuleType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
    public static SubscribeModuleType from(String value) {
        for (SubscribeModuleType Enum : SubscribeModuleType.values()) {
            if (Enum.getValue().equals(value)) {
                return Enum;
            }
        }
        return NOT_FOUND;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


}
