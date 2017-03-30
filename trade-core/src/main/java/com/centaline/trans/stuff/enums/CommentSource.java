package com.centaline.trans.stuff.enums;
/**
 * 
 * @author jjm
 *
 */
public enum CommentSource {
	INTER("INTER", "内部"),
	MORT("MORT", "贷款"), 
	CARD("CARD", "卡证"),   
	XD("XD", "消费贷"), 
	ZJJG("ZJJG", "资金监管");
	
	private String code;
	private String value;

	CommentSource(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return this.code;
	}

	public String getValue() {
		return this.value;
	}
}
