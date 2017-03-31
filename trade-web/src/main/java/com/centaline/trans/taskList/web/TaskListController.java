package com.centaline.trans.taskList.web;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.common.enums.LampEnum;
import com.centaline.trans.engine.service.WorkFlowManager;

/**
 * 
 * <p>Project: 待办任务</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2015 </p>
 * @author wanggh</a>
 */
@Controller
@RequestMapping(value="/task")
public class TaskListController {
	
	@Autowired(required=true)
	UamSessionService uamSessionService;
	@Autowired(required=true)
	WorkFlowManager workFlowManager;
	/**
	 * 初始化
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="myTaskList")
	public String myTaskList(Model model, ServletRequest request){
		//TODO
		SessionUser user = uamSessionService.getSessionUser();
		String[] lamps = LampEnum.getCodes();
		request.setAttribute("userId", user.getId());
		request.setAttribute("Lamp1", lamps[0]);
		request.setAttribute("Lamp2", lamps[1]);
		request.setAttribute("Lamp3", lamps[2]);
		return "task/mytask_list2";
	}
	/**
	 * 初始化
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="mobile/myTaskList")
	public String myTaskList_m(Model model, ServletRequest request){
		//TODO

		String[] lamps = LampEnum.getCodes();
		request.setAttribute("Lamp1", lamps[0]);
		request.setAttribute("Lamp2", lamps[1]);
		request.setAttribute("Lamp3", lamps[2]);
		return "mobile/task/m_mytask_list";
	}

}
