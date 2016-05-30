package com.centaline.trans.cases.vo;



public class ToLoanAgentVO {
    

    private String loanSrvName;

    private String finOrgName;

    

    private String applyStatusName;

    private String confirmStatusName;

    private String lastExceedExportTime;

    private String applyTime;
    private String confirmTime;

    private String signTime;

    private String releaseTime;

    private String incomeConfirmTime;

    private String incomeArriveTime;


    public String getLoanSrvName() {
		return loanSrvName;
	}

	public String getFinOrgName() {
		return finOrgName;
	}

	public String getApplyStatusName() {
		return applyStatusName;
	}

	public String getConfirmStatusName() {
		return confirmStatusName;
	}

	public void setLoanSrvName(String loanSrvName) {
		this.loanSrvName = loanSrvName;
	}

	public void setFinOrgName(String finOrgName) {
		this.finOrgName = finOrgName;
	}

	public void setApplyStatusName(String applyStatusName) {
		this.applyStatusName = applyStatusName;
	}

	public void setConfirmStatusName(String confirmStatusName) {
		this.confirmStatusName = confirmStatusName;
	}

	public String getLastExceedExportTime() {
        return lastExceedExportTime;
    }

    public void setLastExceedExportTime(String lastExceedExportTime) {
        this.lastExceedExportTime = lastExceedExportTime;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getIncomeConfirmTime() {
        return incomeConfirmTime;
    }

    public void setIncomeConfirmTime(String incomeConfirmTime) {
        this.incomeConfirmTime = incomeConfirmTime;
    }

    public String getIncomeArriveTime() {
        return incomeArriveTime;
    }

    public void setIncomeArriveTime(String incomeArriveTime) {
        this.incomeArriveTime = incomeArriveTime;
    }

	public String getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}
}