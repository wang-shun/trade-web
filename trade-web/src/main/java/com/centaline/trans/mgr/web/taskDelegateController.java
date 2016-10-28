/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.mgr.web;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.web.vo.DatagridVO;
import com.aist.uam.auth.remote.UamSessionService;
import com.centaline.trans.extint.web.vo.ResponseVo;
import com.centaline.trans.task.service.TsTaskDelegateService;
import com.centaline.trans.task.vo.UserPagebleVO;

/**
 * 
 * @author natsucheng
 * @version $Id: taskDelegateController.java, v 0.1 2015年8月13日 下午3:54:07
 *          natsucheng Exp $
 */

@Controller
@RequestMapping(value = "/manage")
public class taskDelegateController {
	@Autowired
	private TsTaskDelegateService tsTaskDelegateService;
	@Autowired
	private UamSessionService uamSesstionService;

	@RequestMapping(value = "taskDelegate")
	public String delegatePageShow(Model model, ServletRequest request) {
		String owner = uamSesstionService.getSessionUser().getUsername();
		request.setAttribute("owner", owner);
		return "manage/taskDelegate";
	}

	@RequestMapping(value = "doTaskDelegate/{assignee}")
	@ResponseBody
	public ResponseVo doTaskDelegate(
			@PathVariable(value = "assignee") String assignee) {
		ResponseVo result = new ResponseVo();
		result.setSc("0");
		try {
			String owner = uamSesstionService.getSessionUser().getUsername();
			tsTaskDelegateService.taskDelegate(owner, assignee);
		} catch (Exception e) {
			result.setSc("-1");
			e.printStackTrace();
		}
		return result;
	}
	
	
	//todo  3986

	@RequestMapping(value = "listUser")
	@ResponseBody
	public DatagridVO listUser(UserPagebleVO page) {
		return tsTaskDelegateService.listUser(page);
	}

	
	
	
	@RequestMapping(value = "doTaskDelegateClose/{pkid}")
	@ResponseBody
	public ResponseVo doTaskDelegateClose(
			@PathVariable(value = "pkid") Long pkid) {
		ResponseVo result = new ResponseVo();
		result.setSc("0");
		try {
			String owner = uamSesstionService.getSessionUser().getUsername();
			tsTaskDelegateService.closeTaskDelegate(owner, pkid);
		} catch (Exception e) {
			result.setSc("-1");
			e.printStackTrace();
		}
		return result;
	}

}
