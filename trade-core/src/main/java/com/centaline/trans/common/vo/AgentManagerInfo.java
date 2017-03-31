package com.centaline.trans.common.vo;

import java.util.ArrayList;
import java.util.List;

import com.centaline.trans.cases.vo.CaseDetailProcessorVO;

public class AgentManagerInfo {
	private String agentId;

	private String agentName;

	private String agentPhone;

	private String grpName;

	private String mcId;

	private String mcName;

	private String mcMobile;

	// 交易顾问
	private String cpId;

	private String cpName;

	private String cpMobile;

	// 助理
	private String asId;

	private String asName;

	private String asMobile;

	// 合作顾问
	private List<CaseDetailProcessorVO> proList = new ArrayList<CaseDetailProcessorVO>();

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentPhone() {
		return agentPhone;
	}

	public void setAgentPhone(String agentPhone) {
		this.agentPhone = agentPhone;
	}

	public String getGrpName() {
		return grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public String getMcName() {
		return mcName;
	}

	public void setMcName(String mcName) {
		this.mcName = mcName;
	}

	public String getMcMobile() {
		return mcMobile;
	}

	public void setMcMobile(String mcMobile) {
		this.mcMobile = mcMobile;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getMcId() {
		return mcId;
	}

	public void setMcId(String mcId) {
		this.mcId = mcId;
	}

	public String getCpId() {
		return cpId;
	}

	public void setCpId(String cpId) {
		this.cpId = cpId;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getCpMobile() {
		return cpMobile;
	}

	public void setCpMobile(String cpMobile) {
		this.cpMobile = cpMobile;
	}

	public String getAsId() {
		return asId;
	}

	public void setAsId(String asId) {
		this.asId = asId;
	}

	public String getAsName() {
		return asName;
	}

	public void setAsName(String asName) {
		this.asName = asName;
	}

	public String getAsMobile() {
		return asMobile;
	}

	public void setAsMobile(String asMobile) {
		this.asMobile = asMobile;
	}

	public List<CaseDetailProcessorVO> getProList() {
		return proList;
	}

	public void setProList(List<CaseDetailProcessorVO> proList) {
		this.proList = proList;
	}

}
