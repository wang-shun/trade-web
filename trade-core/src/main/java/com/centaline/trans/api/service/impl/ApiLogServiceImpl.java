package com.centaline.trans.api.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.api.entity.TsApiLog;
import com.centaline.trans.api.repository.TsApiLogMapper;
import com.centaline.trans.api.service.ApiLogService;

@Service
public class ApiLogServiceImpl implements ApiLogService{

	@Autowired
	private TsApiLogMapper tsapiLogMapper;
	
	@Autowired
	private UamSessionService uamSessionService;
	
	@Override
	public void apiLog(String module, String apiUrl, String params, String result, String status, String errMsg) {
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
	}


}
