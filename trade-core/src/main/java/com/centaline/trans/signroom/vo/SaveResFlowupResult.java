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
