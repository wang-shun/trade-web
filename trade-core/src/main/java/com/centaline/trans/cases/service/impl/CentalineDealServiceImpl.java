package com.centaline.trans.cases.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.centaline.trans.cases.service.CentalineDealService;



@Service
public class CentalineDealServiceImpl implements CentalineDealService{

	@Value("${centalineDeal.url}")
	private String centalineDeal;
	
	@Value("${validctmdeal.url}")
	private String validctmdeal;
	
	
	public String centalineDeal(){
		String centalineDealUrl=centalineDeal;
		return centalineDealUrl;
	}
	
	
	public String validctmDeal(){
		String validctmdealUrl=validctmdeal;
		return validctmdealUrl;
	}
	
	
}
