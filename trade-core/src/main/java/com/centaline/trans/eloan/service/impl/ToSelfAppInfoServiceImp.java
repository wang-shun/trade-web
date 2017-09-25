package com.centaline.trans.eloan.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.eloan.entity.ToAppRecordInfo;
import com.centaline.trans.eloan.entity.ToSelfAppInfo;
import com.centaline.trans.eloan.repository.ToAppRecordInfoMapper;
import com.centaline.trans.eloan.repository.ToSelfAppInfoMapper;
import com.centaline.trans.eloan.service.ToSelfAppInfoService;
/**
 * 
 * @author wblujian
 *
 */
@Service
public class ToSelfAppInfoServiceImp implements ToSelfAppInfoService {
	
	
	@Autowired
	private ToSelfAppInfoMapper toSelfAppInfoMapper;
	
	@Autowired
	private ToAppRecordInfoMapper toAppRecordInfoMapper;

	@Override
	public void insertSelfAppInfo(ToSelfAppInfo toSelfAppInfo) {
		Long count = toSelfAppInfoMapper.insertSelfAppInfo(toSelfAppInfo);
		List<ToAppRecordInfo> list = toSelfAppInfo.getTasks();
		for (ToAppRecordInfo toAppRecordInfo : list) {
			toAppRecordInfo.setSelfAppInfoId(toSelfAppInfo.getPkid());
		}
		toAppRecordInfoMapper.insertAppRecordInfo(list);
	}

}
