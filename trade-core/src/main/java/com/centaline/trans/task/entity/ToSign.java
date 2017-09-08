package com.centaline.trans.task.entity;

import java.math.BigDecimal;
import java.util.Date;
/**签约*/
public class ToSign {
    private Long pkid;

    private String partCode;

    private String caseCode;

    private Date realConTime;

    private BigDecimal conPrice;

    private BigDecimal realPrice;

    private String isHukou;

    private String isConCert;

    private String comment;

    private BigDecimal personalIncomeTax;//个人所得税
    
    /**天津废弃
    private BigDecimal houseHodingTax;

    private BigDecimal businessTax;

    private BigDecimal contractTax;
	*/
    private BigDecimal landIncrementTax;//土地增值税
    /*天津新增*/
	private BigDecimal sellerTax;	 /*卖方增值税*/
	private BigDecimal buyerTax;	 /*买方契税*/
	private Date estimateTransferTime;//预计过户时间
	private String fundSupervisionme;//资金监管
	private String netPlace;//网签地点

    private String houseQuantity;		/*是否首套 0:首套 1：二套 2：多套*/

  
    public BigDecimal getPersonalIncomeTax() {
        return personalIncomeTax;
    }

    public void setPersonalIncomeTax(BigDecimal personalIncomeTax) {
        this.personalIncomeTax = personalIncomeTax;
    }


    public BigDecimal getLandIncrementTax() {
        return landIncrementTax;
    }

    public void setLandIncrementTax(BigDecimal landIncrementTax) {
        this.landIncrementTax = landIncrementTax;
    }

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode == null ? null : partCode.trim();
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public Date getRealConTime() {
        return realConTime;
    }

    public void setRealConTime(Date realConTime) {
        this.realConTime = realConTime;
    }

    public BigDecimal getConPrice() {
        return conPrice;
    }

    public void setConPrice(BigDecimal conPrice) {
        this.conPrice = conPrice;
    }

    public BigDecimal getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(BigDecimal realPrice) {
        this.realPrice = realPrice;
    }

    public String getIsHukou() {
        return isHukou;
    }

    public void setIsHukou(String isHukou) {
        this.isHukou = isHukou == null ? null : isHukou.trim();
    }

    public String getIsConCert() {
        return isConCert;
    }

    public void setIsConCert(String isConCert) {
        this.isConCert = isConCert == null ? null : isConCert.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getHouseQuantity() {
        return houseQuantity;
    }

    public void setHouseQuantity(String houseQuantity) {
        this.houseQuantity = houseQuantity;
    }

	public BigDecimal getSellerTax() {
		return sellerTax;
	}

	public void setSellerTax(BigDecimal sellerTax) {
		this.sellerTax = sellerTax;
	}

	public BigDecimal getBuyerTax() {
		return buyerTax;
	}

	public void setBuyerTax(BigDecimal buyerTax) {
		this.buyerTax = buyerTax;
	}

	public Date getEstimateTransferTime() {
		return estimateTransferTime;
	}

	public void setEstimateTransferTime(Date estimateTransferTime) {
		this.estimateTransferTime = estimateTransferTime;
	}

	public String getFundSupervisionme() {
		return fundSupervisionme;
	}

	public void setFundSupervisionme(String fundSupervisionme) {
		this.fundSupervisionme = fundSupervisionme;
	}

	public String getNetPlace() {
		return netPlace;
	}

	public void setNetPlace(String netPlace) {
		this.netPlace = netPlace;
	}


}