package com.centaline.trans.engine.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aist.uam.permission.remote.UamPermissionService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.utils.UriUtility;

/**
 * 流程引擎 任务处理
 * 
 * @author jjm
 *
 */
@Controller("engine.taskController")
@RequestMapping("/engine/task")
public class TaskController {
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private UamPermissionService uamPermissionService;

	/**
	 * 任务处理跳转
	 * 
	 * @param taskId
	 * @param source
	 * @param caseCode
	 * @param request
	 * @param response
	 * @param attr
	 * @return
	 */
	@RequestMapping("{taskId}/process")
	public String process(@PathVariable String taskId, String source, String caseCode, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes attr) {
		TaskVo task = workFlowManager.getHistoryTask(taskId);
		String instCode = task.getProcessInstanceId();
		String formKey = task.getFormKey();
		if (caseCode == null) {
			StartProcessInstanceVo processInstance = workFlowManager.getHistoryInstances(instCode);
			caseCode = processInstance.getBusinessKey();
		}
		Map<String, String> queryParameters = new HashMap<String, String>();

		queryParameters.put("processInstanceId", instCode);

		queryParameters.put("instCode", instCode);
		queryParameters.put("taskitem", task.getTaskDefinitionKey());
		queryParameters.put("taskId", taskId);
		queryParameters.put("source", source);
		queryParameters.put("caseCode", caseCode);
		Boolean sameSever = false;// 是否同服务器
		if (StringUtils.isNotBlank(formKey)) {
			if (!formKey.contains(":")) {
				sameSever = true;
			}
		} else {
			return "forward:/task/" + task.getTaskDefinitionKey() + UriUtility.getQueryString(queryParameters);
		}

		if (sameSever) {
			request.setAttribute("processInstanceId", instCode);
			request.setAttribute("taskitem", task.getTaskDefinitionKey());
			request.setAttribute("taskId", taskId);
			request.setAttribute("source", source);
			request.setAttribute("caseCode", caseCode);
		}
		if (!sameSever) {
			String[] formKeys = formKey.split(":");
			String absoluteUrl = uamPermissionService.getAppByAppName(formKeys[0]).genAbsoluteUrl();
			return "redirect:" + UriUtility.getQueryString(absoluteUrl + formKeys[1], queryParameters);
		} else {
			return "forward:" + UriUtility.getQueryString(task.getFormKey(), queryParameters);
		}

	}

}
