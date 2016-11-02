package com.centaline.trans.remote.vo;

import java.util.List;

public class BaseResult<T> {

	private String sc;
	
	private String msg;
	
	private T result;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSc() {
		return sc;
	}

	public void setSc(String sc) {
		this.sc = sc;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	
}
