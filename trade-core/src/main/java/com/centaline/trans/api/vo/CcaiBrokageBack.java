package com.centaline.trans.api.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * CCAI银行返利申请 基本信息
 * @author yinchao
 * @date 2017/10/14
 */
public class CcaiBrokageBack {
	//交易系统主键ID 使用GUID
	private String BackID;
	//发起人工号 非域账号
	private String RecordEmpNo;
	//申请时间
	private Date RecordDate;
	// 返利总金额
	private BigDecimal TotalMoney;
	// 备注
	private String Remark;

	public String getBackID() {
		return BackID;
	}

	public void setBackID(String backID) {
		BackID = backID;
	}

	public String getRecordEmpNo() {
		return RecordEmpNo;
	}

	public void setRecordEmpNo(String recordEmpNo) {
		RecordEmpNo = recordEmpNo;
	}

	public Date getRecordDate() {
		return RecordDate;
	}

	public void setRecordDate(Date recordDate) {
		RecordDate = recordDate;
	}

	public BigDecimal getTotalMoney() {
		return TotalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		TotalMoney = totalMoney;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}
}
