package com.centaline.trans.signroom.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.signroom.entity.ResFlowup;
import com.centaline.trans.signroom.entity.TradeCenter;
import com.centaline.trans.signroom.entity.TradeCenterSchedule;
import com.centaline.trans.signroom.service.ResFlowupService;
import com.centaline.trans.signroom.service.ReservationService;
import com.centaline.trans.signroom.service.RmSignRoomService;
import com.centaline.trans.signroom.vo.CanceReservionVo;
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
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

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
	 * 判断上一个时间段该房间是否签退
	 * 
	 * @param model
	 * @param request
	 * @return 返回true,说明上一个时间段该房间已经签退;返回false,说明上一个时间段该房间还没签退。
	 */
	@RequestMapping(value = "isOvertimeUse")
	@ResponseBody
	public String isOvertimeUse(Model model, HttpServletRequest request) {
		String scheduleId = request.getParameter("scheduleId");
		Long roomId = Long.parseLong(request.getParameter("roomId"));

		ReservationVo reservationVo = new ReservationVo();
		reservationVo.setScheduleId(scheduleId);
		reservationVo.setRoomId(roomId);

		String result = reservationService.isOvertimeUse(reservationVo);

		return result;
	}

	/**
	 * 提前使用
	 * 
	 * @param model
	 * @param request
	 * @return 返回true,操作成功;返回false,操作失败。
	 */
	@RequestMapping(value = "startUseInAdvance")
	@ResponseBody
	public String startUseInAdvance(Model model, HttpServletRequest request) {
		String result = "true";
		String resId = request.getParameter("resId");
		Long roomId = Long.parseLong(request.getParameter("roomId"));

		ReservationVo reservationVo = new ReservationVo();
		reservationVo.setResId(resId);
		reservationVo.setRoomId(roomId);

		try {
			reservationService.startUseInAdvance(reservationVo);
		} catch (Exception e) {
			result = "false";
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 开始使用之前判断当前时间点是否有这个房号的房间
	 * 
	 * @param model
	 * @param request
	 * @return 返回true,操作成功;返回false,操作失败。
	 */
	@RequestMapping(value = "isHasFreeRoomByCurrentTimeAndRoomNo")
	@ResponseBody
	public String isHasFreeRoomByCurrentTimeAndRoomNo(Model model,
			HttpServletRequest request) {
		Long roomId = Long.parseLong(request.getParameter("roomId"));

		String result = reservationService
				.isHasFreeRoomByCurrentTimeAndRoomNo(roomId);

		return result;
	}

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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dutyDate", new SimpleDateFormat("yyyy-MM-dd").format(Calendar
				.getInstance().getTime()));
		SessionUser user = uamSessionService.getSessionUser();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("districtId", user.getServiceDepId());
		List<TradeCenter> tradeCenters = rmSignRoomService
				.getTradeCenters(param);// 获取 交易中心信息
		List<TradeCenterSchedule> tcs = new ArrayList<TradeCenterSchedule>();
		for (TradeCenter tc : tradeCenters) {// 查询该贵宾服务部当日值班人员
			map.put("centerId", tc.getPkid());
			tcs.addAll(rmSignRoomService.queryTradeCenterSchedules(map));
		}
		model.addAttribute("tcs", tcs);
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

		int count = 0;
		if ("startUse".equals(flag)) {
			count = reservationService.startUse(resId); // 开始使用
		} else if ("endUse".equals(flag)) {
			count = reservationService.endUse(resId); // 结束使用
		}

		StartAndEndUseResult startAndEndUseResult = new StartAndEndUseResult();
		startAndEndUseResult.setResult(count > 0 ? "true" : "false");
		// startAndEndUseResult.setOperateDateTime(operateDateTime);

		return startAndEndUseResult;
	}
	
	/**
	 * 签约室排班
	 * @return
	 */
	@RequestMapping("/canceReservation")
	@ResponseBody
	public AjaxResponse<T> canceReservation(CanceReservionVo canceReservionVo){
		AjaxResponse<T> response = new AjaxResponse<T>();
		try{
			rmSignRoomService.canceReservation(canceReservionVo);
			response.setCode("400");
			response.setMessage("签约室预约取消成功！");
			response.setSuccess(true);
		}catch(Exception e){
			logger.error("签约室预约取消失败:", e);
			response.setCode("500");
			response.setMessage("签约室预约取消失败！");
			response.setSuccess(false);
		}
		return response;
	}
}
