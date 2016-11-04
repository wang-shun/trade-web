package com.centaline.trans.cases.vo;


public class VCaseDistributeUserVO {
	/** 用户 UUID */
	private String id;
	
	/** 真实姓名 **/
	private String realName;
	
	/** 手机号 **/
	private String mobile;
	
	/**当前单数**/
	private Integer userCaseCount;
	/**本月单数**/
	private Integer userCaseMonthCount;
	/**未过户单数**/
	private Integer userCaseUnTransCount;
	/**头像url**/
	private String imgUrl;
	
	public String getId() {
		return id;
	}
	
	public String getRealName() {
		return realName;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public Integer getUserCaseCount() {
		return userCaseCount;
	}
	public Integer getUserCaseMonthCount() {
		return userCaseMonthCount;
	}
	public Integer getUserCaseUnTransCount() {
		return userCaseUnTransCount;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public void setUserCaseCount(Integer userCaseCount) {
		this.userCaseCount = userCaseCount;
	}
	public void setUserCaseMonthCount(Integer userCaseMonthCount) {
		this.userCaseMonthCount = userCaseMonthCount;
	}
	public void setUserCaseUnTransCount(Integer userCaseUnTransCount) {
		this.userCaseUnTransCount = userCaseUnTransCount;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
}
