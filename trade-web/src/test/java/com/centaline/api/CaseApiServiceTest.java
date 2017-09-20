package com.centaline.api;

import com.centaline.trans.api.service.CaseApiService;
import com.centaline.trans.api.vo.ApiCaseInfo;
import com.centaline.trans.api.vo.ApiResultData;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-test.xml",
		"classpath*:com/aist/common/**/META-INF/beans.xml",
		"classpath*:com/aist/uam/**/META-INF/httpInvoke-beans.xml",
		"classpath*:com/aist/uam/**/META-INF/beans.xml",
		"classpath*:com/aist/uam/auth/META-INF/shiro-beans.xml",
		"classpath*:com/centaline/trans/**/META-INF/core-beans.xml",
		"classpath*:com/centaline/trans/**/META-INF/web-beans.xml",
		"classpath*:com/aist/message/**/META-INF/beans.xml"})
@ActiveProfiles({"development","jedis"})
public class CaseApiServiceTest {
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