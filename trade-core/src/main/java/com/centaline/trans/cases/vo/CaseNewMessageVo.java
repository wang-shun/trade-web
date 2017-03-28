package com.centaline.trans.cases.vo;


import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;


/**
 * 功能：新建案件信息推送的 vo
 * @author zhangxb16
 *
 */

public class CaseNewMessageVo {
	
	private TgGuestInfo tgGuestInfo;  // 客户
	
	private ToPropertyInfo toPropertyInfo; // 物业信息
	
	private ToCaseInfo toCaseInfo;  // 案件信息

	

	public TgGuestInfo getTgGuestInfo() {
		return tgGuestInfo;
	}

	public void setTgGuestInfo(TgGuestInfo tgGuestInfo) {
		this.tgGuestInfo = tgGuestInfo;
	}

	public ToPropertyInfo getToPropertyInfo() {
		return toPropertyInfo;
	}

	public void setToPropertyInfo(ToPropertyInfo toPropertyInfo) {
		this.toPropertyInfo = toPropertyInfo;
	}

	public ToCaseInfo getToCaseInfo() {
		return toCaseInfo;
	}

	public void setToCaseInfo(ToCaseInfo toCaseInfo) {
		this.toCaseInfo = toCaseInfo;
	}
	

}
