package com.centaline.parportal.mobile.taskflow.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.task.service.InvalidCaseApproveService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

@Controller
@RequestMapping(value = "/task/invalidCaseApprove")
public class InvalidCaseApproveController {

    private final static Logger LOGGER = LoggerFactory.getLogger(InvalidCaseApproveController.class);

	@Autowired
	private InvalidCaseApproveService invalidCaseApproveService;
	
	@Autowired
	private UamSessionService uamSessionService;
	
	@RequestMapping(value = "process")
	@ResponseBody
	public  JSONObject toProcess(HttpServletRequest request,
			HttpServletResponse response, String caseCode, String source) {
        
        JSONObject json = new JSONObject();
        Map<String,String[]> map = request.getParameterMap();
        for (Map.Entry<String,String[]> e : map.entrySet()) {
        	if("token".equals(e.getKey()) || e.getValue() == null || e.getValue().length == 0) {
        		continue;
        	}
        	json.put(e.getKey(), e.getValue()[0]);
		}
		SessionUser user = uamSessionService.getSessionUser();
		json.put("approveType", "0");
		json.put("operator", user != null ? user.getId() : "");
        return json;
	}

	@RequestMapping(value = "invalidCaseApprove")
	@ResponseBody
	public Object invalidCaseApprove(HttpServletRequest request,
			ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String InvalidCaseApprove,
			String InvalidCaseApprove_response) {
		
		Boolean result = false;
		try {
			result = invalidCaseApproveService.invalidCaseApprove(processInstanceVO, loanlostApproveVO,
					InvalidCaseApprove, InvalidCaseApprove_response);
		}catch (Exception e) {
			result = false;
			LOGGER.error("操作失败",e);
		}
		AjaxResponse<?> response = new AjaxResponse<>();
		response.setSuccess(result);
		String msg = result ? "操作成功!" : "操作失败!";
		response.setMessage(msg);
		return response;
	}

}
