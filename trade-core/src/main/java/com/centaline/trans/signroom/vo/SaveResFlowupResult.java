package com.centaline.trans.signroom.vo;

/**
 * 保存跟进信息返回的结果信息
 * 
 * @author yinjf2
 *
 */
public class SaveResFlowupResult {

	private String result; // true:保存成功,false:保存失败

	private String createDateTime; // 创建时间

	private String realName; // 跟进人姓名

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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

}
