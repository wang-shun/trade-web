package com.centaline.trans.eloan.entity;

import java.util.Date;

/**
 * 审批记录信息
 * 
 * @author wblujian
 *
 */
public class ToAppRecordInfo {

	
	private Long pkid;
	
	private String  applyUserName; // 审批人域账号 ,
	
	private String applyRealName; // 审批人名称 ,
	
	private String level ; //审批人级别 ,
	
	private Date dealTime ;// 审批时间 ,
	
	private Integer result ;// 审批结果 = ['-1', '0', '1']integerEnum:-1, 0, 1,
	
	private String comment ; // 审批意见
	
	private Long  selfAppInfoId; //关联自办申请信息表

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public String getApplyRealName() {
		return applyRealName;
	}

	public void setApplyRealName(String applyRealName) {
		this.applyRealName = applyRealName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getSelfAppInfoId() {
		return selfAppInfoId;
	}

	public void setSelfAppInfoId(Long selfAppInfoId) {
		this.selfAppInfoId = selfAppInfoId;
	}
	
	
	
}
