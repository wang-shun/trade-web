package com.centaline.trans.cases.vo;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.vo.AgentManagerInfo;
import com.centaline.trans.common.vo.BuyerSellerInfo;

public class CaseBaseVO {
	private ToCase toCase;

	private ToCaseInfo toCaseInfo;

	private ToPropertyInfo toPropertyInfo;

	private BuyerSellerInfo buyerSellerInfo;

	private AgentManagerInfo agentManagerInfo;
	//税费卡
	private String loanType;


	public ToCase getToCase() {
		return toCase;
	}

	public void setToCase(ToCase toCase) {
		this.toCase = toCase;
	}

	public ToCaseInfo getToCaseInfo() {
		return toCaseInfo;
	}

	public void setToCaseInfo(ToCaseInfo toCaseInfo) {
		this.toCaseInfo = toCaseInfo;
	}

	public ToPropertyInfo getToPropertyInfo() {
		return toPropertyInfo;
	}

	public void setToPropertyInfo(ToPropertyInfo toPropertyInfo) {
		this.toPropertyInfo = toPropertyInfo;
	}

	public BuyerSellerInfo getBuyerSellerInfo() {
		return buyerSellerInfo;
	}

	public void setBuyerSellerInfo(BuyerSellerInfo buyerSellerInfo) {
		this.buyerSellerInfo = buyerSellerInfo;
	}

	public AgentManagerInfo getAgentManagerInfo() {
		return agentManagerInfo;
	}

	public void setAgentManagerInfo(AgentManagerInfo agentManagerInfo) {
		this.agentManagerInfo = agentManagerInfo;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
}
