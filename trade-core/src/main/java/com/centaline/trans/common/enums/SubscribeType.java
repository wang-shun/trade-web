package com.centaline.trans.common.enums;

/**
 * Created by caoyuan7 on 2016/10/18.
 */
public enum SubscribeType {

    COLLECTION("2001", "收藏"),
    NOT_FOUND("0000", "未知的枚举类型");
    private String value;
    private String desc;


    SubscribeType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static SubscribeType from(String value) {
        for (SubscribeType Enum : SubscribeType.values()) {
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
