package com.centaline.trans.spv.vo;

import java.util.List;

import com.centaline.trans.spv.entity.ToSpvCloseApply;
import com.centaline.trans.spv.entity.ToSpvCloseApplyAudit;

/**
 * ClassName: SpvCloseInfoVO <br/> 
 * Function: 资金监管合约中止/结束VO对象 <br/> 
 * date: 2016年11月21日 上午9:52:39 <br/> 
 * 
 * @author gongjd 
 * @since JDK 1.8
 */
public class SpvCloseInfoVO {
	
	private ToSpvCloseApply toSpvCloseApply;
	
	private List<ToSpvCloseApplyAudit> toSpvCloseApplyAuditList;

	public ToSpvCloseApply getToSpvCloseApply() {
		return toSpvCloseApply;
	}

	public void setToSpvCloseApply(ToSpvCloseApply toSpvCloseApply) {
		this.toSpvCloseApply = toSpvCloseApply;
	}

	public List<ToSpvCloseApplyAudit> getToSpvCloseApplyAuditList() {
		return toSpvCloseApplyAuditList;
	}

	public void setToSpvCloseApplyAuditList(List<ToSpvCloseApplyAudit> toSpvCloseApplyAuditList) {
		this.toSpvCloseApplyAuditList = toSpvCloseApplyAuditList;
	}

}
