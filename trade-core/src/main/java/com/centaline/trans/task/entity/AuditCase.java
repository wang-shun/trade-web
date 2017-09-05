package com.centaline.trans.task.entity;

import java.util.Date;

/**
 * 
 * 
 * @author xiefei1
 *案件审核实体
 */
public class AuditCase {
	private String caseCode;
	
	private String propertyAddress;
	
	private String daiKuanQuanZhenName;
	
	private String guoHuQuanZhenName;
	
	private String yeWuYuanName;
	
	private Date importTime;
	
	private int VALIDCOUNT;
	
	private int INVALIDCOUNT;
	
	public int getVALIDCOUNT() {
		return VALIDCOUNT;
	}

	public void setVALIDCOUNT(int vALIDCOUNT) {
		VALIDCOUNT = vALIDCOUNT;
	}

	public int getINVALIDCOUNT() {
		return INVALIDCOUNT;
	}

	public void setINVALIDCOUNT(int iNVALIDCOUNT) {
		INVALIDCOUNT = iNVALIDCOUNT;
	}



	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public String getDaiKuanQuanZhenName() {
		return daiKuanQuanZhenName;
	}

	public void setDaiKuanQuanZhenName(String daiKuanQuanZhenName) {
		this.daiKuanQuanZhenName = daiKuanQuanZhenName;
	}

	public String getGuoHuQuanZhenName() {
		return guoHuQuanZhenName;
	}

	public void setGuoHuQuanZhenName(String guoHuQuanZhenName) {
		this.guoHuQuanZhenName = guoHuQuanZhenName;
	}

	public String getYeWuYuanName() {
		return yeWuYuanName;
	}

	public void setYeWuYuanName(String yeWuYuanName) {
		this.yeWuYuanName = yeWuYuanName;
	}

	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}
	

}
