package com.centaline.trans.task.vo;

import java.util.List;

public class ServiceChangeApplyVO {

	private List<Long> pkid;
	private List<String> reason;
	private String partCode;
	
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public List<String> getReason() {
		return reason;
	}
	public void setReason(List<String> reason) {
		this.reason = reason;
	}
	public List<Long> getPkid() {
		return pkid;
	}
	public void setPkid(List<Long> pkid) {
		this.pkid = pkid;
	}
	
	
}
