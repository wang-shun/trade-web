package com.centaline.trans.material.service;

import java.util.List;

import com.centaline.trans.material.entity.MmMaterialItem;

public interface MmMaterialItemService {
	
    List<MmMaterialItem> queryMmMaterialItemList();
    
    void  insertMaterialInfoFromSpv(MmMaterialItem mmMaterialItem);    

    int  updateMaterialInfoByItemCode(MmMaterialItem mmMaterialItem);
    
    MmMaterialItem queryMmMaterialByPkid(Long pkid);
}
