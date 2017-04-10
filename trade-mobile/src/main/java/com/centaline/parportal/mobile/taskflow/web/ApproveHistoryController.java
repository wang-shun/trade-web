package com.centaline.parportal.mobile.taskflow.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;

@Controller
@RequestMapping(value = "/approve")
public class ApproveHistoryController {
	
	@Autowired
	private QuickGridService quickGridService;
	
	@RequestMapping(value = "findApproveList")
	@ResponseBody
	public Object findApproveList(@RequestParam(required = true)String caseCode,
			Integer approveType,@RequestParam(required = true)String processInstanceId) {
		JQGridParam gp = new JQGridParam("queryLoanlostApproveList",false);
		gp.put("caseCode", caseCode).put("approveType", approveType).put("processInstanceId", processInstanceId);
        Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp);
        return pages.getContent();
	}
}
