package com.centaline.trans.signroom.vo;

/**
 * 添加跟进信息返回的结果信息
 * 
 * @author yinjf2
 *
 */
public class SaveResFlowupResult {

	private String result; // 操作结果(返回true,添加跟进信息成功;返回false,添加跟进信息失败)

	private String realName; // 当前操作人名称

	private String createDateTime; // 添加跟进信息时间

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}

}
