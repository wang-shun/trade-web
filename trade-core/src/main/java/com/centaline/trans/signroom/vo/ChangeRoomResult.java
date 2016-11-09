package com.centaline.trans.signroom.vo;

/**
 * 变更签约室返回结果信息
 * 
 * @author yinjf2
 *
 */
public class ChangeRoomResult {

	private String result; // true:变更成功,false:变更失败

	private String operateTime; // 操作时间

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

}
