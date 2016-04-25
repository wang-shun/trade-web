package com.centaline.trans.task.entity;


import com.centaline.trans.award.entity.AwardBaseEntity;

public class AwardBase extends AwardBaseEntity {
	private String jobCode;
	//private String orgCode;

	public AwardBase() {
	}

	public AwardBase(String participant, String jobCode, String orgId) {
		super.setParticipant(participant);
		super.setOrgId(orgId);
		this.setJobCode(jobCode);
		//this.setOrgCode(orgCode);
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

	public boolean isSameOrgAndParticipant(Object obj) {
		if (obj instanceof AwardBase) {
			AwardBase other = (AwardBase) obj;
			if (other.getParticipant() == null || other.getOrgId() == null) {
				if (other.getParticipant() == this.getParticipant() && other.getOrgId() == this.getOrgId()) {
					return true;
				} else if (other.getParticipant() == null) {
					return this.getParticipant() == null && (other.getOrgId().equals(this.getOrgId()));
				} else {
					return this.getOrgId() == null && (other.getParticipant().equals(this.getParticipant()));
				}
			}
			return other.getParticipant().equals(this.getParticipant()) && other.getOrgId().equals(this.getOrgId());
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
}

