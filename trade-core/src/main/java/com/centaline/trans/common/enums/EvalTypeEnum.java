package com.centaline.trans.common.enums;

/**
 * @Description:评估类型选择
 * @author：jinwl6
 * @date:2017年9月22日
 * @version:
 */
public enum EvalTypeEnum {
	DKPG("1", "贷款评估"),
    SZPG("2", "市值评估"),
    DSPG("3", "贷款-市值评估");

    private String code;
    private String name;

    private EvalTypeEnum(String code, String name) {
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
        for (EvalTypeEnum cte : EvalTypeEnum.values()) {
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
