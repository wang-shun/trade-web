package com.centaline.trans.cases.entity;
/**
 * CCAI导入案件信息房源信息
 * @author yinchao
 *
 */
public class CcaiImportCaseProperty {
	/**TODO A+房源ID?*/
	private String id;
	/**A+房源CODE ?*/
	private String code;
	/**房源所属行政区域 国标 精确到区 用于案件任务分配*/
	private String district;
	/**房源地址描述*/
	private String address;
	/**物业总层高*/
	private Integer totalFloor;
	/**物业所在层数*/
	private Integer locateFloor;
	/**物业面积*/
	private Double square;
	/**房屋类型*/
	private String propertyType;
	/**物业竣工时间*/
	private String finishYear;
	/**物业描述*/
	private String comment;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getTotalFloor() {
		return totalFloor;
	}
	public void setTotalFloor(Integer totalFloor) {
		this.totalFloor = totalFloor;
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
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public String getFinishYear() {
		return finishYear;
	}
	public void setFinishYear(String finishYear) {
		this.finishYear = finishYear;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	@Override
	public String toString() {
		return "CcaiImportCaseProperty [id=" + id + ", code=" + code + ", district=" + district + ", address=" + address
				+ ", totalFloor=" + totalFloor + ", locateFloor=" + locateFloor + ", square=" + square
				+ ", propertyType=" + propertyType + ", finishYear=" + finishYear + ", comment=" + comment + "]";
	}
}
