package com.centaline.trans.signroom.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;

@Controller
@RequestMapping(value = "weixin/signroom")
public class SignRoomWeiXinController {
	
	@Autowired
	private UamSessionService uamSessionService;
	
	@RequestMapping("/test")
	public String signRoomAllotList(Model model){
		SessionUser user= uamSessionService.getSessionUser();

		model.addAttribute("username", user.getUsername());
		
		return "/signroom/test";
	}

}
