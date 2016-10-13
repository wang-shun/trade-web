package com.centaline.trans.eloan.vo;

import java.util.ArrayList;
import java.util.List;

import com.centaline.trans.eloan.entity.RcRiskControl;
import com.centaline.trans.eloan.entity.ToRcMortgageCard;
import com.centaline.trans.eloan.entity.ToRcMortgageInfo;

public class ToRcMortgageCardVO {

	private RcRiskControl rcRiskControl;

	private ToRcMortgageCard toRcMortgageCard;

	private List<ToRcMortgageInfo> toRcMortgageInfoList = new ArrayList<ToRcMortgageInfo>();

	public ToRcMortgageCard getToRcMortgageCard() {
		return toRcMortgageCard;
	}

	public void setToRcMortgageCard(ToRcMortgageCard toRcMortgageCard) {
		this.toRcMortgageCard = toRcMortgageCard;
	}

	public List<ToRcMortgageInfo> getToRcMortgageInfoList() {
		return toRcMortgageInfoList;
	}

	public void setToRcMortgageInfoList(List<ToRcMortgageInfo> toRcMortgageInfoList) {
		this.toRcMortgageInfoList = toRcMortgageInfoList;
	}

	public RcRiskControl getRcRiskControl() {
		return rcRiskControl;
	}

	public void setRcRiskControl(RcRiskControl rcRiskControl) {
		this.rcRiskControl = rcRiskControl;
	}

}
