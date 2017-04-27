package com.centaline.trans.common.enums;

public enum SurveyStatusEnum {
	
	DEFAULT("0", "待分单"), SIGN_SURVEY_ING("1", "签约回访中"), SIGN_SURVEY_PASS("2", "签约回访通过"), SIGN_SURVEY_REJECT("3", "签约回访打回"), 
	TRANSFER_SURVEY_ING("4", "过户回访中"), TRANSFER_SURVEY_REJECT("5", "过户回访打回"), TRANSFER_SURVEY_PASS("6", "过户回访完成");
	
	private String name;
	private String code;

	private SurveyStatusEnum(String code, String name) {
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
