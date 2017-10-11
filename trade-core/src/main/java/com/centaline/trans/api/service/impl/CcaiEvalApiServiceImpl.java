package com.centaline.trans.api.service.impl;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONObject;
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

	@Override
	public CcaiAssessCompanyResultData getAllAssessCompany() {
		CcaiAssessCompanyResultData apiResult = new CcaiAssessCompanyResultData();
		String responseData = "";
		if(serviceIsEnable()){
			String url =getServiceAddress()+"/CCAIData/GetBasicParameters?type=AssessCompany";
			responseData = restTemplate.getForObject(url,String.class);
			//自动转换 无法转换公司列表
			// apiResult = restTemplate.getForObject(url,CcaiAssessCompanyResultData.class);
		}else{
			responseData = "{\"assessCompanyList\":[{\"AssessCompanyName\":\"同章评估公司(500)\",\"AssessCompanyPrice\":500},{\"AssessCompanyName\":\"同章评估公司(400)\",\"AssessCompanyPrice\":400},{\"AssessCompanyName\":\"志信评估公司\",\"AssessCompanyPrice\":500},{\"AssessCompanyName\":\"融信评估公司\",\"AssessCompanyPrice\":300},{\"AssessCompanyName\":\"悦兴评估公司\",\"AssessCompanyPrice\":300},{\"AssessCompanyName\":\"天元评估公司\",\"AssessCompanyPrice\":500},{\"AssessCompanyName\":\"融信评估公司(500)\",\"AssessCompanyPrice\":500},{\"AssessCompanyName\":\"其它评估公司\",\"AssessCompanyPrice\":0},{\"AssessCompanyName\":\"500\",\"AssessCompanyPrice\":500},{\"AssessCompanyName\":\"中同华评估公司\",\"AssessCompanyPrice\":450},{\"AssessCompanyName\":\"中同华评估公司(450)\",\"AssessCompanyPrice\":450},{\"AssessCompanyName\":\"中同华评估公司(500)\",\"AssessCompanyPrice\":500},{\"AssessCompanyName\":\"津港评估公司\",\"AssessCompanyPrice\":300},{\"AssessCompanyName\":\"国策评估公司\",\"AssessCompanyPrice\":500}],\"message\":\"成功\",\"success\":true}";
		}
		apiResult =  JSONObject.parseObject(responseData,CcaiAssessCompanyResultData.class);
		return apiResult;
	}

}
