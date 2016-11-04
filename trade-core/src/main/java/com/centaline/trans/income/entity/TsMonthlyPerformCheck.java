package com.centaline.trans.income.entity;

import java.util.Date;

public class TsMonthlyPerformCheck {
    private Long pkid;

    private String participantId;
    private String orgId;

	private String jobId;

    private Integer loanLostNo;

    private Integer eplusCaseNoAll;

    private Date belongMonth;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId == null ? null : participantId.trim();
    }

    public String getOrgId() {
		return orgId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
    public Integer getLoanLostNo() {
        return loanLostNo;
    }

    public void setLoanLostNo(Integer loanLostNo) {
        this.loanLostNo = loanLostNo;
    }

    public Integer getEplusCaseNoAll() {
        return eplusCaseNoAll;
    }

    public void setEplusCaseNoAll(Integer eplusCaseNoAll) {
        this.eplusCaseNoAll = eplusCaseNoAll;
    }

    public Date getBelongMonth() {
        return belongMonth;
    }

    public void setBelongMonth(Date belongMonth) {
        this.belongMonth = belongMonth;
    }
}