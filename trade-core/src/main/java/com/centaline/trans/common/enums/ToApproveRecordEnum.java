package com.centaline.trans.common.enums;

/**
 * Created by wbzhouht on 2017/10/26.
 * 交易变更审批历史表中审批状态枚举
 */
public enum  ToApproveRecordEnum {
    DEFUALT("待审核",0),
    AGREE("审批通过",1),
    REJECT("审批不通过",2);
    String name;
    int code;

   private ToApproveRecordEnum(String name,int code){
    this.setName(name);
    this.setCode(code);
   }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
