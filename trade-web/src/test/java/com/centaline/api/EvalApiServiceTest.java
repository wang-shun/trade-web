package com.centaline.api;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.api.service.CaseApiService;
import com.centaline.trans.api.service.EvalApiService;
import com.centaline.trans.api.vo.ApiCaseInfo;
import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.api.vo.CcaiEvalRebateVo;
import com.centaline.trans.api.vo.FlowFeedBack;
import com.centaline.trans.common.enums.CcaiFlowResultEnum;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;


public class EvalApiServiceTest extends AbstractServiceTest {
	@Autowired
	EvalApiService evalApiService;
	@Autowired
	UamSessionService uamSessionService;
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

}