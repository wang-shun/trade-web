package com.centaline.trans.spv.vo;

import java.util.ArrayList;
import java.util.List;

public class SpvRecordedsVO {
	private List<SpvRecordedsVOItem> items = new ArrayList<SpvRecordedsVOItem>();
	public void setItems(List<SpvRecordedsVOItem> items) {
		this.items = items;
	}
	public List<SpvRecordedsVOItem> getItems() {
		return items;
	}
	
	//产品类型
	private String prdCode;
	//监管金额
	private Double amount;
	//物业地址
	private String prAddr;
	//收款人名称
	private String spvAccountName;
	//收款人账户
	private String spvAccountCode;
	//收款人开户行
	private String spvAccountBank;
	
	
	//监管合约编号
    private String spvConCode;
    
    private String handle;
    
    private String taskId;
    
    private Boolean chargeInAppr;
    
    private String source;
    
    private String instCode;
    
    private String caseCode;
    
 	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Boolean getChargeInAppr() {
		return chargeInAppr;
	}
	public void setChargeInAppr(Boolean chargeInAppr) {
		this.chargeInAppr = chargeInAppr;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getInstCode() {
		return instCode;
	}
	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}
	public String getSpvConCode() {
		return spvConCode;
	}
	public void setSpvConCode(String spvConCode) {
		this.spvConCode = spvConCode;
	}
	public String getPrdCode() {
		return prdCode;
	}
	public void setPrdCode(String prdCode) {
		this.prdCode = prdCode;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getPrAddr() {
		return prAddr;
	}
	public void setPrAddr(String prAddr) {
		this.prAddr = prAddr;
	}
	public String getSpvAccountName() {
		return spvAccountName;
	}
	public void setSpvAccountName(String spvAccountName) {
		this.spvAccountName = spvAccountName;
	}
	public String getSpvAccountCode() {
		return spvAccountCode;
	}
	public void setSpvAccountCode(String spvAccountCode) {
		this.spvAccountCode = spvAccountCode;
	}
	public String getSpvAccountBank() {
		return spvAccountBank;
	}
	public void setSpvAccountBank(String spvAccountBank) {
		this.spvAccountBank = spvAccountBank;
	}
}
