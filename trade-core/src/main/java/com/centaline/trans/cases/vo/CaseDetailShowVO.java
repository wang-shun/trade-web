package com.centaline.trans.cases.vo;

import java.math.BigDecimal;
import java.util.List;

import com.centaline.trans.cases.entity.VCaseTradeInfo;

public class CaseDetailShowVO {

	private String agentId;                       //经纪人
	private String agentName;                   
	private String agentMobile;           
	private String agentOrgId;            
	private String agentOrgName;
	private String mcId;                          //分行经理
	private String mcName;
	private String mcMobile;
	private String asId;                          //助理
	private String asName;
	private String asMobile;
	private String cpId;                          //交易顾问
	private String cpName;
	private String cpMobile;                  
	private String sellerName;                      //上家
	private String sellerMobile;             
	private String buyerName;                      //下家
	private String buyerMobile;
	private List<CaseDetailProcessorVO> proList;     //合作顾问
	private List<CaseDetailProcessorVO> srvList;     //服务项
	private String srvCodes;     //服务项
	

	private String evaName;            //评估公司
	private BigDecimal evaFee;            //评估费金额
	private String bankName;            //分行
	private String parentBankName;            //支行
	private String buyerWork;            //主贷人单位
	private String mortBuyer;            //主贷人
	private String mortTypeName;            //贷款类型
	
    private String signDate;
    private String apprDate;
    private String tazhengArrDate;
    private String lendDate;
    private String prfApplyDate;
    

    private String createTime; //派单
    private String resDate;            //分单
    private String realConTime;           //签约
    private String payTime1;             //付款时间(首付)
    private String payTime2;          //付款时间 二期
    private String payTime3;          // 付款时间 尾款
    private String payTime4;          // 付款时间 装修补偿
    private String payType1;             //付款方式(首付)
    private String payType2;          //付款方式 二期
    private String payType3;          // 付款方式 尾款
    private String payType4;          // 付款方式 装修补偿
    private String loanCloseCode;      //还款时间
    private String realHtTime;         //过户时间
    private String taxTime;         //审税时间
    private String pricingTime;        //核价时间
    private String realPlsTime;   //查限购时间
    private String realPropertyGetTime; //领证时间
    private String closeTime;  //结案时间

    private String closeType;    //还款方式
    private String lendWay;    //放款方式
    private String holdYear;      //购房年数
    private String houseProperty;  //房屋性质
    private String isUniqueHome;   //唯一住房
    private String isHukou;   //户口情况
    private String isConCert;   //合同公证
    
    private String spvType;   //监管方式
    private String spvInsti;  // 监管机构
    private String signTime;   //监管签约时间
    
    private String caseProperty; // 案件属性
    
    private String propertyAddress;//物业地址
    private String loanLostType;//贷款流失类型
    
    private String loanType;//税费卡类型
    

	public String getLoanLostType() {
		return loanLostType;
	}

	public void setLoanLostType(String loanLostType) {
		this.loanLostType = loanLostType;
	}

	public String getHoldYear() {
		return holdYear;
	}

	public String getHouseProperty() {
		return houseProperty;
	}

	public String getIsUniqueHome() {
		return isUniqueHome;
	}

	public void setTazhengArrDate(String tazhengArrDate) {
		this.tazhengArrDate = tazhengArrDate;
	}

	public void setHoldYear(String holdYear) {
		this.holdYear = holdYear;
	}

	public void setHouseProperty(String houseProperty) {
		this.houseProperty = houseProperty;
	}

	public void setIsUniqueHome(String isUniqueHome) {
		this.isUniqueHome = isUniqueHome;
	}

	public void setTradeDate(VCaseTradeInfo info){
    	
    	
    }
    
	public String getAgentId() {
		return agentId;
	}
	public String getAgentName() {
		return agentName;
	}
	public String getAgentMobile() {
		return agentMobile;
	}
	public String getAgentOrgId() {
		return agentOrgId;
	}
	public String getAgentOrgName() {
		return agentOrgName;
	}
	public String getMcId() {
		return mcId;
	}
	public String getMcName() {
		return mcName;
	}
	public String getMcMobile() {
		return mcMobile;
	}
	public String getAsId() {
		return asId;
	}
	public String getAsName() {
		return asName;
	}
	public String getAsMobile() {
		return asMobile;
	}
	
