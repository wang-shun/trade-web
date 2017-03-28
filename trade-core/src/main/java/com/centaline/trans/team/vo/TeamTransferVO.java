package com.centaline.trans.team.vo;

import java.util.List;
import com.centaline.trans.cases.entity.ToCaseInfo;

public class TeamTransferVO {

	private List<ToCaseInfo> caseInfoList;

	private String orgId;

	public List<ToCaseInfo> getCaseInfoList() {
		return caseInfoList;
	}

	public void setCaseInfoList(List<ToCaseInfo> caseInfoList) {
		this.caseInfoList = caseInfoList;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}
