package com.centaline.trans.cases.vo;

import java.math.BigDecimal;

public class ViHouseDelBaseVo {

	private String DISTRICT_CODE; 
	private String BUILD_END_YEAR;
	private BigDecimal BUILD_SIZE; 
	private Integer FLOOR;
	private Integer TOTAL_FLOOR;  
	private Long HOUSE_ID;
	private String HOUDEL_CODE;
	
	public String getDISTRICT_CODE() {
		return DISTRICT_CODE;
	}
	public void setDISTRICT_CODE(String dISTRICT_CODE) {
		DISTRICT_CODE = dISTRICT_CODE;
	}
	public String getBUILD_END_YEAR() {
		return BUILD_END_YEAR;
	}
	public void setBUILD_END_YEAR(String bUILD_END_YEAR) {
		BUILD_END_YEAR = bUILD_END_YEAR;
	}
	public BigDecimal getBUILD_SIZE() {
		return BUILD_SIZE;
	}
	public void setBUILD_SIZE(BigDecimal bUILD_SIZE) {
		BUILD_SIZE = bUILD_SIZE;
	}
	public Integer getFLOOR() {
		return FLOOR;
	}
	public void setFLOOR(Integer fLOOR) {
		FLOOR = fLOOR;
	}
	public Integer getTOTAL_FLOOR() {
		return TOTAL_FLOOR;
	}
	public void setTOTAL_FLOOR(Integer tOTAL_FLOOR) {
		TOTAL_FLOOR = tOTAL_FLOOR;
	}
	public Long getHOUSE_ID() {
		return HOUSE_ID;
	}
	public void setHOUSE_ID(Long hOUSE_ID) {
		HOUSE_ID = hOUSE_ID;
	}
	public String getHOUDEL_CODE() {
		return HOUDEL_CODE;
	}
	public void setHOUDEL_CODE(String hOUDEL_CODE) {
		HOUDEL_CODE = hOUDEL_CODE;
	}
	
	
}
