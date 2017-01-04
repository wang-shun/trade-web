package com.centaline.trans.comment.enums;

public enum CommentSrvType {
	REQSTUFF("reqStuff", "补件");
	private String code;
	private String value;

	CommentSrvType(String code, String value) {
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
