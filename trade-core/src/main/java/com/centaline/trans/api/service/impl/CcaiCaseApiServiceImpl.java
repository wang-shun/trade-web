package com.centaline.trans.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.api.service.CaseApiService;
import com.centaline.trans.api.vo.ApiCaseInfo;
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
public class CcaiCaseApiServiceImpl implements CaseApiService {
	@Value("${api.ccai.url}")
	private String ccaiaddress;
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ApiCaseInfo getApiCaseInfo(String ccaiCode) {
		Map<String,String> param = new HashMap<>();
		param.put("ccaiCode",ccaiCode);
		String url =ccaiaddress+"/CCAIData/GetContract"+"?ccaiCode="+ccaiCode;
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

	@Override
	public ApiResultData SyncNetSign(ToSign info) {
		return null;
	}
}
