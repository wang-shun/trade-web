package com.centaline.trans.material.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.centaline.trans.material.entity.MmMaterialItem;
import com.centaline.trans.material.repository.MmMaterialItemMapper;
import com.centaline.trans.material.service.MmMaterialItemService;

@Service
public class MmMaterialItemServiceImpl implements MmMaterialItemService{
	
	@Autowired
	private MmMaterialItemMapper mmMaterialItemMapper;
	
	
	@Override
	public List<MmMaterialItem> queryMmMaterialItemList() {
		// TODO Auto-generated method stub
		return mmMaterialItemMapper.queryMmMaterialItemList();
	}


	@Override
	public void insertMaterialInfoFromSpv(MmMaterialItem mmMaterialItem) {
		// TODO Auto-generated method stub
		if(null != mmMaterialItem){
			mmMaterialItemMapper.insertMaterialInfoFromSpv(mmMaterialItem);
		}else{
			throw new BusinessException("传入对象为空,物品保管信息失败!");
		}
		
	}


	@Override
	public int updateMaterialInfoByItemCode(String itemCode) {
		// TODO Auto-generated method stub
		
		return mmMaterialItemMapper.updateMaterialInfoByItemCode(itemCode);
	}


	@Override
	public MmMaterialItem queryMmMaterialByPkid(Long pkid) {
		// TODO Auto-generated method stub
		return  mmMaterialItemMapper.selectByPrimaryKey(pkid);
	}

}
