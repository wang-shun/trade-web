package com.centaline.trans.eloan.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
	public void addSelfAppInfo(ToSelfAppInfo toSelfAppInfo) {
		Long count = toSelfAppInfoMapper.insertSelfAppInfo(toSelfAppInfo);
	}

	@Override
	public ToSelfAppInfo getAppInfoByCaseCode(String caseCode,String type) {
		return toSelfAppInfoMapper.getAppInfoByCaseCode(caseCode,type);
	}

	@Override
	public List<ToAppRecordInfo> getAppRecordInfo(String appInfoId) {
		if(StringUtils.isBlank(appInfoId)){
			return new ArrayList<ToAppRecordInfo>();
		}
		return toAppRecordInfoMapper.getAppRecordInfo(appInfoId);
	}

	@Override
	public ToSelfAppInfo getAppInfoByCCAICode(String ccaiCode,String type) {
		if(StringUtils.isBlank(ccaiCode)){
			return null;
		}
		return toSelfAppInfoMapper.getAppInfoByCCAICode(ccaiCode,type);
	}

	@Override
	public int saveBatchToAppRecordInfo(List<ToAppRecordInfo> listRecord) {
		return toAppRecordInfoMapper.insertAppRecordInfo(listRecord);
	}

	@Override
	public void updateByPrimaryKeySelective(ToSelfAppInfo toSelfAppInfo) {
		toSelfAppInfoMapper.updateByPrimaryKeySelective(toSelfAppInfo);
		
	}

	@Override
	public ToSelfAppInfo getAppInfoByCaseCode(String caseCode) {
		return toSelfAppInfoMapper.getAppInfoByCaseCo(caseCode);
	}


}