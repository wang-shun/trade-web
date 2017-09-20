package com.centaline.trans.api.service.impl;

import com.centaline.trans.api.service.CaseApiService;
import com.centaline.trans.api.vo.ApiCaseInfo;
import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.task.entity.ToSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerRequestExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import java.net.URL;

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
	public ApiResultData<ApiCaseInfo> getApiCaseInfo(String ccaiCode) {
		//TODO 先提供默认的数据进行使用
		String result = restTemplate.getForObject("http://www.baidu.com",String.class);
		// restTemplate.getForObject(ccaiaddress+"/caseinfo/"+ccaiCode,String.class);
		System.out.println("in service imple ");
		System.out.println(result);
		return null;
	}

	@Override
	public ApiResultData<String> SyncNetSign(ToSign info) {
		return null;
	}
}
