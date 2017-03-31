package com.centaline.trans.stuff.enums;

/**
 * 
 * @author jjm
 *
 */
public enum CommentType {
                         CMT("CMT", "备注"), BUJIAN("BUJIAN", "补件"), TRACK("TRACK",
                                                                         "跟进"), REJECT("REJECT",
                                                                                       "打回");

    private String code;
    private String value;

    CommentType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }
}
