package com.centaline.trans.eloan.service.impl;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.repository.ToCaseInfoMapper;
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
	
	@Autowired
	private ToCaseInfoMapper toCaseInfoMapper;
	
	@Override
	public String addSelfAppInfo(ToSelfAppInfo toSelfAppInfo) {
		String caseCode = toCaseInfoMapper.findcaseCodeByCcaiCode(toSelfAppInfo.getCcaiCode());
		if(StringUtils.isNotBlank(caseCode)){
			toSelfAppInfo.setCaseCode(caseCode);
			Long count = toSelfAppInfoMapper.insertSelfAppInfo(toSelfAppInfo);
			List<ToAppRecordInfo> list = toSelfAppInfo.getTasks();
			for (ToAppRecordInfo toAppRecordInfo : list) {
				toAppRecordInfo.setSelfAppInfoId(toSelfAppInfo.getPkid());
			}
			toAppRecordInfoMapper.insertAppRecordInfo(list);
			return caseCode;
		}
		return null;
	}

}
