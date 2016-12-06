package com.centaline.trans.common.vo;


public class TgCooperVo {
    private Long pkid;

    private String caseCode;
    private String srvCode;
    private String srvCat;
    private String srvName;

    private String oldProcessorId;
    
    private String orgId;
    
    private String newProcessorId;
    
    private String newProcessorName;
    
    
    private String oldProcessorName;


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
		this.caseCode = caseCode;
	}


	public String getSrvCode() {
		return srvCode;
	}


	public void setSrvCode(String srvCode) {
		this.srvCode = srvCode;
	}


	public String getSrvCat() {
		return srvCat;
	}


	public void setSrvCat(String srvCat) {
		this.srvCat = srvCat;
	}


	public String getSrvName() {
		return srvName;
	}


	public void setSrvName(String srvName) {
		this.srvName = srvName;
	}


	public String getOldProcessorId() {
		return oldProcessorId;
	}


	public void setOldProcessorId(String oldProcessorId) {
		this.oldProcessorId = oldProcessorId;
	}


	public String getOrgId() {
		return orgId;
	}


	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}


	public String getNewProcessorId() {
		return newProcessorId;
	}


	public void setNewProcessorId(String newProcessorId) {
		this.newProcessorId = newProcessorId;
	}


	public String getNewProcessorName() {
		return newProcessorName;
	}


	public void setNewProcessorName(String newProcessorName) {
		this.newProcessorName = newProcessorName;
	}


	public String getOldProcessorName() {
		return oldProcessorName;
	}


	public void setOldProcessorName(String oldProcessorName) {
		this.oldProcessorName = oldProcessorName;
	}
   
	
}