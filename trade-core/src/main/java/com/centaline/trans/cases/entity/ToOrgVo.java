package com.centaline.trans.cases.entity;

public class ToOrgVo {

    private String id;
    private String orgName;
    /**红灯总数*/
    private Integer redNum;
    private String depHierarchy;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Integer getRedNum() {
		return redNum;
	}
	public void setRedNum(Integer redNum) {
		this.redNum = redNum;
	}
	public String getDepHierarchy() {
		return depHierarchy;
	}
	public void setDepHierarchy(String depHierarchy) {
		this.depHierarchy = depHierarchy;
	}
   
}