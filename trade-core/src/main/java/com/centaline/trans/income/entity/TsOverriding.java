package com.centaline.trans.income.entity;

public class TsOverriding {
    private Long pkid;

    private String caseCode;

    private Long incomeId;

    private String orOwnerName;

    private String orOwnerId;

    private String orPar;

    private String orAmount;

    private Integer importNo;
    
    private String orOwnerJobId;
    
    private String orOwnerOrgId;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public Long getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Long incomeId) {
        this.incomeId = incomeId;
    }

    public String getOrOwnerName() {
        return orOwnerName;
    }

    public void setOrOwnerName(String orOwnerName) {
        this.orOwnerName = orOwnerName == null ? null : orOwnerName.trim();
    }

    public String getOrOwnerId() {
        return orOwnerId;
    }

    public void setOrOwnerId(String orOwnerId) {
        this.orOwnerId = orOwnerId == null ? null : orOwnerId.trim();
    }

    public String getOrPar() {
        return orPar;
    }

    public void setOrPar(String orPar) {
        this.orPar = orPar == null ? null : orPar.trim();
    }

    public String getOrAmount() {
        return orAmount;
    }

    public void setOrAmount(String orAmount) {
        this.orAmount = orAmount == null ? null : orAmount.trim();
    }

    public Integer getImportNo() {
        return importNo;
    }

    public void setImportNo(Integer importNo) {
        this.importNo = importNo;
    }

	public String getOrOwnerJobId() {
		return orOwnerJobId;
	}

	public void setOrOwnerJobId(String orOwnerJobId) {
		this.orOwnerJobId = orOwnerJobId;
	}

	public String getOrOwnerOrgId() {
		return orOwnerOrgId;
	}

	public void setOrOwnerOrgId(String orOwnerOrgId) {
		this.orOwnerOrgId = orOwnerOrgId;
	}
    
    
}