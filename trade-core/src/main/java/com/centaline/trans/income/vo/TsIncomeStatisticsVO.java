package com.centaline.trans.income.vo;

import com.aist.common.utils.excel.annotation.ExcelField;

public class TsIncomeStatisticsVO {
    @ExcelField(title = "案件编号")
    private String caseCode;

    @ExcelField(title = "案件地址")
    private String proAddr;

    @ExcelField(title = "收益类别")
    private String incomeItem;

    @ExcelField(title = "收入金额")
    private String incomeAmount;

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getIncomeItem() {
        return incomeItem;
    }

    public void setIncomeItem(String incomeItem) {
        this.incomeItem = incomeItem == null ? null : incomeItem.trim();
    }

    public String getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(String incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public String getProAddr() {
        return proAddr;
    }

    public void setProAddr(String proAddr) {
        this.proAddr = proAddr;
    }

}