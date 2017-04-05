package com.centaline.trans.taskList.web;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.common.enums.LampEnum;

@Controller
@RequestMapping(value="/task")
public class TaskOfGroupController {
	@Autowired
	private UamSessionService uamSessionService;
	@RequestMapping("toTaskOfGroupList")
	private String toTaskOfGroupList(ServletRequest request){
		String[] lamps = LampEnum.getCodes();
		request.setAttribute("Lamp1", lamps[0]);
		request.setAttribute("Lamp2", lamps[1]);
		request.setAttribute("Lamp3", lamps[2]);
		SessionUser suser= uamSessionService.getSessionUser();
		request.setAttribute("serviceDepId", suser.getServiceDepId());
		return "/task/toTaskOfGroupList2";
	}
}
