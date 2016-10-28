package com.centaline.trans.remote.enums;

import org.apache.commons.lang3.StringUtils;

public enum SC {

	BUSYING("-1","系统繁忙，此时请开发者稍候再试"),
	SUCCESS("0","请求成功"),
	KEY_NOT_EXIST("30001","vc-user-key不存在"),
	USER_NOT_EXIST("30002","un（e估用户）不存在"),
	TOKEN_ERR("30003","token校验失败"),
	FILE_TYPE_ERR("30004","上传文件类型不正确"),
	FILE_SIZE_ERR("30005","上传文件大小不正确"),
	MOBILE_ERR("30006","手机号验证不通过"),
	PARAM_REQUIRE("30007","请求参数必填项为空"),
	PARAM_ERR("30008","请求参数值为非合法数据，如约定字典数据等"),
	MULTI_ADDRESS("30009","询价时不能匹配到单个地址（匹配到多个地址）"),
	CASE_ID_ERR("30010","中原caseId与系统中存储的不一致"),
	DUPLICATE_OBJECTION("30011","价格异议重复申请"),
	PRICING_SUB_FAIL("30012","未确认价格，或确认未成功"),
	HOUSE_CARD_NOT_UPLOAD("30013","房产证照片未上传"),
	ID_CARD_NOT_UPLOAD("30014","身份证件照片未上传"),
	AREA_NOT_SAME("30015","询价时如果是别墅，建筑面积不等于地上面积与地下面积之和"),
	ILLEGAL_APPLY("30016","非法报告申请");

	private String code;
	private String value;
	
	SC(String code,String value){
		this.code = code;
		this.value = value;
	}
	public String getCode(){
		return this.code;
	}
	public String getValue(){
		return this.value;
	}
	
	public static String getValueByCode(String code){
		if(StringUtils.isBlank(code)){
			return "";
		}
		SC[] enums = SC.values();
		for(SC e : enums){
			if(e.getCode().equals(code)){
				return e.getValue();
			}
		}
		return "";
	}
}
