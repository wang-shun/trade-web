package com.centaline.trans.material.vo;


import java.util.List;

import com.centaline.trans.material.entity.MmMaterialItem;

public class MaterialPackageVo {
	
    private String itemAddrCode;
    
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

	
}
