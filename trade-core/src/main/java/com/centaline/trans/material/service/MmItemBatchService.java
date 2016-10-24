package com.centaline.trans.material.service;

import java.util.List;

import com.centaline.trans.material.entity.MmItemBatch;

public interface MmItemBatchService {

	List<MmItemBatch> queryMmItemBatchList(Long itemId);
	
	int insertSelective(MmItemBatch mmItemBatch);
}
