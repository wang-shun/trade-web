package com.centaline.trans.eval.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.centaline.trans.eval.entity.ToEvaCommPersonAmount;

public class EvalChangeCommVO {
	// 合作人list
	private List<ToEvaCommPersonAmount> coPersonList;
	// 分成人list
	private List<ToEvaCommPersonAmount> sharePersonList;
	// 权证list
	private List<ToEvaCommPersonAmount> warrantPersonList;
	// 佣金合计，总业绩
	private BigDecimal ttlComm;
	// 单数合计
	private int dealCount;
	// caseCode
	private String caseCode;
	// 调佣事项
	private String changeChargesItem;
	// 调佣事由
	private String changeChargesCause;
	// 中介费折评估费总金额
	private BigDecimal agEvalAmount;
	// 调佣类型
	private String changeChargesType;
	
	
	public EvalChangeCommVO() {
		super();
		ArrayList<ToEvaCommPersonAmount> coPersonList = new ArrayList<ToEvaCommPersonAmount>();
		ArrayList<ToEvaCommPersonAmount> sharePersonList = new ArrayList<ToEvaCommPersonAmount>();
		ArrayList<ToEvaCommPersonAmount> warrantPersonList = new ArrayList<ToEvaCommPersonAmount>();
		
		this.coPersonList = coPersonList;
		this.sharePersonList = sharePersonList;
		this.warrantPersonList = warrantPersonList;
	}
	public EvalChangeCommVO(String caseCode) {
		ArrayList<ToEvaCommPersonAmount> coPersonList = new ArrayList<ToEvaCommPersonAmount>();
		ArrayList<ToEvaCommPersonAmount> sharePersonList = new ArrayList<ToEvaCommPersonAmount>();
		ArrayList<ToEvaCommPersonAmount> warrantPersonList = new ArrayList<ToEvaCommPersonAmount>();
		
		this.caseCode=caseCode;
		this.coPersonList = coPersonList;
		this.sharePersonList = sharePersonList;
		this.warrantPersonList = warrantPersonList;
	}
	public List<ToEvaCommPersonAmount> getCoPersonList() {
		return coPersonList;
	}
	public void setCoPersonList(List<ToEvaCommPersonAmount> coPersonList) {
		this.coPersonList = coPersonList;
	}
	public List<ToEvaCommPersonAmount> getSharePersonList() {
		return sharePersonList;
	}
	public void setSharePersonList(List<ToEvaCommPersonAmount> sharePersonList) {
		this.sharePersonList = sharePersonList;
	}
	public List<ToEvaCommPersonAmount> getWarrantPersonList() {
		return warrantPersonList;
	}
	public void setWarrantPersonList(List<ToEvaCommPersonAmount> warrantPersonList) {
		this.warrantPersonList = warrantPersonList;
	}

	public BigDecimal getTtlComm() {
		return ttlComm;
	}
	public void setTtlComm(BigDecimal ttlComm) {
		this.ttlComm = ttlComm;
	}
	public int getDealCount() {
		return dealCount;
	}
	public void setDealCount(int dealCount) {
		this.dealCount = dealCount;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getChangeChargesItem() {
		return changeChargesItem;
	}
	public void setChangeChargesItem(String changeChargesItem) {
		this.changeChargesItem = changeChargesItem;
	}
	public String getChangeChargesCause() {
		return changeChargesCause;
	}
	public void setChangeChargesCause(String changeChargesCause) {
		this.changeChargesCause = changeChargesCause;
	}
	public BigDecimal getAgEvalAmount() {
		return agEvalAmount;
	}
	public void setAgEvalAmount(BigDecimal agEvalAmount) {
		this.agEvalAmount = agEvalAmount;
	}
	public String getChangeChargesType() {
		return changeChargesType;
	}
	public void setChangeChargesType(String changeChargesType) {
		this.changeChargesType = changeChargesType;
	}

}
