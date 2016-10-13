package com.centaline.trans.spv.vo;

import java.util.List;

import com.centaline.trans.spv.entity.ToSpvAduit;
import com.centaline.trans.spv.entity.ToSpvCashFlowApply;
import com.centaline.trans.spv.entity.ToSpvCashFlowApplyAttach;

/**
 * 
 * @ClassName: SpvChargeOutInfoVO 
 * @Description: 出款VO对象 
 * @author gongjd 
 * @date 2016年9月23日 下午2:41:24 
 *
 */
public class SpvChargeInfoVO {
	
	private ToSpvCashFlowApply toSpvCashFlowApply;
	
	private List<ToSpvAduit> toSpvAduitList;
	
    private List<SpvCaseFlowOutInfoVO> spvCaseFlowOutInfoVOList;
	
	private List<ToSpvCashFlowApplyAttach> toSpvCashFlowApplyAttachList;

	public ToSpvCashFlowApply getToSpvCashFlowApply() {
		return toSpvCashFlowApply;
	}

	public void setToSpvCashFlowApply(ToSpvCashFlowApply toSpvCashFlowApply) {
		this.toSpvCashFlowApply = toSpvCashFlowApply;
	}

	public List<ToSpvAduit> getToSpvAduitList() {
		return toSpvAduitList;
	}

	public void setToSpvAduitList(List<ToSpvAduit> toSpvAduitList) {
		this.toSpvAduitList = toSpvAduitList;
	}

	public List<SpvCaseFlowOutInfoVO> getSpvCaseFlowOutInfoVOList() {
		return spvCaseFlowOutInfoVOList;
	}

	public void setSpvCaseFlowOutInfoVOList(List<SpvCaseFlowOutInfoVO> spvCaseFlowOutInfoVOList) {
		this.spvCaseFlowOutInfoVOList = spvCaseFlowOutInfoVOList;
	}

	public List<ToSpvCashFlowApplyAttach> getToSpvCashFlowApplyAttachList() {
		return toSpvCashFlowApplyAttachList;
	}

	public void setToSpvCashFlowApplyAttachList(List<ToSpvCashFlowApplyAttach> toSpvCashFlowApplyAttachList) {
		this.toSpvCashFlowApplyAttachList = toSpvCashFlowApplyAttachList;
	}
	
}
