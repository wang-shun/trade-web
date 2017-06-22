package com.centaline.trans.common.enums;
/**
 * 应收科目
 * @author jjm
 *
 */
public enum CommSubjectEnum {
	/**服务费**/
	SERVICE_FEE("SERVICE_FEE", "服务费"),
	/**评估费**/
	EVAL_FEE("EVAL_FEE","评估费"),
	/**代收评估费**/
	EVAL_FEE_FOR_AGENT("EVAL_FEE_FOR_AGENT","代收评估费");
	
	private String name;
	private String code;

	private CommSubjectEnum(String code, String name) {
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
