package com.centaline.trans.material.vo;


import java.util.List;

import com.centaline.trans.material.entity.MmMaterialItem;

public class MaterialPackageVo {
	
    private String itemAddrCode;
    
    private String attachPkid;
    
    private String relevantUser;
    
    private String relevantUserId;
    
    private List<MmMaterialItem> materialList;
    
	public String getItemAddrCode() {
		return itemAddrCode;
	}
	public void setItemAddrCode(String itemAddrCode) {
		this.itemAddrCode = itemAddrCode;
	}

	public List<MmMaterialItem> getMaterialList() {
		return materialList;
	}
	public void setMaterialList(List<MmMaterialItem> materialList) {
		this.materialList = materialList;
	}
	public String getAttachPkid() {
		return attachPkid;
	}
	public void setAttachPkid(String attachPkid) {
		this.attachPkid = attachPkid;
	}
	public String getRelevantUser() {
		return relevantUser;
	}
	public void setRelevantUser(String relevantUser) {
		this.relevantUser = relevantUser;
	}
	public String getRelevantUserId() {
		return relevantUserId;
	}
	public void setRelevantUserId(String relevantUserId) {
		this.relevantUserId = relevantUserId;
	}
	
	
	
}
