package com.centaline.trans.common.enums;

public enum SatisfactionStatusEnum {
	
	DEFAULT("1", "待分单"), SIGN_SURVEY_ING("2", "签约回访"), SIGN_SURVEY_REJECT("3", "签约打回"), 
	GUOHU_SURVEY_ING("4", "过户回访"), GUOHU_SURVEY_REJECT("5", "过户打回"), GUOHU_SURVEY_PASS("6", "已回访");
	
	private String name;
	private String code;

	private SatisfactionStatusEnum(String code, String name) {
		this.setName(name);
		this.setCode(code);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
