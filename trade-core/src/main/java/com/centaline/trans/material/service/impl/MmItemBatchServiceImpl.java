package com.centaline.trans.material.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.material.entity.MmItemBatch;
import com.centaline.trans.material.repository.MmItemBatchMapper;
import com.centaline.trans.material.service.MmItemBatchService;

@Service
public class MmItemBatchServiceImpl implements MmItemBatchService{
	
	@Autowired
	private MmItemBatchMapper mmItemBatchMapper;
	
	@Override
	public List<MmItemBatch> queryMmItemBatchList(Long itemId) {
		// TODO Auto-generated method stub
		return mmItemBatchMapper.queryMmItemBatchList(itemId);
	}

}
