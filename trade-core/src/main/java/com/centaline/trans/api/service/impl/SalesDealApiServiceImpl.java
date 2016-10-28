package com.centaline.trans.api.service.impl;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.aist.common.exception.BusinessException;
import com.alibaba.fastjson.JSONArray;
import com.centaline.trans.api.service.ApiLogService;
import com.centaline.trans.api.service.SalesApiResponse;
import com.centaline.trans.api.service.SalesDealApiService;
import com.centaline.trans.cases.service.CentalineDealService;


@Service
public class SalesDealApiServiceImpl implements SalesDealApiService{
	
	@Autowired
	private CentalineDealService centalinedealService;
	
	@Qualifier("restTemplate")
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ApiLogService apiLogservice;
	
	// 通知三级市场中原成交
	@Override
	public void noticeSalesDeal(String ctmCode) throws BusinessException {
		String centalineDealUrl=centalinedealService.centalineDeal();
		if(null!=ctmCode){
			MultiValueMap<String, Object> mvm=new LinkedMultiValueMap<String, Object>();
			mvm.add("ctmcode", ctmCode);
			try{
				SalesApiResponse result=restTemplate.postForObject(centalineDealUrl, mvm, SalesApiResponse.class);
				if(null!=result){
					if(result.getSuccess()){
						apiLogservice.apiLog("SALES", centalineDealUrl, ctmCode, JSONArray.toJSONString(result), "1", null);
					}else{
						apiLogservice.apiLog("SALES", centalineDealUrl, ctmCode, JSONArray.toJSONString(result), "1", result.getMessage());
						//throw new BusinessException("房源成交状态回写三级市场系统失败"+result.getMessage());
					}
				}else{
					apiLogservice.apiLog("SALES", centalineDealUrl, ctmCode, "", "0", null);
					//throw new BusinessException("调用接口异常");
				}
			}catch(Exception e){
				apiLogservice.apiLog("SALES", centalineDealUrl, ctmCode, e.getMessage(), "0", ExceptionUtils.getRootCauseMessage(e).substring(0, 1000));
			}
		}else{
			throw new BusinessException("ctmcode, 必填。");
		}
		
	}

	
	// 调用销售接口,判断是否可以进行过户操作
	@Override
	public SalesApiResponse checkCanHouseTransfer(String ctmCode)
			throws BusinessException {
		
		String validCtmDealUrl=centalinedealService.validctmDeal();
		if(null!=ctmCode){
			MultiValueMap<String, Object> mvm=new LinkedMultiValueMap<String, Object>();
			mvm.add("ctmcode", ctmCode);
			SalesApiResponse result=restTemplate.postForObject(validCtmDealUrl, mvm, SalesApiResponse.class);
			if(null!=result){
				apiLogservice.apiLog("SALES", validCtmDealUrl, ctmCode, JSONArray.toJSONString(result), "1", null);
				return result;
			}else{
				apiLogservice.apiLog("SALES", validCtmDealUrl, ctmCode, "", "0", null);
				throw new BusinessException("调用接口异常");
			}
		}else{
			throw new BusinessException("ctmcode, 必填。");
		}
		

	}

	
	
}
