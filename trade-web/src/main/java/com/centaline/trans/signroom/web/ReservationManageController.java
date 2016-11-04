package com.centaline.trans.signroom.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.signroom.entity.ResFlowup;
import com.centaline.trans.signroom.service.ResFlowupService;
import com.centaline.trans.signroom.service.ReservationService;
import com.centaline.trans.signroom.vo.ReservationVo;
import com.centaline.trans.signroom.vo.SignroomInfo;

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

	@Autowired
	private UamUserOrgService uamUserOrgService;

	/**
	 * 变更签约室----更换签约室保存
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "changeRoom")
	@ResponseBody
	public String changeRoom(Model model, HttpServletRequest request) {
		String isSuccess = "true";

		String resId = request.getParameter("resId");
		String scheduleId = request.getParameter("scheduleId");
		String flag = request.getParameter("flag");

		ReservationVo reservationVo = new ReservationVo();
		reservationVo.setResId(resId);
		reservationVo.setScheduleId(scheduleId);
		reservationVo.setFlag(flag);

		try {
			reservationService.changeRoom(reservationVo);
		} catch (Exception e) {
			isSuccess = "false";
			e.printStackTrace();
		}

		return isSuccess;
	}

	/**
	 * 变更签约室----获取可用的房间信息列表
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getUseableSignRoomList")
	@ResponseBody
	public List<SignroomInfo> getUseableSignRoomList(Model model,
			HttpServletRequest request) {

		Long tradeCenterId = Long.parseLong(request
				.getParameter("tradeCenterId"));
		String startDate = request.getParameter("resStartTime");
		String endDate = request.getParameter("resEndTime");

		ReservationVo reservationVo = new ReservationVo();
		reservationVo.setStartDate(startDate);
		reservationVo.setEndDate(endDate);
		reservationVo.setTradeCenterId(tradeCenterId);

		List<SignroomInfo> signroomInfoList = reservationService
				.getUseableSignRoomList(reservationVo);

		return signroomInfoList;

	}

	/**
	 * 签约室列表
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "list")
	public String list(Model model, HttpServletRequest request) {
		SessionUser currentUser = uamSessionService.getSessionUser();

		String distinctId = "";
		if ("yucui_team".equals(currentUser.getServiceDepHierarchy())) {
			Org org = uamUserOrgService.getOrgById(currentUser
					.getServiceCompanyId());

			distinctId = org.getParentId();
		} else if ("yucui_district"
				.equals(currentUser.getServiceDepHierarchy())) {
			distinctId = currentUser.getServiceCompanyId();
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String resPersonId = request.getParameter("resPersonId");
		String resPeopleId = request.getParameter("resPeopleId");
		String resNo = request.getParameter("resNo");
		String mobile = request.getParameter("mobile");
		String startDateTime = request.getParameter("startDateTime");
		String endDateTime = request.getParameter("endDateTime");
		String resTime = request.getParameter("resTime");
		String resStatus = request.getParameter("resStatus");
		String flag = request.getParameter("flag");

		if (flag == null) {
			if (startDateTime == null || "".equals(startDateTime)) {
				startDateTime = sdf.format(new Date());
			}

			if (endDateTime == null || "".equals(endDateTime)) {
				endDateTime = sdf.format(new Date());
			}
		}

		request.setAttribute("resPersonId", resPersonId);
		request.setAttribute("resPeopleId", resPeopleId);
		request.setAttribute("resNo", resNo);
		request.setAttribute("mobile", mobile);
		request.setAttribute("startDateTime", startDateTime);
		request.setAttribute("endDateTime", endDateTime);
		request.setAttribute("resTime", resTime);
		request.setAttribute("resStatus", resStatus);
		request.setAttribute("distinctId", distinctId);

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
