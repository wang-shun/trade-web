package com.centaline.trans.eloan.vo;

import com.centaline.trans.eloan.entity.RcRiskControl;
import com.centaline.trans.eloan.entity.ToRcForceRegister;

public class ToRcForceRegisterVO {

	private RcRiskControl rcRiskControl;

	private ToRcForceRegister toRcForceRegister;

	public RcRiskControl getRcRiskControl() {
		return rcRiskControl;
	}

	public void setRcRiskControl(RcRiskControl rcRiskControl) {
		this.rcRiskControl = rcRiskControl;
	}

	public ToRcForceRegister getToRcForceRegister() {
		return toRcForceRegister;
	}

	public void setToRcForceRegister(ToRcForceRegister toRcForceRegister) {
		this.toRcForceRegister = toRcForceRegister;
	}

}
