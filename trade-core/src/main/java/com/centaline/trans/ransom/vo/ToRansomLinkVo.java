package com.centaline.trans.ransom.vo;

/**
 * 赎楼案件关联信息
 * @author wbwumf
 *
 *2017年10月14日
 */
public class ToRansomLinkVo {

	/**
	 * 案件编号
	 */
	private String caseCode;
	
	/**
	 * 产证地址
	 */
	private String propertyAddr;
	
	/**
	 * 交易顾问
	 */
	private String warrent;
	
	/**
	 * 上家
	 */
	private String seller;
	
	/**
	 * 经纪人
	 */
	private String agentName;
	
	/**
	 * 下家
	 */
	private String buyer;
	
	/**
	 * 主贷人姓名
	 */
	private String borrowerName;
	
	/**
	 * 上家手机号码
	 */
	private String sellPhone;
	
	/**
	 * 下家手机号码
	 */
	private String buyPhone;

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getPropertyAddr() {
		return propertyAddr;
	}

	public void setPropertyAddr(String propertyAddr) {
		this.propertyAddr = propertyAddr;
	}

	public String getWarrent() {
		return warrent;
	}

	public void setWarrent(String warrent) {
		this.warrent = warrent;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getSellPhone() {
		return sellPhone;
	}

	public void setSellPhone(String sellPhone) {
		this.sellPhone = sellPhone;
	}

	public String getBuyPhone() {
		return buyPhone;
	}

	public void setBuyPhone(String buyPhone) {
		this.buyPhone = buyPhone;
	}

	
	
}
