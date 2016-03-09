package com.centaline.trans.common.enums;

public enum AppTypeEnum {

	APP_IMAGE("shcl-image-web", "图片服务"), APP_UAM("aist-uam-web", "统一应用管理平台"), APP_DEMO(
			"aist-appdemo-web", "示例应用"), APP_SHCL("shcl-h5-web", "三级市场外网访问"), APP_CTPOST(
			"ctpost-web", "放盘站"), APP_FILESVR("aist-filesvr-web", "上传下载"), APP_LOG(
			"aist-log-web", "平台日志管理"), APP_TRADE("trade-web", "交易管理"), APP_PORTAL(
			"aist-portal-web", "旅游平台门户"), APP_SSO("aist-sso-web", "SSO服务"), APP_MSG(
			"aist-message-web", "消息中心"), APP_WORKFLOW("aist-workflow-web",
			"工作流管理"), APP_ACHEDULER("aist-scheduler-web", "任务调度"), APP_SALES(
			"sales-web", "三级市场销售平台"), APP_ACTIVITY("aist-activiti-web", "工作流管理");

	private String code;

	private String name;

	private AppTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * 根据code获取Name
	 * 
	 * @param code
	 * @return
	 */
	public static String getName(String code) {
		for (AppTypeEnum sde : AppTypeEnum.values()) {
			if (code.equalsIgnoreCase(sde.getCode())) {
				return sde.getName();
			}
		}
		return null;
	}

	/**
	 * 根据code获取Name
	 * 
	 * @param code
	 * @return
	 */
	/*
	 * public static String getName(String code) { for (CustdelTradeTypeEnum sde
	 * : CustdelTradeTypeEnum.values()) { if
	 * (code.equalsIgnoreCase(sde.getCode())){ return sde.getName(); } } return
	 * null; }
	 */

	/**
	 * Getter method for property <tt>name</tt>.
	 * 
	 * @return property value of name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method for property <tt>name</tt>.
	 * 
	 * @param name
	 *            value to be assigned to property name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method for property <tt>code</tt>.
	 * 
	 * @return property value of code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Setter method for property <tt>code</tt>.
	 * 
	 * @param code
	 *            value to be assigned to property code
	 */
	public void setCode(String code) {
		this.code = code;
	}

}
