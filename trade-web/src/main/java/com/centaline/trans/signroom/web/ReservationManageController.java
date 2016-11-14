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
import com.centaline.trans.signroom.entity.Reservation;
import com.centaline.trans.signroom.service.ResFlowupService;
import com.centaline.trans.signroom.service.ReservationService;
import com.centaline.trans.signroom.service.RmSignRoomService;
import com.centaline.trans.signroom.vo.ChangeRoomResult;
import com.centaline.trans.signroom.vo.ReservationVo;
import com.centaline.trans.signroom.vo.SaveResFlowupResult;
import com.centaline.trans.signroom.vo.SignroomInfo;
import com.centaline.trans.signroom.vo.StartAndEndUseResult;

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

	@Autowired
	private RmSignRoomService rmSignRoomService;

	/**
	 * 变更签约室----更换签约室保存
	 * 
	 * @param model
	 * @param request
	 * @return 返回true,操作成功;返回false,操作失败。
	 */
	@RequestMapping(value = "changeRoom")
	@ResponseBody
	public ChangeRoomResult changeRoom(Model model, HttpServletRequest request) {
		String isSuccess = "true";

		String resId = request.getParameter("resId");
		String scheduleId = request.getParameter("scheduleId");
		String flag = request.getParameter("flag");

		ReservationVo reservationVo = new ReservationVo();
		reservationVo.setResId(resId);
		reservationVo.setScheduleId(scheduleId);
		reservationVo.setFlag(flag);

		ChangeRoomResult changeRoomResult = null;
		try {
			changeRoomResult = reservationService.changeRoom(reservationVo);
		} catch (Exception e) {
			isSuccess = "false";
			e.printStackTrace();
		}

		changeRoomResult.setResult(isSuccess);

		return changeRoomResult;
	}

	/**
	 * 变更签约室----获取可用的房间信息列表
	 * 
	 * @param model
	 * @param request
	 * @return 返回true,操作成功;返回false,操作失败。
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
		boolean isCurrenDayDuty = rmSignRoomService.isCurrenDayDuty();// 是否当日值班

		String distinctId = "";
		// 如果当前用户属于组级别的
		if ("yucui_team".equals(currentUser.getServiceDepHierarchy())) {
			Org org = uamUserOrgService.getOrgById(currentUser
					.getServiceCompanyId());

			distinctId = org.getParentId();
			// 如果当前用户属于区级别的
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
		request.setAttribute("isCurrenDayDuty", isCurrenDayDuty);

		return "signroom/signinglist";
	}

	/**
	 * 保存最新跟进信息
	 * 
	 * @param model
	 * @param request
	 * @return 返回true,操作成功;返回false,操作失败。
	 */
	@RequestMapping(value = "saveResFlowup")
	@ResponseBody
	public SaveResFlowupResult saveResFlowup(Model model,
			HttpServletRequest request) {
		SessionUser currentUser = uamSessionService.getSessionUser();

		Long resId = Long.parseLong(request.getParameter("resId"));
		String comment = request.getParameter("comment");
		Date createDateTime = new Date();

		ResFlowup resFlowup = new ResFlowup();
		resFlowup.setResId(resId);
		resFlowup.setComment(comment);
		resFlowup.setCreateTime(createDateTime);
		resFlowup.setCreateBy(currentUser.getId());
		resFlowup.setUpdateBy(currentUser.getId());
		resFlowup.setUpdateTime(createDateTime);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SaveResFlowupResult saveResFlowupResult = new SaveResFlowupResult();
		saveResFlowupResult.setCreateDateTime(sdf.format(createDateTime));
		saveResFlowupResult
				.setResult(resFlowupService.saveResFlowup(resFlowup) > 0 ? "true"
						: "false");
		saveResFlowupResult.setRealName(currentUser.getRealName());

		return saveResFlowupResult;
	}

	/**
	 * 开始使用及结束使用
	 * 
	 * @param model
	 * @param request
	 * @return 返回true,操作成功;返回false,操作失败。
	 */
	@RequestMapping(value = "startAndEndUse")
	@ResponseBody
	public StartAndEndUseResult startAndEndUse(Model model,
			HttpServletRequest request) {
		String flag = request.getParameter("flag");
		Long resId = Long.parseLong(request.getParameter("resId"));
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

		int count = 0;
		String operateDateTime = "";
		if ("startUse".equals(flag)) {
			count = reservationService.startUse(resId); // 开始使用

			Reservation reservation = reservationService
					.getReservationById(resId);
			operateDateTime = sdf.format(reservation.getCheckInTime());
		} else if ("endUse".equals(flag)) {
			count = reservationService.endUse(resId); // 结束使用

			Reservation reservation = reservationService
					.getReservationById(resId);
			operateDateTime = sdf.format(reservation.getCheckedOutTime());
		}

		StartAndEndUseResult startAndEndUseResult = new StartAndEndUseResult();
		startAndEndUseResult.setResult(count > 0 ? "true" : "false");
		startAndEndUseResult.setOperateDateTime(operateDateTime);

		return startAndEndUseResult;
	}
}
