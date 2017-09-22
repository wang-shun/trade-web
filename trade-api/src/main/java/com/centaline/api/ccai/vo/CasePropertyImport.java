package com.centaline.api.ccai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * CCAI导入案件信息房源信息
 *
 * @author yinchao
 */
@ApiModel("案件物业信息")
public class CasePropertyImport {
	@ApiModelProperty(value = "房源ID", required = true, position = 0)
	private String id;
	@ApiModelProperty(value = "房源编号", required = true, position = 1)
	private String code;
	@ApiModelProperty(value = "所属行政区域", required = true, example = "120105",
			allowableValues = "range[120100, 120119],range[110100,110119]", position = 2)
	private String district;
	@ApiModelProperty(value = "房源地址描述", required = true, position = 3)
	private String address;
	@ApiModelProperty(value = "物业总层高", position = 4)
	private Integer totalFloor;
	@ApiModelProperty(value = "物业所在层数", position = 5)
	private Integer locateFloor;
	@ApiModelProperty(value = "物业面积", example = "123.45", position = 6)
	private Double square;
	@ApiModelProperty(value = "房屋类型", example = "普通住宅（私产）",
			allowableValues = "别墅,公寓,写字楼,普通住宅（私产）,普通住宅（公产）,厂房,洋房,商住,商铺,其他",
			position = 7)
	private String propertyType;
	@ApiModelProperty(value = "物业竣工时间", example = "2012", position = 8)
	private String finishYear;
	@ApiModelProperty(value = "物业描述", position = 9)
	private String comment;

	@NotBlank(message = "房源ID不能为空")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@NotBlank(message = "房源编码不能为空")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@NotBlank(message = "房源地址不能为空")
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

	@NotBlank(message = "所属区域不能为空")
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Override
	public String toString() {
		return "CasePropertyImport [id=" + id + ", code=" + code + ", district=" + district + ", address=" + address
				+ ", totalFloor=" + totalFloor + ", locateFloor=" + locateFloor + ", square=" + square
				+ ", propertyType=" + propertyType + ", finishYear=" + finishYear + ", comment=" + comment + "]";
	}
}
