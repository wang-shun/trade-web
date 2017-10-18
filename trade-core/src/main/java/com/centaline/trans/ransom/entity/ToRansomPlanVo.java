package com.centaline.trans.ransom.entity;

import java.util.Date;

/**
 * 赎楼计划
 * @author wbcaiyx
 *
 */
public class ToRansomPlanVo {
	
	private Long pkid;
	/**
	 * 赎楼编号
	 */
    private String ransomCode;
    /**
	 * 赎楼环节
	 */
    private String partCode;
    /**
	 * 赎楼环节名
	 */
    private String partName;
    /**
	 * 计划时间
	 */  
    private Date estPartTime;
    
    private String estPartTimeStr;
    /**
	 * 实际完成时间
	 */
    private Date completeTime;
    
    private String completeTimeStr;
    
    private Date createTime;
    /**
     * 创建人
     */
    private String createUser;
    
    /**
     * 更新人
     */
    private String updateUser;
    
    private Date updateTime;
    
    private String remark;
    
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRansomCode() {
		return ransomCode;
	}
	public void setRansomCode(String ransomCode) {
		this.ransomCode = ransomCode;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public Date getEstPartTime() {
		return estPartTime;
	}
	public void setEstPartTime(Date estPartTime) {
		this.estPartTime = estPartTime;
	}
	public String getEstPartTimeStr() {
		return estPartTimeStr;
	}
	public void setEstPartTimeStr(String estPartTimeStr) {
		this.estPartTimeStr = estPartTimeStr;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	public String getCompleteTimeStr() {
		return completeTimeStr;
	}
	public void setCompleteTimeStr(String completeTimeStr) {
		this.completeTimeStr = completeTimeStr;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Long getPkid() {
		return pkid;
	}
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
    

}