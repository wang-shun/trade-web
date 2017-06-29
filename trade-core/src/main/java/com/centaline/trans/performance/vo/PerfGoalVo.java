package com.centaline.trans.performance.vo;

import java.util.Date;

public class PerfGoalVo {
	/**
	 * 业绩目标主键
	 */
	private long[] pkids;
	/**
	 * userOrgJog主键
	 */
	private String[] uojIds;
	/**
	 *归属月份
	 */
	private Date  belongMonth;
	/**
	 * 业绩目标
	 */
	private Double perfGoal;
	/**
	 * 登录人OrgID
	 */
	private String orgId;

	public long[] getPkids() {
		return pkids;
	}

	public void setPkids(long[] pkids) {
		this.pkids = pkids;
	}

	public String[] getUojIds() {
		return uojIds;
	}

	public void setUojIds(String[] uojIds) {
		this.uojIds = uojIds;
	}

	public Double getPerfGoal() {
		return perfGoal;
	}

	public void setPerfGoal(Double perfGoal) {
		this.perfGoal = perfGoal;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Date getBelongMonth() {
		return belongMonth;
	}

	public void setBelongMonth(Date belongMonth) {
		this.belongMonth = belongMonth;
	}
}
