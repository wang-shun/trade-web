package com.centaline.trans.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.api.service.ApiService;
import com.centaline.trans.api.service.CaseApiService;
import com.centaline.trans.api.vo.ApiCaseInfo;
import com.centaline.trans.api.vo.ApiRebateReportInfo;
import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.task.entity.ToSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 对CCAI案件相关的接口实现
 * @author yinchao
 * @date 2017/9/14
 */
@Service
public class CcaiCaseApiServiceImpl extends ApiService implements CaseApiService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ApiCaseInfo getApiCaseInfo(String ccaiCode) {
		//接口服务开启时 访问CCAI获取数据
		if(serviceIsEnable()){
			return toCcaiGetInfo(ccaiCode);
		}else{
			//生成模拟数据 防止报错
			return demoApiCaseInfo();
		}
	}

	@Override
	public ApiResultData SyncNetSign(ToSign info) {
		return null;
	}

	@Override
	public ApiRebateReportInfo getApiRebateReportInfo(String reportCode) {
		//接口服务开启时 访问CCAI获取数据
		if(serviceIsEnable()){
			return toCcaiGetRebateReportInfo(reportCode);
		}else{
			//生成模拟数据 防止报错
			return demoApiRebateReportInfo();
		}
	}

	/**
	 *
	 * 访问CCAI的服务 获取成交报告信息
	 * @param ccaiCode 成交报告编号
	 * @return
	 */
	private ApiCaseInfo toCcaiGetInfo(String ccaiCode){
		Map<String,String> param = new HashMap<>();
		param.put("ccaiCode",ccaiCode);
		String url =getServiceAddress()+"/CCAIData/GetContract"+"?ccaiCode="+ccaiCode;
		ApiCaseInfo result;
		try {
			String json = restTemplate.getForObject(url,String.class);
			return JSONObject.parseObject(json,ApiCaseInfo.class);
			// return mapper.readValue(json,ApiCaseInfo.class); jackjson 无法转换分成信息和合作信息 原因暂时未知
			// return restTemplate.getForObject(url,ApiCaseInfo.class); 自带的也无法进行转换分成信息和合作信息 而且会报错
		}catch (Exception e){
			e.printStackTrace();
			result = new ApiCaseInfo();
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 获取成交报告demo数据 便于测试
	 * @return
	 */
	private ApiCaseInfo demoApiCaseInfo(){
		String demo = "{\"fees\":{\"cooperateFeeInfo\":[],\"sharingInfo\":[{\"department\":\"交易按揭1组\",\"employee\":\"李宝丰\",\"sharingAmount\":0,\"sharingInstruction\":\"权证过户\",\"sharingProportion\":0,\"turnoverNum\":0,\"type\":2},{\"department\":\"万新村三分行1组\",\"employee\":\"陈芳\",\"sharingAmount\":8240,\"sharingInstruction\":\"其他\",\"sharingProportion\":40,\"turnoverNum\":0,\"type\":1},{\"department\":\"交易按揭1组\",\"employee\":\"胡敏\",\"sharingAmount\":0,\"sharingInstruction\":\"权证贷款\",\"sharingProportion\":0,\"turnoverNum\":0,\"type\":2},{\"department\":\"万新村分行1组\",\"employee\":\"朱莎\",\"sharingAmount\":12360,\"sharingInstruction\":\"成交\",\"sharingProportion\":60,\"turnoverNum\":1,\"type\":1}],\"totalFee\":20600,\"totalPerformance\":20600,\"totalTurnoverNum\":1},\"message\":\"demo数据\",\"prices\":{\"assessmentFee\":null,\"guestCommissionDis\":null,\"guestCommissionMature\":1464192000000,\"guestCommissionTime\":1456416000000,\"guestReceivableCommission\":10300,\"ownerCommissionDis\":null,\"ownerCommissionMature\":1464192000000,\"ownerCommissionTime\":1456416000000,\"ownerReceivableCommission\":10300,\"receiptsAssessmentFee\":null,\"receivableAssessmentFee\":null},\"success\":true}";
		return  JSONObject.parseObject(demo,ApiCaseInfo.class);
	}


	/**
	 *
	 * 访问CCAI的服务 获取返利报告信息
	 * @param ccaiCode 返利报告编号
	 * @return
	 */
	private ApiRebateReportInfo toCcaiGetRebateReportInfo(String reportCode){
		String url =getServiceAddress()+"/CCAIData/GetBankFlowInfo"+"?ccaiCode="+reportCode;
		ApiRebateReportInfo result;
		try {
			String json = restTemplate.getForObject(url,String.class);
			return JSONObject.parseObject(json,ApiRebateReportInfo.class);
			// return mapper.readValue(json,ApiCaseInfo.class); jackjson 无法转换分成信息和合作信息 原因暂时未知
			// return restTemplate.getForObject(url,ApiCaseInfo.class); 自带的也无法进行转换分成信息和合作信息 而且会报错
		}catch (Exception e){
			e.printStackTrace();
			result = new ApiRebateReportInfo();
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	/**
	 * 获取返利报告demo数据 便于测试
	 * @return
	 */
	private ApiRebateReportInfo demoApiRebateReportInfo(){
		String demo = "{\"bfiModel\":{\"BankName\":\"渤海银行\",\"BizMoney\":350.00,\"ReturnMoney\":4700.00,\"dkCertMoney\":75,\"ghCertMoney\":75},\"message\":\"成功！\",\"success\":true}";
		return  JSONObject.parseObject(demo,ApiRebateReportInfo.class);
	}


}
