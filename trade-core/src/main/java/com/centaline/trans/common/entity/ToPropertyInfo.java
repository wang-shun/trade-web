package com.centaline.trans.common.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ToPropertyInfo {
	private Long pkid;

	private String propertyCode;

	private String propertyAddr;

	private String ctmAddr;

	private String caseCode;

	private Integer totalFloor;

	private String propertyType;

	private Integer locateFloor;

	private Double square;

	private String distCode;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", locale = "zh", timezone = "GMT+8")
	private Date finishYear;

	private String ctmCode;

	private String comment;

	private String propertyAgentId;

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getPropertyCode() {
		return propertyCode;
	}

	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	}

	public String getCtmAddr() {
		return ctmAddr;
	}

	public void setCtmAddr(String ctmAddr) {
		this.ctmAddr = ctmAddr;
	}

	public String getPropertyAddr() {
		return propertyAddr;
	}

	public void setPropertyAddr(String propertyAddr) {
		this.propertyAddr = propertyAddr == null ? null : propertyAddr.trim();
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode == null ? null : caseCode.trim();
	}

	public Integer getTotalFloor() {
		return totalFloor;
	}

	public void setTotalFloor(Integer totalFloor) {
		this.totalFloor = totalFloor;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType == null ? null : propertyType.trim();
	}

	public Integer getLocateFloor() {
		return locateFloor;
	}

	public void setLocateFloor(Integer locateFloor) {
		this.locateFloor = locateFloor;
	}

	public Double getSquare() {
		return square;
	}

	public void setSquare(Double square) {
		this.square = square;
	}

	public Date getFinishYear() {
		return finishYear;
	}

	public void setFinishYear(Date finishYear) {
		this.finishYear = finishYear;
	}

	public String getCtmCode() {
		return ctmCode;
	}

	public void setCtmCode(String ctmCode) {
		this.ctmCode = ctmCode == null ? null : ctmCode.trim();
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment == null ? null : comment.trim();
	}

	public String getDistCode() {
		return distCode;
	}

	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}

	public String getPropertyAgentId() {
		return propertyAgentId;
	}

	public void setPropertyAgentId(String propertyAgentId) {
		this.propertyAgentId = propertyAgentId;
	}

}