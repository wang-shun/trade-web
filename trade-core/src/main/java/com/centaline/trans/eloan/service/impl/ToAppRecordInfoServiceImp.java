package com.centaline.trans.eloan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.eloan.entity.ToAppRecordInfo;
import com.centaline.trans.eloan.repository.ToAppRecordInfoMapper;
import com.centaline.trans.eloan.service.ToAppRecordInfoService;

/**
 * 
 * @author wblujian
 *
 */
@Service
public class ToAppRecordInfoServiceImp implements ToAppRecordInfoService {

	@Autowired
	private  ToAppRecordInfoMapper toAppRecordInfoMapper;
	
	@Override
	public ToAppRecordInfo getAppRedordByAppInfoId(Long pkid) {
		List<ToAppRecordInfo> list = toAppRecordInfoMapper.getAppRecordInfo(pkid.toString());
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

}
