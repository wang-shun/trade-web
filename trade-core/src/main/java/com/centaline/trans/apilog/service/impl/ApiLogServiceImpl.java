package com.centaline.trans.apilog.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.apilog.entity.TsApiLog;
import com.centaline.trans.apilog.repository.TsApiLogMapper;
import com.centaline.trans.apilog.service.ApiLogService;

@Service
public class ApiLogServiceImpl implements ApiLogService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TsApiLogMapper tsapiLogMapper;
	
	@Autowired
	private UamSessionService uamSessionService;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void apiLog(String module, String apiUrl, String params, String result, String status, String errMsg) {
		try{
			TsApiLog apilog=new TsApiLog();
			apilog.setModule(module);
			apilog.setApiUrl(apiUrl);
			apilog.setParams(params);
			apilog.setResult(result);
			apilog.setStatus(status);
			apilog.setErrMsg(errMsg);
			apilog.setCreateDate(new Date());
			SessionUser user=uamSessionService.getSessionUser();
			if(null!=user){
				apilog.setCreateBy(user.getId());
			}else{
				apilog.setCreateBy("SYSTEM");
			}
			
			tsapiLogMapper.insert(apilog);
		}catch(Exception e){
			logger.error("Error in save api log", e);
		}
		
	}


}
