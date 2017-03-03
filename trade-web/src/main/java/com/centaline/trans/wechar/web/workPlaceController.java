package com.centaline.trans.wechar.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;

@Controller
@RequestMapping({"/weixin/work","/anon/work"})
public class workPlaceController {

	@Autowired
	private UamSessionService uamSesstionService;

	@RequestMapping("workPlace")
	public String toApply(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		SessionUser user = uamSesstionService.getSessionUser();
		request.setAttribute("user", user);

		return "mobile/searchWorkPlace/workPlace";
	}

}