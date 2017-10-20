package com.centaline.trans.ransom.vo;

public class VRansomChangeUserVo {

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
	
	private String type;
	
	private String orgName;
	
	private String jobName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getUserCaseCount() {
		return userCaseCount;
	}

	public void setUserCaseCount(Integer userCaseCount) {
		this.userCaseCount = userCaseCount;
	}

	public Integer getUserCaseMonthCount() {
		return userCaseMonthCount;
	}

	public void setUserCaseMonthCount(Integer userCaseMonthCount) {
		this.userCaseMonthCount = userCaseMonthCount;
	}

	public Integer getUserCaseUnTransCount() {
		return userCaseUnTransCount;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
}
