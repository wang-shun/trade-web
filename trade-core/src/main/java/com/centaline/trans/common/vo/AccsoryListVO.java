package com.centaline.trans.common.vo;

import java.util.List;

public class AccsoryListVO {
	
	private String partCode;
	private List<Long> pkidList;
	private List<String> accessoryNameList;
	private List<String> accessoryCodeList;
	private List<Long> accesoryPkid;
	
	public List<Long> getPkidList() {
		return pkidList;
	}
	public void setPkidList(List<Long> pkidList) {
		this.pkidList = pkidList;
	}
	public List<String> getAccessoryNameList() {
		return accessoryNameList;
	}
	public void setAccessoryNameList(List<String> accessoryNameList) {
		this.accessoryNameList = accessoryNameList;
	}
	public List<String> getAccessoryCodeList() {
		return accessoryCodeList;
	}
	public void setAccessoryCodeList(List<String> accessoryCodeList) {
		this.accessoryCodeList = accessoryCodeList;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public List<Long> getAccesoryPkid() {
		return accesoryPkid;
	}
	public void setAccesoryPkid(List<Long> accesoryPkid) {
		this.accesoryPkid = accesoryPkid;
	}
}
