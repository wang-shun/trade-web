package com.centaline.trans.bankRebate.entity;

import java.math.BigDecimal;
/**
 * 
 * @author wbwangxj
 *
 */
public class ToBankRebateInfo {
    private Long pkid;

    private String caseCode;

    private String ccaiCode;

    private String guaranteeCompId;

    private String bankName;

    private BigDecimal rebateMoney;

    private BigDecimal rebateWarrant;

    private BigDecimal rebateBusiness;

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

    public String getCcaiCode() {
        return ccaiCode;
    }

    public void setCcaiCode(String ccaiCode) {
        this.ccaiCode = ccaiCode == null ? null : ccaiCode.trim();
    }

   

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public BigDecimal getRebateMoney() {
        return rebateMoney;
    }

    public void setRebateMoney(BigDecimal rebateMoney) {
        this.rebateMoney = rebateMoney;
    }

    public BigDecimal getRebateWarrant() {
        return rebateWarrant;
    }

    public void setRebateWarrant(BigDecimal rebateWarrant) {
        this.rebateWarrant = rebateWarrant;
    }

    public BigDecimal getRebateBusiness() {
        return rebateBusiness;
    }

    public void setRebateBusiness(BigDecimal rebateBusiness) {
        this.rebateBusiness = rebateBusiness;
    }

	public String getGuaranteeCompId() {
		return guaranteeCompId;
	}

	public void setGuaranteeCompId(String guaranteeCompId) {
		this.guaranteeCompId = guaranteeCompId;
	}
}