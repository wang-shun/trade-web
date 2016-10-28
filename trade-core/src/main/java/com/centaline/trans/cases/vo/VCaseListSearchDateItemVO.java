package com.centaline.trans.cases.vo;

import java.util.Date;


public class VCaseListSearchDateItemVO {

    private String code; //时间类型编码
    private Date startDate; //开始时间
    private Date endDate;    //结束时间
	public String getCode() {
		return code;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
    
}
