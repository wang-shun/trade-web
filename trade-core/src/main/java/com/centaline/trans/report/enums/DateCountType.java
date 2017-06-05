//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.centaline.trans.report.enums;

public enum DateCountType {
    JDS_TYPE("JDS", "接单数"),
    QYS_TYPE("QYS", "签约数"),
    GHS_TYPE("GHS", "过户数"),
    JAS_TYPE("JAS", "结案数"),
    NOT_FOUND("NOT_FOUND", "未知的枚举类型");

    private String name;
    private String desc;

    private DateCountType(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public static DateCountType from(String name) {
        DateCountType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            DateCountType dateCountType = var1[var3];
            if(dateCountType.getName().equals(name)) {
                return dateCountType;
            }
        }

        return NOT_FOUND;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
