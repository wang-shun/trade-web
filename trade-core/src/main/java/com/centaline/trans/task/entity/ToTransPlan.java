package com.centaline.trans.task.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
/**交易计划*/
public class ToTransPlan {
    private Long pkid;

    private String caseCode;

    private String partCode;
    private String partName;
    private String lampStatus;
    private String redLock;
    
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm", locale = "zh", timezone = "GMT+8")
    @JSONField(format= "yyyy-MM-dd HH:mm" )
    private Date estPartTime;
    private String estPartTimeStr;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode == null ? null : partCode.trim();
    }

    public Date getEstPartTime() {
        return estPartTime;
    }

    public void setEstPartTime(Date estPartTime) {
        this.estPartTime = estPartTime;
    }

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getEstPartTimeStr() {
		return estPartTimeStr;
	}

	public void setEstPartTimeStr(String estPartTimeStr) {
		this.estPartTimeStr = estPartTimeStr;
	}

    public String getLampStatus() {
		return lampStatus;
	}

	public String getRedLock() {
		return redLock;
	}

	public void setLampStatus(String lampStatus) {
		this.lampStatus = lampStatus;
	}

	public void setRedLock(String redLock) {
		this.redLock = redLock;
	}

}