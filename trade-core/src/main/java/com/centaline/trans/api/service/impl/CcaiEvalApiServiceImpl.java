package com.centaline.trans.api.service.impl;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.api.service.ApiService;
import com.centaline.trans.api.service.EvalApiService;
import com.centaline.trans.api.vo.*;
import com.centaline.trans.bankRebate.entity.ToBankRebate;
import com.centaline.trans.bankRebate.entity.ToBankRebateInfo;
import com.centaline.trans.bankRebate.vo.ToBankRebateInfoVO;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.eval.entity.ToEvalRebate;
import com.centaline.trans.eval.service.ToEvalRebateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 评估模块与CCAI交互的接口
 * @author yinchao
 * @date 2017/10/11
 */
@Service
public class CcaiEvalApiServiceImpl extends ApiService implements EvalApiService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ToEvalRebateService toEvalRebateService;

	@Autowired
	private ToCaseInfoMapper toCaseInfoMapper;

	@Autowired
	private UamBasedataService uamBasedataService;

	@Autowired
	private UamUserOrgService uamUserOrgService;

	@Override
	public ApiResultData evalRebateFeedBack(FlowFeedBack result, CcaiEvalRebateVo info) {
		ApiResultData apiResult = new CcaiFlowApiResultData();
		if(serviceIsEnable()){
			//获取评估返利报告CCAI中的applyId和评估报告编号
			ToEvalRebate rebate = toEvalRebateService.findToEvalRebateByCaseCode(info.getCode());
			ToCaseInfo caseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(info.getCode());
			if(rebate!=null && caseInfo!=null){
				String reportNo = caseInfo.getCcaiCode();
				String applyId = rebate.getEvaRpocessId();
				SessionUser user = result.getUser();
				//转换成CCAI需要的信息
				CcaiPostAssessFlowInfo requestBody = new CcaiPostAssessFlowInfo();
				requestBody.setReportNo(reportNo);
				requestBody.setApplyId(applyId);
				requestBody.setAssessPrice(info.getAssessPrice());
				requestBody.setAssessCompany(info.getAssessCompany());
				requestBody.setAssessCompanyPrice(info.getAssessCompanyPrice());
				requestBody.setAssessReceip(info.getAssessReceip());
				requestBody.setUserName(user.getUsername());
				requestBody.setRealName(user.getRealName());
				requestBody.setResult(result.getResult().getCode());
				requestBody.setComment(result.getComment());
				requestBody.setApproveTime(result.getApproveTime());
				String url =getServiceAddress()+"/CCAIData/PostAssessFlowInfo";
				apiResult = restTemplate.postForObject(url,requestBody,CcaiFlowApiResultData.class);
			}else{
				apiResult.setSuccess(false);
				apiResult.setMessage("未获取到案件["+info.getCode()+"]，对应的返利申请信息!");
			}
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

	@Override
	public ApiResultData evalBankRebateSync(ToBankRebateInfoVO info,String userId) {
		ApiResultData apiResult = new CcaiFlowApiResultData();
		if(serviceIsEnable()){
			User user = uamUserOrgService.getUserById(userId);
			if(user!=null){
				CcaiPostBankFlowInfo request = new CcaiPostBankFlowInfo();
				request.setBrokageBack(convertBrokageBack(info.getToBankRebate(),user));
				request.setBbdList(convertBrokageBackDetail(info.getToBankRebateInfoList()));
				String url =getServiceAddress()+"/CCAIData/PostBankFlowInfo";
				apiResult = restTemplate.postForObject(url,request,CcaiFlowApiResultData.class);
			}else{
				apiResult.setSuccess(false);
				apiResult.setMessage("未获取到用户："+userId);
			}
		}else{
			apiResult.setSuccess(true);
			apiResult.setMessage("服务未开启，默认成功!");
		}
		return apiResult;
	}

	@Override
	public ApiResultData evalBankRebateModify(ToBankRebateInfoVO info, String userId) {
		ApiResultData apiResult = new CcaiFlowApiResultData();
		if(serviceIsEnable()){
			User user = uamUserOrgService.getUserById(userId);
			if(user!=null){
				JSONObject request = new JSONObject();
				request.put("applyId",info.getToBankRebate().getCaseCode());//目前caseCode存的是CCAI APPLYID
				request.put("userName",user.getUsername());
				request.put("result",1);
				request.put("comment","调整完成");
				request.put("brokageBack",convertBrokageBack(info.getToBankRebate(),user));
				request.put("bbdList",convertBrokageBackDetail(info.getToBankRebateInfoList()));
				String url =getServiceAddress()+"/CCAIData/PostModifBankFlowInfo";
				apiResult = restTemplate.postForObject(url,request,CcaiFlowApiResultData.class);
			}else{
				apiResult.setSuccess(false);
				apiResult.setMessage("未获取到用户："+userId);
			}
		}else{
			apiResult.setSuccess(true);
			apiResult.setMessage("服务未开启，默认成功!");
		}
		return apiResult;
	}

	@Override
	public CcaiBankResultData getAllBankName() {
		String responseData = "";
		CcaiBankResultData result = new CcaiBankResultData();
		if(serviceIsEnable()){
			String url =getServiceAddress()+"/CCAIData/GetBasicParameters?type=Bank";
			responseData = restTemplate.getForObject(url,String.class);
		}else{
			responseData = "{\"bankList\":[{\"BankName\":\"花旗银行\"},{\"BankName\":\"建设银行\"},{\"BankName\":\"上海银行\"},{\"BankName\":\"评估公司\"},{\"BankName\":\"华夏银行\"},{\"BankName\":\"中信银行\"},{\"BankName\":\"天津银行\"},{\"BankName\":\"浦发银行\"},{\"BankName\":\"北京银行\"},{\"BankName\":\"招商银行\"},{\"BankName\":\"农业银行\"},{\"BankName\":\"中德住房储蓄银行\"},{\"BankName\":\"典当公司\"},{\"BankName\":\"中国银行\"},{\"BankName\":\"光大银行\"},{\"BankName\":\"渤海银行\"},{\"BankName\":\"农商银行\"},{\"BankName\":\"交通银行\"},{\"BankName\":\"邮政储蓄银行\"},{\"BankName\":\"民生银行\"},{\"BankName\":\"兴业银行\"},{\"BankName\":\"工商银行\"}],\"message\":\"成功\",\"success\":true}";
		}
		result =  JSONObject.parseObject(responseData,CcaiBankResultData.class);
		return result;
	}

	/**
	 * 将银行返利基本信息转换为CCAI需要的信息
	 * @param rebate 银行返利申请信息
	 * @param user 申请人信息
	 * @return
	 */
	private CcaiBrokageBack convertBrokageBack(ToBankRebate rebate, User user){
		CcaiBrokageBack base = new CcaiBrokageBack();
		base.setBackID(rebate.getPkid());
		base.setRecordEmpNo(user.getEmployeeCode());
		base.setRecordDate(rebate.getApplyTime());
		base.setTotalMoney(rebate.getRebateTotal());
		base.setRemark(rebate.getComment());
		return base;
	}

	/**
	 * 将银行返利案件信息转换成CCAI通讯信息
	 * @param infos
	 * @return
	 */
	private List<CcaiBrokageBackDetail> convertBrokageBackDetail(List<ToBankRebateInfo> infos){
		List<CcaiBrokageBackDetail> details = new ArrayList<>();
		for(ToBankRebateInfo i : infos){
			CcaiBrokageBackDetail detail = new CcaiBrokageBackDetail();
			detail.setReportNo(i.getCcaiCode());
			detail.setReturnMoney(i.getRebateMoney());
			detail.setCertMoney(i.getRebateWarrant());
			detail.setBizMoney(i.getRebateBusiness());
			detail.setBankName(uamBasedataService.getDictValue("bank_rebate_bank",i.getBankName()));
			details.add(detail);
		}
		return details;
	}

}
