package com.centaline.trans.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.api.service.ApiService;
import com.centaline.trans.api.service.CommissionAssignApiService;
import com.centaline.trans.api.vo.ApiCommissionAssign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
/**
 * @author xiefei1
 * @since 2017年10月25日 下午9:43:49 
 * @description 调佣前，获取要调佣的成交报告的分成信息
 */
@Service
public class CommissionAssignApiServiceImpl extends ApiService implements CommissionAssignApiService {
	@Autowired
	private RestTemplate restTemplate;
	@Override
	public ApiCommissionAssign getApiCommissionAssign(String ccaiCode) {
		//接口服务开启时 访问CCAI获取数据
		if(serviceCanPull()){
			return toCcaiGetInfo(ccaiCode);
		}else{
			//生成模拟数据 防止报错
			return demoApiCaseInfo();
		}
	}
	
	/**
	 *
	 * 访问CCAI的服务 获取最新的调佣分成信息
	 * @param ccaiCode 成交报告编号
	 * @return
	 */
	private ApiCommissionAssign toCcaiGetInfo(String ccaiCode){
		String url =getServiceAddress()+"/CCAIData/GetAssessContractCommisionAssigns"+"?ccaiCode="+ccaiCode;
		ApiCommissionAssign result;
		try {
			String json = restTemplate.getForObject(url,String.class);
			return JSONObject.parseObject(json,ApiCommissionAssign.class);
		}catch (Exception e){
			e.printStackTrace();
			result = new ApiCommissionAssign();
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	private ApiCommissionAssign demoApiCaseInfo(){
		String demo = "{\"fees\":{\"cooperateFeeInfo\":[],\"sharingInfo\":[{\"department\":\"交易按揭1组\",\"employee\":\"李宝丰\",\"sharingAmount\":0,\"sharingInstruction\":\"权证过户\",\"sharingProportion\":0,\"turnoverNum\":0,\"type\":2},{\"department\":\"万新村三分行1组\",\"employee\":\"陈芳\",\"sharingAmount\":8240,\"sharingInstruction\":\"其他\",\"sharingProportion\":40,\"turnoverNum\":0,\"type\":1},{\"department\":\"交易按揭1组\",\"employee\":\"胡敏\",\"sharingAmount\":0,\"sharingInstruction\":\"权证贷款\",\"sharingProportion\":0,\"turnoverNum\":0,\"type\":2},{\"department\":\"万新村分行1组\",\"employee\":\"朱莎\",\"sharingAmount\":12360,\"sharingInstruction\":\"成交\",\"sharingProportion\":60,\"turnoverNum\":1,\"type\":1}],\"totalFee\":20600,\"totalPerformance\":20600,\"totalTurnoverNum\":1},\"message\":\"demo数据\",\"prices\":{\"assessmentFee\":null,\"guestCommissionDis\":null,\"guestCommissionMature\":1464192000000,\"guestCommissionTime\":1456416000000,\"guestReceivableCommission\":10300,\"ownerCommissionDis\":null,\"ownerCommissionMature\":1464192000000,\"ownerCommissionTime\":1456416000000,\"ownerReceivableCommission\":10300,\"receiptsAssessmentFee\":null,\"receivableAssessmentFee\":null},\"success\":true}";
		return  JSONObject.parseObject(demo,ApiCommissionAssign.class);
	}

}
