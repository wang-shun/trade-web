package com.centaline.trans.spv.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ToCashFlow {
    private Long pkid;
    private String caseCode;
    private String cashFlowType;
    private String cashFlowTypeName;
    private String flowDirection;
    private BigDecimal flowAmount;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date flowTime;
    private String flowTimeStr;
    private String cashItem;
    private String initiator;
    
    
    /**
     * 冗余的3个字段
     */
    private double money;
    private String item;
    private int direction;

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

    public String getCashFlowType() {
        return cashFlowType;
    }

    public void setCashFlowType(String cashFlowType) {
        this.cashFlowType = cashFlowType == null ? null : cashFlowType.trim();
    }

    public String getFlowDirection() {
        return flowDirection;
    }

    public void setFlowDirection(String flowDirection) {
        this.flowDirection = flowDirection == null ? null : flowDirection.trim();
    }

    public BigDecimal getFlowAmount() {
        return flowAmount;
    }

    public void setFlowAmount(BigDecimal flowAmount) {
        this.flowAmount = flowAmount;
    }

    public Date getFlowTime() {
        return flowTime;
    }

    public void setFlowTime(Date flowTime) {
        this.flowTime = flowTime;
    }

	public String getCashFlowTypeName() {
		return cashFlowTypeName;
	}

	public String getFlowTimeStr() {
		return flowTimeStr;
	}

	public void setCashFlowTypeName(String cashFlowTypeName) {
		this.cashFlowTypeName = cashFlowTypeName;
	}

	public void setFlowTimeStr(String flowTimeStr) {
		this.flowTimeStr = flowTimeStr;
	}

	public String getCashItem() {
		return cashItem;
	}

	public void setCashItem(String cashItem) {
		this.cashItem = cashItem;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	
}