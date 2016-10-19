package com.centaline.trans.material.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;


@Controller
@RequestMapping(value="/material")
public class MaterialManagementController {
	
	@Autowired
	private UamSessionService uamSessionService;
	//列表页面
	@RequestMapping("materialList")
	public String spvList(HttpServletRequest request){
		
		SessionUser currentUser = uamSessionService.getSessionUser();
		String currentDeptId = currentUser.getServiceDepId();
		
	
		request.setAttribute("currentDeptId", currentDeptId);
		return "material/materialList";
	}
}
