package com.centaline.trans.spv.vo;

import java.util.List;

import com.centaline.trans.spv.entity.ToSpvAduit;
import com.centaline.trans.spv.entity.ToSpvCashFlow;
import com.centaline.trans.spv.entity.ToSpvCashFlowApply;
import com.centaline.trans.spv.entity.ToSpvCashFlowApplyAttach;
import com.centaline.trans.spv.entity.ToSpvReceipt;
import com.centaline.trans.spv.entity.ToSpvVoucher;

/**
 * 
 * @ClassName: SpvRecordedInfoVO 
 * @Description: 入账VO对象 
 * @author hejf 
 * @date 2016年9月23日 下午2:41:24 
 *
 */
public class SpvRecordedInfoVO {
	
	private ToSpvCashFlowApply toSpvCashFlowApply;
	
	private List<ToSpvCashFlow> toSpvCashFlowList;
	
	private List<ToSpvAduit> toSpvAduitList;
	
	private List<ToSpvReceipt> toSpvReceiptList;
	
	private List<ToSpvCashFlowApplyAttach> toSpvCashFlowApplyAttachList;

	public ToSpvCashFlowApply getToSpvCashFlowApply() {
		return toSpvCashFlowApply;
	}

	public void setToSpvCashFlowApply(ToSpvCashFlowApply toSpvCashFlowApply) {
		this.toSpvCashFlowApply = toSpvCashFlowApply;
	}

	public List<ToSpvCashFlow> getToSpvCashFlowList() {
		return toSpvCashFlowList;
	}

	public void setToSpvCashFlowList(List<ToSpvCashFlow> toSpvCashFlowList) {
		this.toSpvCashFlowList = toSpvCashFlowList;
	}

	public List<ToSpvAduit> getToSpvAduitList() {
		return toSpvAduitList;
	}

	public void setToSpvAduitList(List<ToSpvAduit> toSpvAduitList) {
		this.toSpvAduitList = toSpvAduitList;
	}

	public List<ToSpvReceipt> getToSpvReceiptList() {
		return toSpvReceiptList;
	}

	public void setToSpvReceiptList(List<ToSpvReceipt> toSpvReceiptList) {
		this.toSpvReceiptList = toSpvReceiptList;
	}

	public List<ToSpvCashFlowApplyAttach> getToSpvCashFlowApplyAttachList() {
		return toSpvCashFlowApplyAttachList;
	}

	public void setToSpvCashFlowApplyAttachList(List<ToSpvCashFlowApplyAttach> toSpvCashFlowApplyAttachList) {
		this.toSpvCashFlowApplyAttachList = toSpvCashFlowApplyAttachList;
	}
	
	

}
