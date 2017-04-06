package com.centaline.parportal.mobile.taskList.web;

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
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.task.service.InvalidCaseApproveService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

@Controller
@RequestMapping(value = "/task/invalidCaseApprove")
public class InvalidCaseApproveController {

	@Autowired
	private InvalidCaseApproveService invalidCaseApproveService;
	
	@Autowired
	private QuickGridService quickGridService;
	
	@Autowired
	private UamSessionService uamSessionService;
	
	@RequestMapping(value = "process")
	@ResponseBody
	public String toProcess(HttpServletRequest request,
			HttpServletResponse response, String caseCode, String source) {
		
		JQGridParam gp = new JQGridParam();
		gp.setPagination(false);
        gp.put("caseCode", caseCode);
        gp.setQueryId("queryLoanlostApproveList");
        Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp);
        
        JSONObject json = new JSONObject();
        Map<String,String[]> map = request.getParameterMap();
        for (Map.Entry<String,String[]> e : map.entrySet()) {
        	if("token".equals(e.getKey()) || e.getValue() == null || e.getValue().length == 0) {
        		continue;
        	}
        	json.put(e.getKey(), e.getValue()[0]);
		}
        json.put("approveList", pages.getContent());
        
		SessionUser user = uamSessionService.getSessionUser();
		json.put("approveType", "0");
		json.put("operator", user != null ? user.getId() : "");
        return json.toJSONString();
	}

	@RequestMapping(value = "invalidCaseApprove")
	@ResponseBody
	public JSONObject invalidCaseApprove(HttpServletRequest request,
			ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String InvalidCaseApprove,
			String InvalidCaseApprove_response) {
		Boolean result = invalidCaseApproveService.invalidCaseApprove(processInstanceVO,
				loanlostApproveVO, InvalidCaseApprove,
				InvalidCaseApprove_response);
		JSONObject json = new JSONObject();
		json.put("isSuccess", result);
		String msg = result ? "操作成功!" : "操作失败!";
		json.put("msg", msg);
		return json;
	}

}
