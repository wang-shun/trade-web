package com.centaline.trans.material.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.material.entity.MmMaterialItem;
@MyBatisRepository
public interface MmMaterialItemMapper {
    int deleteByPrimaryKey(Long pkid);
    
    // 删除待入库
    int deleteWillStorageMmByProperty(MmMaterialItem mmMaterialItem);

    int insert(MmMaterialItem record);

    int insertSelective(MmMaterialItem record);

    MmMaterialItem selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(MmMaterialItem record);

    int updateByPrimaryKey(MmMaterialItem record);    
    
    //查询物品管理信息列表
	List<MmMaterialItem> queryMmMaterialItemList();
	
	List<MmMaterialItem> getMmMaterialItemListByProperty(MmMaterialItem record);

	void insertMaterialInfoFromSpv(MmMaterialItem mmMaterialItem);
	
	int updateMaterialInfoByItemCode(MmMaterialItem mmMaterialItem);
	
	int updateMaterialInfoByPkid(MmMaterialItem mmMaterialItem);
	
}