package com.centaline.api;

import com.centaline.trans.api.service.CaseApiService;
import com.centaline.trans.api.vo.ApiCaseInfo;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class CaseApiServiceTest extends AbstractServiceTest {
	@Autowired
	CaseApiService caseApiService;

	@Test
	public void getApiCaseInfo() throws Exception {
		try {
			ApiCaseInfo result = caseApiService.getApiCaseInfo("TJZY-ZBZF1-1708-0002");
			System.out.println(result);
			result = caseApiService.getApiCaseInfo("TJZY-LXYE1-1506-0003");
			System.out.println(result);
			result = caseApiService.getApiCaseInfo("TJZY-WXCF1-1602-0011");
			System.out.println(result);
			result = caseApiService.getApiCaseInfo("TJZY-JPJZ2-1605-0007");
			System.out.println(result);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	@Ignore
	@Test
	public void syncNetSign() throws Exception {
	}

}