package com.centaline.trans.bankRebate.vo;

import java.util.List;

import com.centaline.trans.bankRebate.entity.ToBankRebate;
import com.centaline.trans.bankRebate.entity.ToBankRebateInfo;

public class ToBankRebateInfoVO {

	private ToBankRebate toBankRebate;

	private List<ToBankRebateInfo> toBankRebateInfoList;

	public List<ToBankRebateInfo> getToBankRebateInfoList() {
		return toBankRebateInfoList;
	}

	public ToBankRebate getToBankRebate() {
		return toBankRebate;
	}

	public void setToBankRebate(ToBankRebate toBankRebate) {
		this.toBankRebate = toBankRebate;
	}

	public void setToBankRebateInfoList(List<ToBankRebateInfo> toBankRebateInfoList) {
		this.toBankRebateInfoList = toBankRebateInfoList;
	}
	 
	 
}
