package com.centaline.trans.spv.vo;

import java.util.List;

import com.centaline.trans.spv.entity.ToSpvCashFlow;
import com.centaline.trans.spv.entity.ToSpvReceipt;
import com.centaline.trans.spv.entity.ToSpvVoucher;

/**
 * 
 * @ClassName: SpvCaseFlowOutInfoVO 
 * @Description: 出账VO对象 
 * @author gongjd 
 * @date 2016年9月29日 下午1:17:38 
 *
 */
public class SpvCaseFlowOutInfoVO {
	
	private ToSpvCashFlow toSpvCashFlow;
	
	private List<ToSpvVoucher> toSpvVoucherList;
	
	private List<ToSpvReceipt> toSpvReceiptList;

	public ToSpvCashFlow getToSpvCashFlow() {
		return toSpvCashFlow;
	}

	public void setToSpvCashFlow(ToSpvCashFlow toSpvCashFlow) {
		this.toSpvCashFlow = toSpvCashFlow;
	}

	public List<ToSpvVoucher> getToSpvVoucherList() {
		return toSpvVoucherList;
	}

	public void setToSpvVoucherList(List<ToSpvVoucher> toSpvVoucherList) {
		this.toSpvVoucherList = toSpvVoucherList;
	}

	public List<ToSpvReceipt> getToSpvReceiptList() {
		return toSpvReceiptList;
	}

	public void setToSpvReceiptList(List<ToSpvReceipt> toSpvReceiptList) {
		this.toSpvReceiptList = toSpvReceiptList;
	}
	
	

}
