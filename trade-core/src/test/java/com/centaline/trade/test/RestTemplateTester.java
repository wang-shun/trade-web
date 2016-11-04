package com.centaline.trade.test;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.centaline.trans.api.service.SalesApiResponse;

public class RestTemplateTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, Object> mvm=new LinkedMultiValueMap<String, Object>();
		mvm.add("ctmcode", "BMP-2-201606-0060");
		SalesApiResponse result=restTemplate.postForObject("http://sales.centaline.com:8082/sales-web/api/ctm/centalineDeal", mvm, SalesApiResponse.class);
		System.out.println(result);
	}

}
