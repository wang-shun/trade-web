package com.centaline.trans.remote.vo;

import java.util.List;

public class BankResultVo {
	
	private String sc;
	
	private List<BankInfo> result;

	public String getSc() {
		return sc;
	}

	public void setSc(String sc) {
		this.sc = sc;
	}

	public List<BankInfo> getResult() {
		return result;
	}

	public void setResult(List<BankInfo> result) {
		this.result = result;
	}

}
