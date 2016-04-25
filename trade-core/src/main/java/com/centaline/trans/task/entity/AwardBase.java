package com.centaline.trans.task.entity;

import java.util.Date;

import com.centaline.trans.award.entity.AwardBaseEntity;

public class AwardBase extends AwardBaseEntity {
	private String jobCode;
	private String orgCode;
	
	public AwardBase() {
	}

	public AwardBase(String participant, String jobCode, String orgId, String orgCode) {
		super.setParticipant(participant);
		super.setOrgId(orgId);
		this.setJobCode(jobCode);
		this.setOrgCode(orgCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj instanceof AwardBase) {
			AwardBase other = (AwardBase) obj;
			if (other.getParticipant() == null) {
				return this.getParticipant() == null;
			}
			return other.getParticipant().equals(this.getParticipant());
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		if (this.getParticipant() == null) {
			return -1;
		}
		return this.getParticipant().hashCode();
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
}
