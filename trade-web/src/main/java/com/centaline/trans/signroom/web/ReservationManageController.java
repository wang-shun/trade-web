package com.centaline.trans.signroom.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.signroom.entity.ResFlowup;
import com.centaline.trans.signroom.service.ResFlowupService;
import com.centaline.trans.signroom.service.ReservationService;

/**
 * 预约取号后台controller
 * 
 * @author yinjf2
 *
 */
@Controller
@RequestMapping(value = "/reservation")
public class ReservationManageController {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private ResFlowupService resFlowupService;

	@Autowired
	private UamSessionService uamSessionService;

	/**
	 * 签约室列表
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "list")
	public String list(Model model, HttpServletRequest request) {

		return "signroom/signinglist";
	}

	@RequestMapping(value = "saveResFlowup")
	@ResponseBody
	public String saveResFlowup(Model model, HttpServletRequest request) {
		SessionUser currentUser = uamSessionService.getSessionUser();

		Long resId = Long.parseLong(request.getParameter("resId"));
		String comment = request.getParameter("comment");

		ResFlowup resFlowup = new ResFlowup();
		resFlowup.setResId(resId);
		resFlowup.setComment(comment);
		resFlowup.setCreateTime(new Date());
		resFlowup.setCreateBy(currentUser.getId());
		resFlowup.setUpdateBy(currentUser.getId());
		resFlowup.setUpdateTime(new Date());

		return resFlowupService.saveResFlowup(resFlowup) > 0 ? "true" : "false";
	}

	@RequestMapping(value = "startAndEndUse")
	@ResponseBody
	public String startAndEndUse(Model model, HttpServletRequest request) {
		String flag = request.getParameter("flag");
		Long resId = Long.parseLong(request.getParameter("resId"));

		int count = 0;
		if ("startUse".equals(flag)) {
			count = reservationService.startUse(resId); // 开始使用
		} else if ("endUse".equals(flag)) {
			count = reservationService.endUse(resId); // 结束使用
		}

		return count > 0 ? "true" : "false";
	}
}
