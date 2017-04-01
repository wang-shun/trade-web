package com.centaline.trans.signroom.vo;

/**
 * 开始或结束使用返回的结果信息
 * 
 * @author yinjf2
 *
 */
public class StartAndEndUseResult {

	private String result; // 操作结果(返回true,操作成功;返回false,操作失败)

	private String operateDateTime; // 操作时间

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOperateDateTime() {
		return operateDateTime;
	}

	public void setOperateDateTime(String operateDateTime) {
		this.operateDateTime = operateDateTime;
	}

}
