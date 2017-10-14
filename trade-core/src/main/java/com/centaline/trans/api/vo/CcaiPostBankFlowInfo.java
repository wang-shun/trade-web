package com.centaline.trans.api.vo;

import java.util.List;

/**
 * CCAI需要的银行返利信息
 * @author yinchao
 * @date 2017/10/14
 */
public class CcaiPostBankFlowInfo {
	// CCAI银行返利申请 基本信息
	private CcaiBrokageBack BrokageBack;
	// CCAI银行返利申请 明细信息 成交报告编号 分成金额 返利银行等
	private List<CcaiBrokageBackDetail> bbdList;

	public CcaiBrokageBack getBrokageBack() {
		return BrokageBack;
	}

	public void setBrokageBack(CcaiBrokageBack brokageBack) {
		BrokageBack = brokageBack;
	}

	public List<CcaiBrokageBackDetail> getBbdList() {
		return bbdList;
	}

	public void setBbdList(List<CcaiBrokageBackDetail> bbdList) {
		this.bbdList = bbdList;
	}
}
