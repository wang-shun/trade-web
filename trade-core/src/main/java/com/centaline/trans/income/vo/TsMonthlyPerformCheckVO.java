package com.centaline.trans.income.vo;

import com.aist.common.utils.excel.annotation.ExcelField;

public class TsMonthlyPerformCheckVO {

    @ExcelField(title = "人员")
    private String userName;
	@ExcelField(title = "员工编号")
    private String userCode;
    @ExcelField(title = "当月金融产品完成单数（包含外单）")
    private String monthCount;

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserCode() {
		return userCode;
	}

	public String getMonthCount() {
		return monthCount;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public void setMonthCount(String monthCount) {
		this.monthCount = monthCount;
	}
}
