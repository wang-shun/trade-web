package com.centaline.trans.spv.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ToSpvDeCond {
    private Long pkid;

    private String spvCode;

    private BigDecimal deAmount;

    private String deCondition;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dePreTime;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
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

    public String getDeCondition() {
        return deCondition;
    }

    public void setDeCondition(String deCondition) {
        this.deCondition = deCondition == null ? null : deCondition.trim();
    }

	public Date getDePreTime() {
		return dePreTime;
	}

	public void setDePreTime(Date dePreTime) {
		this.dePreTime = dePreTime;
	}
    
}