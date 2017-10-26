package com.centaline.api;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.api.service.EvalApiService;
import com.centaline.trans.api.vo.*;
import com.centaline.trans.common.enums.CcaiFlowResultEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;


public class EvalApiServiceTest extends AbstractServiceTest {
	@Autowired
	EvalApiService evalApiService;
	@Autowired
	UamSessionService uamSessionService;
	@Autowired
	UamUserOrgService uamUserOrgService;
	@Test
	public void evalRebateFeedBackTest(){
		SessionUser user = uamSessionService.getSessionUserById("BE72FFF387DE4D0EB5F05ED4187BC25C");
		FlowFeedBack info = new FlowFeedBack(user, CcaiFlowResultEnum.SUCCESS,"测试接口");
		CcaiEvalRebateVo vo = new CcaiEvalRebateVo();
		vo.setAssessCompany("同章评估公司");
		vo.setAssessPrice(new BigDecimal(1200));
		vo.setAssessReceip("");
		vo.setCode("123456");
		ApiResultData result = evalApiService.evalRebateFeedBack(info,vo);
		System.out.println(result);
	}
	@Test
	public void getAllAssessCompany(){
		CcaiAssessCompanyResultData result = evalApiService.getAllAssessCompany();
		System.out.println(result);
		for(AssessCompanyVo c : result.getAssessCompanyList()){
			System.out.println(c);
		}
	}

	@Test
	public void getAllBank(){
		CcaiBankResultData result = evalApiService.getAllBankName();
		System.out.println(result);
		for(String c : result.getBankList()){
			System.out.println(c);
		}
	}

	@Test
	public void syncBankRebate(){
		// ApiResultData result = evalApiService.evalBankRebateSync();
		// System.out.println(result);
	}
	@Test
	public void getUserCode(){
		String id = "ACDA326A51DA4415A24FB7C65F0C213A";
		SessionUser user = uamSessionService.getSessionUserById(id);
		System.out.println(user.getEmployeeCode());
		User u = uamUserOrgService.getUserById(id);
		System.out.println(u.getEmployeeCode());
	}

}