	public String getCpId() {
		return cpId;
	}
	public String getCpName() {
		return cpName;
	}
	public String getCpMobile() {
		return cpMobile;
	}
	public String getSellerName() {
		return sellerName;
	}
	public String getSellerMobile() {
		return sellerMobile;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public String getBuyerMobile() {
		return buyerMobile;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public void setAgentMobile(String agentMobile) {
		this.agentMobile = agentMobile;
	}
	public void setAgentOrgId(String agentOrgId) {
		this.agentOrgId = agentOrgId;
	}
	public void setAgentOrgName(String agentOrgName) {
		this.agentOrgName = agentOrgName;
	}
	public void setMcId(String mcId) {
		this.mcId = mcId;
	}
	public void setMcName(String mcName) {
		this.mcName = mcName;
	}
	public void setMcMobile(String mcMobile) {
		this.mcMobile = mcMobile;
	}
	public void setAsId(String asId) {
		this.asId = asId;
	}
	public void setAsName(String asName) {
		this.asName = asName;
	}
	public void setAsMobile(String asMobile) {
		this.asMobile = asMobile;
	}
	
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	public void setCpMobile(String cpMobile) {
		this.cpMobile = cpMobile;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public void setSellerMobile(String sellerMobile) {
		this.sellerMobile = sellerMobile;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}
	public List<CaseDetailProcessorVO> getProList() {
		return proList;
	}
	public void setProList(List<CaseDetailProcessorVO> proList) {
		this.proList = proList;
	}
	public String getEvaName() {
		return evaName;
	}
	public String getBankName() {
		return bankName;
	}
	public String getParentBankName() {
		return parentBankName;
	}
	public String getBuyerWork() {
		return buyerWork;
	}
	public void setEvaName(String evaName) {
		this.evaName = evaName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public void setParentBankName(String parentBankName) {
		this.parentBankName = parentBankName;
	}
	public void setBuyerWork(String buyerWork) {
		this.buyerWork = buyerWork;
	}
	public String getMortTypeName() {
		return mortTypeName;
	}
	public void setMortTypeName(String mortTypeName) {
		this.mortTypeName = mortTypeName;
	}
	public String getSignDate() {
		return signDate;
	}
	public String getApprDate() {
		return apprDate;
	}
	public String getTazhengArrDate() {
		return tazhengArrDate;
	}
	public String getLendDate() {
		return lendDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	public void setApprDate(String apprDate) {
		this.apprDate = apprDate;
	}
	public void setTazhengArrString(String tazhengArrDate) {
		this.tazhengArrDate = tazhengArrDate;
	}
	public void setLendDate(String lendDate) {
		this.lendDate = lendDate;
	}
	public String getPrfApplyDate() {
		return prfApplyDate;
	}
	public void setPrfApplyDate(String prfApplyDate) {
		this.prfApplyDate = prfApplyDate;
	}
	public String getCreateTime() {
		return createTime;
	}
	public String getResDate() {
		return resDate;
	}
	public String getRealConTime() {
		return realConTime;
	}
	public String getPayTime1() {
		return payTime1;
	}
	public String getPayTime2() {
		return payTime2;
	}
	public String getPayTime3() {
		return payTime3;
	}
	public String getPayTime4() {
		return payTime4;
	}
	public String getLoanCloseCode() {
		return loanCloseCode;
	}
	public String getRealHtTime() {
		return realHtTime;
	}
	public String getTaxTime() {
		return taxTime;
	}
	public String getPricingTime() {
		return pricingTime;
	}
	public String getRealPlsTime() {
		return realPlsTime;
	}
	public String getRealPropertyGetTime() {
		return realPropertyGetTime;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public void setResDate(String resDate) {
		this.resDate = resDate;
	}
	public void setRealConTime(String realConTime) {
		this.realConTime = realConTime;
	}
	public void setPayTime1(String payTime1) {
		this.payTime1 = payTime1;
	}
	public void setPayTime2(String payTime2) {
		this.payTime2 = payTime2;
	}
	public void setPayTime3(String payTime3) {
		this.payTime3 = payTime3;
	}
	public void setPayTime4(String payTime4) {
		this.payTime4 = payTime4;
	}
	public void setLoanCloseCode(String loanCloseCode) {
		this.loanCloseCode = loanCloseCode;
	}
	public void setRealHtTime(String realHtTime) {
		this.realHtTime = realHtTime;
	}
	public void setTaxTime(String taxTime) {
		this.taxTime = taxTime;
	}
	public void setPricingTime(String pricingTime) {
		this.pricingTime = pricingTime;
	}
	public void setRealPlsTime(String realPlsTime) {
		this.realPlsTime = realPlsTime;
	}
	public void setRealPropertyGetTime(String realPropertyGetTime) {
		this.realPropertyGetTime = realPropertyGetTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public String getCloseType() {
		return closeType;
	}

	public void setCloseType(String closeType) {
		this.closeType = closeType;
	}

	public String getIsHukou() {
		return isHukou;
	}

	public String getIsConCert() {
		return isConCert;
	}

	public void setIsHukou(String isHukou) {
		this.isHukou = isHukou;
	}

	public void setIsConCert(String isConCert) {
		this.isConCert = isConCert;
	}

	public String getPayType1() {
		return payType1;
	}

	public String getPayType2() {
		return payType2;
	}

	public String getPayType3() {
		return payType3;
	}

	public String getPayType4() {
		return payType4;
	}

	public void setPayType1(String payType1) {
		this.payType1 = payType1;
	}

	public void setPayType2(String payType2) {
		this.payType2 = payType2;
	}

	public void setPayType3(String payType3) {
		this.payType3 = payType3;
	}

	public void setPayType4(String payType4) {
		this.payType4 = payType4;
	}

	public String getSpvType() {
		return spvType;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSpvType(String spvType) {
		this.spvType = spvType;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public List<CaseDetailProcessorVO> getSrvList() {
		return srvList;
	}

	public void setSrvList(List<CaseDetailProcessorVO> srvList) {
		this.srvList = srvList;
	}

	public String getSrvCodes() {
		return srvCodes;
	}

	public void setSrvCodes(String srvCodes) {
		this.srvCodes = srvCodes;
	}

	public String getMortBuyer() {
		return mortBuyer;
	}

	public void setMortBuyer(String mortBuyer) {
		this.mortBuyer = mortBuyer;
	}

	public BigDecimal getEvaFee() {
		return evaFee;
	}

	public void setEvaFee(BigDecimal evaFee) {
		this.evaFee = evaFee;
	}

	public String getLendWay() {
		return lendWay;
	}

	public void setLendWay(String lendWay) {
		this.lendWay = lendWay;
	}

	public String getSpvInsti() {
		return spvInsti;
	}

	public void setSpvInsti(String spvInsti) {
		this.spvInsti = spvInsti;
	}

	public String getCaseProperty() {
		return caseProperty;
	}

	public void setCaseProperty(String caseProperty) {
		this.caseProperty = caseProperty;
	}

	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	
}
