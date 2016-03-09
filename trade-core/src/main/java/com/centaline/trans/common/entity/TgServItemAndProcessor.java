package com.centaline.trans.common.entity;

import java.util.List;

import com.aist.uam.userorg.remote.vo.User;

public class TgServItemAndProcessor {
    private Long pkid;

    private String caseCode;

    private String srvCode;
    private String srvCat;

    private String processorId;
    
    private String orgId;
    
    private String preProcessorId;
    
    
    private List<User>users;
    
    
    /**
     * 冗余字段
     */
    private String srvName;

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

    public String getSrvCode() {
        return srvCode;
    }

    public void setSrvCode(String srvCode) {
        this.srvCode = srvCode == null ? null : srvCode.trim();
    }

    public String getProcessorId() {
        return processorId;
    }

    public void setProcessorId(String processorId) {
        this.processorId = processorId == null ? null : processorId.trim();
    }

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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

	public String getPreProcessorId() {
		return preProcessorId;
	}

	public void setPreProcessorId(String preProcessorId) {
		this.preProcessorId = preProcessorId;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
}