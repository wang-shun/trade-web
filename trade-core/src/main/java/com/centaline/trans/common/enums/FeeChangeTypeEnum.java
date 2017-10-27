package com.centaline.trans.common.enums;

/**
 * @Description:费用调整类型
 * @author： wbwangxj
 * @date:2017年9月22日
 * @version:
 */
public enum FeeChangeTypeEnum {
	FPSD("1", "发票税点"),
    TBG("2", "退报告"),
    BD("3", "爆单"),
	WD("4","无");

    private String code;
    private String name;

    private FeeChangeTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据code获取Name
     * 
     * @param code
     * @return
     */
    public static String getName(String code) {
        for (FeeChangeTypeEnum cte : FeeChangeTypeEnum.values()) {
            if (code.equalsIgnoreCase(cte.getCode()))
                return cte.getCode();
        }
        return null;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
