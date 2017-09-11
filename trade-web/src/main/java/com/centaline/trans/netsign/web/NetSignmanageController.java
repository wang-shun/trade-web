package com.centaline.trans.netsign.web;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
/**
 * 预约网签列表
 * 
 * @author wbwangning
 *
 */
@Controller
@RequestMapping(value = "/netsign")
public class NetSignmanageController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());


	@Autowired
	private UamSessionService uamSessionService;




	/**
	 * 预约网签列表
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "netSignManage")
	public String netSignManage(Model model, HttpServletRequest request) {
		SessionUser currentUser = uamSessionService.getSessionUser();

		return "netsign/netSignList";
	}
	/**
	 * 预约网签列表
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addSignList")
	public String addSignList(Model model, HttpServletRequest request) {
		SessionUser currentUser = uamSessionService.getSessionUser();

		return "netsign/addSignList";
	}
}
