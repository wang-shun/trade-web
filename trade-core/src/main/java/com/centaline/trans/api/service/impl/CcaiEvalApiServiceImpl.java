package com.centaline.trans.api.service.impl;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.api.service.ApiService;
import com.centaline.trans.api.service.EvalApiService;
import com.centaline.trans.api.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 评估模块与CCAI交互的接口
 * @author yinchao
 * @date 2017/10/11
 */
@Service
public class CcaiEvalApiServiceImpl extends ApiService implements EvalApiService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ApiResultData evalRebateFeedBack(FlowFeedBack result, CcaiEvalRebateVo info) {
		ApiResultData apiResult = new CcaiFlowApiResultData();
		if(serviceIsEnable()){
			//TODO 校验 并获取评估返利报告CCAI中的applyId和评估报告编号
			String reportNo = "TJZY-FLCF2-1702-0001";
			String applyId = "A458D9A7-DAD0-4C58-BEE1-1623322693F1";
			SessionUser user = result.getUser();
			//转换成CCAI需要的信息
			CcaiPostAssessFlowInfo requestBody = new CcaiPostAssessFlowInfo();
			requestBody.setReportNo(reportNo);
			requestBody.setApplyId(applyId);
			requestBody.setAssessPrice(info.getAssessPrice());
			requestBody.setAssessCompany(info.getAssessCompany());
			requestBody.setAssessReceip(info.getAssessReceip());
			requestBody.setUserName(user.getUsername());
			requestBody.setRealName(user.getRealName());
			requestBody.setResult(result.getResult().getCode());
			requestBody.setComment(result.getComment());
			requestBody.setApproveTime(result.getApproveTime());
			String url =getServiceAddress()+"/CCAIData/PostAssessFlowInfo";
			apiResult = restTemplate.postForObject(url,requestBody,CcaiFlowApiResultData.class);
		}else{
			apiResult.setSuccess(true);
			apiResult.setMessage("服务未开启，默认成功!");
		}
		return apiResult;
	}

}
