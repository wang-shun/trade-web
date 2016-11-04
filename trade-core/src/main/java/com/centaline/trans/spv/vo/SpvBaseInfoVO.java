package com.centaline.trans.spv.vo;

import java.util.ArrayList;
import java.util.List;

import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.entity.ToSpvAccount;
import com.centaline.trans.spv.entity.ToSpvCust;
import com.centaline.trans.spv.entity.ToSpvDe;
import com.centaline.trans.spv.entity.ToSpvDeDetail;
import com.centaline.trans.spv.entity.ToSpvProperty;

public class SpvBaseInfoVO {

	private ToSpv toSpv;

	private List<ToSpvCust> spvCustList = new ArrayList<ToSpvCust>();

	private ToSpvProperty toSpvProperty;

	private List<ToSpvAccount> toSpvAccountList = new ArrayList<ToSpvAccount>();
	
	private List<ToSpvAccountVO> toSpvAccountListVo = new ArrayList<ToSpvAccountVO>();

	private ToSpvDe toSpvDe;

	private List<ToSpvDeDetail> toSpvDeDetailList = new ArrayList<ToSpvDeDetail>();
	
	private String handle;
	
	public List<ToSpvCust> getSpvCustList() {
		return spvCustList;
	}

	public void setSpvCustList(List<ToSpvCust> spvCustList) {
		this.spvCustList = spvCustList;
	}

	public ToSpvProperty getToSpvProperty() {
		return toSpvProperty;
	}

	public void setToSpvProperty(ToSpvProperty toSpvProperty) {
		this.toSpvProperty = toSpvProperty;
	}

	public List<ToSpvAccount> getToSpvAccountList() {
		return toSpvAccountList;
	}

	public void setToSpvAccountList(List<ToSpvAccount> toSpvAccountList) {
		this.toSpvAccountList = toSpvAccountList;
	}

	public ToSpvDe getToSpvDe() {
		return toSpvDe;
	}

	public void setToSpvDe(ToSpvDe toSpvDe) {
		this.toSpvDe = toSpvDe;
	}

	public List<ToSpvDeDetail> getToSpvDeDetailList() {
		return toSpvDeDetailList;
	}

	public void setToSpvDeDetailList(List<ToSpvDeDetail> toSpvDeDetailList) {
		this.toSpvDeDetailList = toSpvDeDetailList;
	}

	public ToSpv getToSpv() {
		return toSpv;
	}

	public void setToSpv(ToSpv toSpv) {
		this.toSpv = toSpv;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	public String getHandle() {
		return handle;
	}
	public List<ToSpvAccountVO> getToSpvAccountListVo() {
		return toSpvAccountListVo;
	}

	public void setToSpvAccountListVo(List<ToSpvAccountVO> toSpvAccountListVo) {
		this.toSpvAccountListVo = toSpvAccountListVo;
	}
}
