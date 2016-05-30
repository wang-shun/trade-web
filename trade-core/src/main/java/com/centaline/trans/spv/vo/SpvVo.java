package com.centaline.trans.spv.vo;

import java.util.List;

import com.centaline.trans.spv.entity.ToCashFlow;
import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.entity.ToSpvDeCond;

public class SpvVo {

	private ToSpv toSpv;
	
	private List<ToSpvDeCond> toSpvDeCondList;
	
	private String[] delIds;
	
	private ToCashFlow toCashFlow;
	
	private List<ToCashFlow> toCashFlowList;
	
	private String[] delFlowIds;

	public ToSpv getToSpv() {
		return toSpv;
	}

	public void setToSpv(ToSpv toSpv) {
		this.toSpv = toSpv;
	}

	public List<ToSpvDeCond> getToSpvDeCondList() {
		return toSpvDeCondList;
	}

	public void setToSpvDeCondList(List<ToSpvDeCond> toSpvDeCondList) {
		this.toSpvDeCondList = toSpvDeCondList;
	}

	public String[] getDelIds() {
		return delIds;
	}

	public void setDelIds(String[] delIds) {
		this.delIds = delIds;
	}

	public ToCashFlow getToCashFlow() {
		return toCashFlow;
	}

	public void setToCashFlow(ToCashFlow toCashFlow) {
		this.toCashFlow = toCashFlow;
	}

	public List<ToCashFlow> getToCashFlowList() {
		return toCashFlowList;
	}

	public void setToCashFlowList(List<ToCashFlow> toCashFlowList) {
		this.toCashFlowList = toCashFlowList;
	}

	public String[] getDelFlowIds() {
		return delFlowIds;
	}

	public void setDelFlowIds(String[] delFlowIds) {
		this.delFlowIds = delFlowIds;
	}
		
}
