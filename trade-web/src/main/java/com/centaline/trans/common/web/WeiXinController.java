package com.centaline.trans.common.web;

import javax.servlet.ServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/weixinAuth")
public class WeiXinController {
	
	@RequestMapping(value="authFail")
	public String reminderListSet(Model model, ServletRequest request){
		return "common/weixinAuth/authFail";
	}

}
