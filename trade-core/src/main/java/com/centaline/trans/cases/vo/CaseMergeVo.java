package com.centaline.trans.cases.vo;

import java.math.BigDecimal;
import java.util.List;

public class CaseMergeVo {
	
	private String propertyCode;
	private String distCode;
	private String propertyAddr;		
	private String propertyType;
	private Double square;
	private Integer floor;
	private Integer totalFloor;
	private String finishYear;
	
	
	private String agentName;	
	private String agentCode;
	private String agentOrgName;
	private String agentOrgId;
	private String agentOrgCode;	
	private String agentPhone;
	
	/**上家*/
    private List<Long> pkidUp;
	private List<String> guestNameUp;
    private List<String> guestPhoneUp;
    /**下家*/
    private List<Long> pkidDown;
    private List<String> guestNameDown;
    private List<String> guestPhoneDown;
    /**外单案件推荐人*/
    private List<Long> pkidRecommend;
    private List<String> guestNameRecommend;
    private List<String> guestPhoneRecommend;
   
	/*上下家删除的记录id*/
    private List<Long> guestPkid;
    /**
     * 外单案件的合作来源
     */
    private String sourceOfCooperation;
    /**
     * 外单案件的应收费用项
     */
	private String commSubject;
	/**
     * 外单案件的应收费金额
     */
    private BigDecimal commCost;
   
	public String getCommSubject() {
		return commSubject;
	}
	public void setCommSubject(String commSubject) {
		this.commSubject = commSubject;
	}
	public BigDecimal getCommCost() {
		return commCost;
	}
	public void setCommCost(BigDecimal commCost) {
		this.commCost = commCost;
	}
	public String getSourceOfCooperation() {
		return sourceOfCooperation;
	}
	public void setSourceOfCooperation(String sourceOfCooperation) {
		this.sourceOfCooperation = sourceOfCooperation;
	}
	public String getPropertyCode() {
		return propertyCode;
	}
	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	}
	public String getPropertyAddr() {
		return propertyAddr;
	}
	public void setPropertyAddr(String propertyAddr) {
		this.propertyAddr = propertyAddr;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getAgentOrgName() {
		return agentOrgName;
	}
	public void setAgentOrgName(String agentOrgName) {
		this.agentOrgName = agentOrgName;
	}
	public String getAgentOrgId() {
		return agentOrgId;
	}
	public void setAgentOrgId(String agentOrgId) {
		this.agentOrgId = agentOrgId;
	}
	public String getAgentPhone() {
		return agentPhone;
	}
	public void setAgentPhone(String agentPhone) {
		this.agentPhone = agentPhone;
	}
	public List<Long> getPkidUp() {
		return pkidUp;
	}
	public void setPkidUp(List<Long> pkidUp) {
		this.pkidUp = pkidUp;
	}
	public List<String> getGuestNameUp() {
		return guestNameUp;
	}
	public void setGuestNameUp(List<String> guestNameUp) {
		this.guestNameUp = guestNameUp;
	}
	public List<String> getGuestPhoneUp() {
		return guestPhoneUp;
	}
	public void setGuestPhoneUp(List<String> guestPhoneUp) {
		this.guestPhoneUp = guestPhoneUp;
	}
	public List<Long> getPkidDown() {
		return pkidDown;
	}
	public void setPkidDown(List<Long> pkidDown) {
		this.pkidDown = pkidDown;
	}
	public List<String> getGuestNameDown() {
		return guestNameDown;
	}
	public void setGuestNameDown(List<String> guestNameDown) {
		this.guestNameDown = guestNameDown;
	}
	public List<String> getGuestPhoneDown() {
		return guestPhoneDown;
	}
	public void setGuestPhoneDown(List<String> guestPhoneDown) {
		this.guestPhoneDown = guestPhoneDown;
	}
	public List<Long> getGuestPkid() {
		return guestPkid;
	}
	public void setGuestPkid(List<Long> guestPkid) {
		this.guestPkid = guestPkid;
	}
	public String getAgentOrgCode() {
		return agentOrgCode;
	}
	public void setAgentOrgCode(String agentOrgCode) {
		this.agentOrgCode = agentOrgCode;
	}
	public String getDistCode() {
		return distCode;
	}
	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	
	public Double getSquare() {
		return square;
	}
	public void setSquare(Double square) {
		this.square = square;
	}
	public Integer getFloor() {
		return floor;
	}
	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	public Integer getTotalFloor() {
		return totalFloor;
	}
	public void setTotalFloor(Integer totalFloor) {
		this.totalFloor = totalFloor;
	}
	public String getFinishYear() {
		return finishYear;
	}
	public void setFinishYear(String finishYear) {
		this.finishYear = finishYear;
	}
	public List<Long> getPkidRecommend() {
		return pkidRecommend;
	}
	public void setPkidRecommend(List<Long> pkidRecommend) {
		this.pkidRecommend = pkidRecommend;
	}
	public List<String> getGuestNameRecommend() {
		return guestNameRecommend;
	}
	public void setGuestNameRecommend(List<String> guestNameRecommend) {
		this.guestNameRecommend = guestNameRecommend;
	}
	public List<String> getGuestPhoneRecommend() {
		return guestPhoneRecommend;
	}
	public void setGuestPhoneRecommend(List<String> guestPhoneRecommend) {
		this.guestPhoneRecommend = guestPhoneRecommend;
	}
		
}
