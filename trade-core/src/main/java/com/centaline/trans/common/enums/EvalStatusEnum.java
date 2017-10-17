package com.centaline.trans.common.enums;

/**
 * @Description:TODO
 * @author：jinwl6
 * @date:2017年9月22日
 * @version:
 */
public enum EvalStatusEnum {
       
	YSQ("1", "已申请"),
    YSB("2", "已上报"),
    YCNBG("3", "已出具报告"),
	YSYBG("4", "已使用报告"),
	YCQ("5","重启"),
	BBH("6","驳回"),
	YBD("7","爆单");

    private String code;
    private String name;

    private EvalStatusEnum(String code, String name) {
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
        for (EvalStatusEnum cte : EvalStatusEnum.values()) {
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
