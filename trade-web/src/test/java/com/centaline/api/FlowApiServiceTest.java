package com.centaline.api;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.api.service.FlowApiService;
import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.api.vo.FlowFeedBack;
import com.centaline.trans.common.enums.CcaiFlowResultEnum;
import com.centaline.trans.common.enums.CcaiTaskEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FlowApiServiceTest extends AbstractServiceTest {
	@Autowired
	FlowApiService service;

	@Autowired
	UamSessionService uamSessionService;

	@Test
	public void tradeFeedBackCcai() throws Exception {
		SessionUser user = uamSessionService.getSessionUserById("0EEE36FF103841A882F2AFAF9CE2BC24");

		FlowFeedBack info = new FlowFeedBack(user, CcaiFlowResultEnum.SUCCESS,"ok");
		ApiResultData result = service.tradeFeedBackCcai("ZY-TJ-2017090006", CcaiTaskEnum.TRADE_WARRANT_MANAGER,info);
		System.out.println(result.getMessage()+"-------"+result.isSuccess());
	}

}