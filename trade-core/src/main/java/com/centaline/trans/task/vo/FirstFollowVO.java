package com.centaline.trans.task.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class FirstFollowVO {

	private String taskId;
	private String processInstanceId;
	private String invalid_reason;
	private String operator,approveType; /*无效审批申请需要数据*/
	
	private String caseCode;
    private String partCode;
	/*交易单*/
    private Long caseId;
	private String caseProperty; /*案件性质（有效or无效）*/
	
	/*签约表*/
	private Long signId;
    private BigDecimal conPrice;		/*合同价*/
    private BigDecimal realPrice;		/*成交价*/
    
    /*交易计划*/
    private Long tTLId;
	private Date realConTime;			/*预计签约时间*/
	
    /*物业信息*/
	private Long propertyInfoId;
    private String propertyAddr;		/*物业地址*/
    private Double square;				/*产证面积*/
    private String distCode;
    
    private String diya;				/*抵押*/
    private String chaxiangou;			/*查限购*/
    
    /*服务项目和经办人*/
    private String srvCode;				/*服务编号*/
    
    private String orgId;
    
    private List<String> cooperationUser;		/*办理人ID*/
    private List<String> coworkService;		/*合作项目*/
    
    private String mortageService;		/*贷款服务项*/
    
    private String zbkServices;	/*自办项目*/
    private String userId;				/*操作人id*/
    private String userOrgId;			/*操作人组别id*/
    
    private Long firstfollowId;
    private String isPerchaseReserachNeed;  /* 查限购情况 */
    private String isLoanClose;  /* 抵押情况 */
    private String comment;  /* 备注 */

    private boolean isrepeat;  // 判断用户是否重复提交表单[true 表示重复提交]
    
    
	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public Long getSignId() {
		return signId;
	}

	public void setSignId(Long signId) {
		this.signId = signId;
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

	public Long getPropertyInfoId() {
		return propertyInfoId;
	}

	public void setPropertyInfoId(Long propertyInfoId) {
		this.propertyInfoId = propertyInfoId;
	}

	public String getPropertyAddr() {
		return propertyAddr;
	}

	public void setPropertyAddr(String propertyAddr) {
		this.propertyAddr = propertyAddr;
	}

	public Double getSquare() {
		return square;
	}

	public void setSquare(Double square) {
		this.square = square;
	}

	public String getDiya() {
		return diya;
	}

	public void setDiya(String diya) {
		this.diya = diya;
	}

	public String getChaxiangou() {
		return chaxiangou;
	}

	public void setChaxiangou(String chaxiangou) {
		this.chaxiangou = chaxiangou;
	}

	public String getSrvCode() {
		return srvCode;
	}

	public void setSrvCode(String srvCode) {
		this.srvCode = srvCode;
	}

	public String getCaseProperty() {
		return caseProperty;
	}

	public void setCaseProperty(String caseProperty) {
		this.caseProperty = caseProperty;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public Long gettTLId() {
		return tTLId;
	}

	public void settTLId(Long tTLId) {
		this.tTLId = tTLId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getInvalid_reason() {
		return invalid_reason;
	}

	public void setInvalid_reason(String invalid_reason) {
		this.invalid_reason = invalid_reason;
	}

	public String getMortageService() {
		return mortageService;
	}

	public void setMortageService(String mortageService) {
		this.mortageService = mortageService;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getApproveType() {
		return approveType;
	}

	public void setApproveType(String approveType) {
		this.approveType = approveType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserOrgId() {
		return userOrgId;
	}

	public void setUserOrgId(String userOrgId) {
		this.userOrgId = userOrgId;
	}

	public List<String> getCooperationUser() {
		return cooperationUser;
	}

	public void setCooperationUser(List<String> cooperationUser) {
		this.cooperationUser = cooperationUser;
	}

	public List<String> getCoworkService() {
		return coworkService;
	}

	public void setCoworkService(List<String> coworkService) {
		this.coworkService = coworkService;
	}

	public String getZbkServices() {
		return zbkServices;
	}

	public void setZbkServices(String zbkServices) {
		this.zbkServices = zbkServices;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getDistCode() {
		return distCode;
	}

	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}

	public String getIsPerchaseReserachNeed() {
		return isPerchaseReserachNeed;
	}

	public void setIsPerchaseReserachNeed(String isPerchaseReserachNeed) {
		this.isPerchaseReserachNeed = isPerchaseReserachNeed;
	}

	public String getIsLoanClose() {
		return isLoanClose;
	}

	public void setIsLoanClose(String isLoanClose) {
		this.isLoanClose = isLoanClose;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getFirstfollowId() {
		return firstfollowId;
	}

	public void setFirstfollowId(Long firstfollowId) {
		this.firstfollowId = firstfollowId;
	}

	public boolean isIsrepeat() {
		return isrepeat;
	}

	public void setIsrepeat(boolean isrepeat) {
		this.isrepeat = isrepeat;
	}
    
	
}
