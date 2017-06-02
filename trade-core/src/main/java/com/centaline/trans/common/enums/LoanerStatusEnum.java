package com.centaline.trans.common.enums;

/**
 * 派单状态枚举类
 * 
 * @author yinjf2
 *
 */
public enum LoanerStatusEnum
{

    ACCEPTING("ACCEPTING", "待接单"), AUDITING("AUDITING", "待审批"), COMPLETED("COMPLETED", "完成"), ACC_REJECTED("ACC_REJECTED", "接单打回"), AUD_REJECTED(
            "AUD_REJECTED", "审批打回"), CANCELED("CANCELED", "誉萃撤单"), CLOSED("CLOSED", "已关闭");

    private String code;
    private String name;

    private LoanerStatusEnum(String code, String name)
    {
        this.code = code;
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
