package com.centaline.trans.material.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.material.entity.MmIoBatch;
import com.centaline.trans.material.repository.MmIoBatchMapper;
import com.centaline.trans.material.service.MmIoBatchService;

@Service
public class MmIoBatchServiceImpl implements  MmIoBatchService{
	
	@Autowired
	private MmIoBatchMapper mmIoBatchMapper;
	
	@Override
	public MmIoBatch queryMmIoBatchByPkid(Long pkid) {
		// TODO Auto-generated method stub
		return mmIoBatchMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public long insertMmIoBatchInfo(MmIoBatch mmIoBatch) {
		// TODO Auto-generated method stub
		return mmIoBatchMapper.insertMmIoBatchInfo(mmIoBatch);
	}

}
