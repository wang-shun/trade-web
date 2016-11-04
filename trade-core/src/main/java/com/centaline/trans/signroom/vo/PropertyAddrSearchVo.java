package com.centaline.trans.signroom.vo;

/**
 * 签约室预约产证地址查询
 * 
 * @author yinjf2
 *
 */
public class PropertyAddrSearchVo {

	private String inputValue; // 用户输入的产证地址信息

	private String agentCode; // 经纪人code

	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

}
