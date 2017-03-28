package com.centaline.trans.spv.entity;

import java.math.BigDecimal;

public class ToSpvDeRec {
    private Long pkid;

    private Long condId;

    private String spvCode;

    private BigDecimal deAmount;

    private String operId;

    private String deCond;

    private String remark;
    
    private String processInstanceId;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public Long getCondId() {
        return condId;
    }

    public void setCondId(Long condId) {
        this.condId = condId;
    }

    public String getSpvCode() {
        return spvCode;
    }

    public void setSpvCode(String spvCode) {
        this.spvCode = spvCode == null ? null : spvCode.trim();
    }

    public BigDecimal getDeAmount() {
        return deAmount;
    }

    public void setDeAmount(BigDecimal deAmount) {
        this.deAmount = deAmount;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId == null ? null : operId.trim();
    }

    public String getDeCond() {
        return deCond;
    }

    public void setDeCond(String deCond) {
        this.deCond = deCond == null ? null : deCond.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
    
}