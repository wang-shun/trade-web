package com.centaline.trans.taskList.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.centaline.trans.common.entity.ToAccesoryList;
import com.centaline.trans.common.entity.ToAttachment;
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

		String[] lamps = LampEnum.getCodes();
		request.setAttribute("Lamp1", lamps[0]);
		request.setAttribute("Lamp2", lamps[1]);
		request.setAttribute("Lamp3", lamps[2]);
		return "task/mytask_list";
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
