package com.centaline.parportal.mobile.engine.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.centaline.trans.common.enums.OldActivitiFormKey;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aist.uam.permission.remote.UamPermissionService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.utils.UriUtility;
import com.opensymphony.module.sitemesh.RequestConstants;

/**
 * 流程引擎 任务处理
 * 
 * @author jjm
 *
 */
@Controller("engine.taskController")
@RequestMapping("/engine/task")
public class TaskController {
	
    private final Logger        LOGGER        = LoggerFactory.getLogger(this.getClass());
    
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
	public String process(@PathVariable String taskId, String source, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes attr) {

		TaskVo task = workFlowManager.getHistoryTask(taskId);
		String instCode = task.getProcessInstanceId();
		String formKey = task.getFormKey();
		String businessKey = "";

		StartProcessInstanceVo processInstance = workFlowManager.getHistoryInstances(instCode);
		businessKey = processInstance.getBusinessKey();

		Map<String, String> queryParameters = new HashMap<String, String>();

		queryParameters.put("processInstanceId", instCode);

		queryParameters.put("instCode", instCode);
		queryParameters.put("taskitem", task.getTaskDefinitionKey());
		queryParameters.put("taskId", taskId);
		queryParameters.put("source", source);
		queryParameters.put("businessKey", businessKey);
		queryParameters.put("caseCode", businessKey);
		
		Boolean sameSever = false;// 是否同服务器
		if (StringUtils.isNotBlank(formKey)) {
			if (!formKey.contains(":")) {
				sameSever = true;
			}
		} else {
			//LOGGER.info("result1===task/"+task.getTaskDefinitionKey() + UriUtility.getQueryString(queryParameters));
			//return "forward:/task/" + task.getTaskDefinitionKey() + UriUtility.getQueryString(queryParameters);
			String uri = "/task/"+ OldActivitiFormKey.from(task.getTaskDefinitionKey()).getFormKey()+"/process"+UriUtility.getQueryString(queryParameters);
			return "forward:"+uri;
		}
		if (sameSever) {
			request.setAttribute("processInstanceId", instCode);
			request.setAttribute("taskitem", task.getTaskDefinitionKey());
			request.setAttribute("taskId", taskId);
			request.setAttribute("source", source);
			request.setAttribute("businessKey", businessKey);
			request.setAttribute("caseCode",  businessKey);//兼容老代码
		}
		if (!sameSever) {
			String[] formKeys = formKey.split(":");
			String absoluteUrl = uamPermissionService.getAppByAppName(formKeys[0]).genAbsoluteUrl();
			LOGGER.info("result2==="+UriUtility.getQueryString(absoluteUrl + formKeys[1], queryParameters));
			return "redirect:" + UriUtility.getQueryString(absoluteUrl + formKeys[1], queryParameters);
		} else {
			String decorator = getDecorator(task.getFormKey());
			if (!StringUtils.isBlank(decorator)) {
				request.setAttribute(RequestConstants.DECORATOR, decorator);
			}	
			LOGGER.info("result3==="+UriUtility.getQueryString(task.getFormKey(), queryParameters));
			return "forward:" + UriUtility.getQueryString(task.getFormKey(), queryParameters);
	
		}

	}

	/**
	 * 获得模板参数
	 * 
	 * @param formKey
	 * @return
	 */
	private String getDecorator(String formKey) {
		if (!StringUtils.isBlank(formKey)) {
			Map<String, String> maps = UriUtility.parseQueryParameters(UriUtility.extractQueryString(formKey), null);
			if (maps.containsKey("__decorator")){
				return maps.get("__decorator");
			}
		}
		return null;
	}

}
