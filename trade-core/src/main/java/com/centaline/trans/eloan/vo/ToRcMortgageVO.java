package com.centaline.trans.eloan.vo;

import java.util.ArrayList;
import java.util.List;

import com.centaline.trans.eloan.entity.RcRiskControl;
import com.centaline.trans.eloan.entity.ToRcMortgage;
import com.centaline.trans.eloan.entity.ToRcMortgageInfo;

public class ToRcMortgageVO {
	private RcRiskControl rcRiskControl;

	private ToRcMortgage toRcMortgage;

	private List<ToRcMortgageInfo> toRcMortgageInfoList = new ArrayList<ToRcMortgageInfo>();

	public RcRiskControl getRcRiskControl() {
		return rcRiskControl;
	}

	public void setRcRiskControl(RcRiskControl rcRiskControl) {
		this.rcRiskControl = rcRiskControl;
	}

	public ToRcMortgage getToRcMortgage() {
		return toRcMortgage;
	}

	public void setToRcMortgage(ToRcMortgage toRcMortgage) {
		this.toRcMortgage = toRcMortgage;
	}

	public List<ToRcMortgageInfo> getToRcMortgageInfoList() {
		return toRcMortgageInfoList;
	}

	public void setToRcMortgageInfoList(List<ToRcMortgageInfo> toRcMortgageInfoList) {
		this.toRcMortgageInfoList = toRcMortgageInfoList;
	}

}
