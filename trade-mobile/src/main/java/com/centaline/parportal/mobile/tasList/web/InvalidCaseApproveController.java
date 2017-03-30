package com.centaline.parportal.mobile.tasList.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.uam.auth.remote.UamSessionService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.vo.MobileHolder;
import com.centaline.trans.task.service.InvalidCaseApproveService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;
import com.centaline.trans.utils.Pages2JSONMoblie;
import com.centaline.trans.utils.UiImproveUtil;

@Controller
@RequestMapping(value = "/task/invalidCaseApprove")
public class InvalidCaseApproveController {

	@Autowired
	private InvalidCaseApproveService invalidCaseApproveService;
	
	@Autowired
	private QuickGridService quickGridService;
	
	@RequestMapping(value = "process")
	@ResponseBody
	public String toProcess(HttpServletRequest request,
			HttpServletResponse response, String caseCode, String source) {
		JQGridParam gp = new JQGridParam();
		gp.setPagination(false);
        gp.put("caseCode", caseCode);
        gp.setQueryId("queryLoanlostApproveList");
        Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp);
		return Pages2JSONMoblie.pages2JsonMoblie(pages).toJSONString();
	}

	@RequestMapping(value = "invalidCaseApprove")
	@ResponseBody
	public Boolean invalidCaseApprove(HttpServletRequest request,
			ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String InvalidCaseApprove,
			String InvalidCaseApprove_response) {
		return invalidCaseApproveService.invalidCaseApprove(processInstanceVO,
				loanlostApproveVO, InvalidCaseApprove,
				InvalidCaseApprove_response);
	}

}
