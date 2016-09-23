package com.centaline.trans.signroom.web;

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
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.signroom.entity.Reservation;
import com.centaline.trans.signroom.entity.TradeCenter;
import com.centaline.trans.signroom.service.ReservationService;
import com.centaline.trans.signroom.service.TradeCenterService;
import com.centaline.trans.signroom.vo.FreeRoomInfo;
import com.centaline.trans.signroom.vo.PropertyAddrInfoVo;
import com.centaline.trans.signroom.vo.PropertyAddrSearchVo;
import com.centaline.trans.signroom.vo.ReservationInfo;
import com.centaline.trans.signroom.vo.ReservationSearchVo;
import com.centaline.trans.signroom.vo.ReservationVo;
import com.centaline.trans.signroom.vo.TransactItemVo;

/**
 * 预约取号信息controller
 * 
 * @author yinjf2
 *
 */
@Controller
@RequestMapping(value = "mobile/reservation")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private UamSessionService uamSessionService;

	@Autowired
	private TradeCenterService tradeCenterService;

	@Autowired
	private UamUserOrgService uamUserOrgService;

	@Autowired
	private ToPropertyInfoService toPropertyInfoService;

	/**
	 * 签约室预约列表
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "list")
	public String list(Model model, HttpServletRequest request) {
		SessionUser currentUser = uamSessionService.getSessionUser();
		String orgId = reservationService.getOrgIdByGrpcode(currentUser
				.getBusigrpId());

		/*
		 * if ("A433968FA56D4D8596C3C709AFDF77C4".equals(orgId)) {
		 * request.setAttribute("orgId", orgId); } else { Org org =
		 * uamUserOrgService.getOrgById(orgId);
		 * 
		 * request.setAttribute("orgId", org.getParentId()); }
		 */

		request.setAttribute("orgId", "6a84979158b942b78a8a5921cc30b8c3");

		return "mobile/signroom/reservation/list";
	}

	/**
	 * ajax获取签约室签约列表
	 * 
	 * @return 签约室签约列表
	 */
	@RequestMapping(value = "getSignRoomInfoList")
	@ResponseBody
	public List<ReservationInfo> getSignRoomInfoList(
			ReservationSearchVo reservationSearchVo) {

		List<ReservationInfo> reservationInfoList = reservationService
				.getSignRoomInfoListByCondition(reservationSearchVo);

		return reservationInfoList;
	}

	/**
	 * 进入预约取号页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bespeakUI")
	public String bespeakUI(Model model, HttpServletRequest request) {
		SessionUser sessionUser = uamSessionService.getSessionUser();

		String orgId = request.getParameter("orgId");
		String selDate = request.getParameter("inputSelDate");
		String bespeakTime = request.getParameter("inputBespeakTime");
		List<TransactItemVo> transactItemVoList = reservationService
				.getTransactItemList();

		request.setAttribute("transactItemVoList", transactItemVoList);
		request.setAttribute("orgId", orgId);
		request.setAttribute("selDate", selDate);
		request.setAttribute("bespeakTime", bespeakTime);
		// request.setAttribute("agentCode", sessionUser.getId());
		request.setAttribute("agentCode", "E39F5661B6614F968F27E7BD24BA324A");

		return "mobile/signroom/reservation/bespeak";
	}

	/**
	 * 预约取号保存
	 * 
	 * @param reservationVo
	 *            预约取号前台传的参数对象
	 * @return 返回true,说明保存成功;返回false,保存失败。
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public FreeRoomInfo save(ReservationVo reservationVo) {
		FreeRoomInfo freeRoomInfo = reservationService
				.getFreeRoomByCondition(reservationVo); // 获取闲置的房间信息

		if (freeRoomInfo == null) {
			freeRoomInfo = new FreeRoomInfo();
			freeRoomInfo.setIsSuccess("noRoom");

			return freeRoomInfo;
		}

		SessionUser currentUser = uamSessionService.getSessionUser();

		Reservation reservation = new Reservation();
		reservation.setResNo("20160918001"); // 预约编号是根据某一规则自动生成，现在是为了测试先写死
		reservation.setResType(reservationVo.getResType());
		reservation.setResPersonId(reservationVo.getResPersonId());

		String orgId = reservationService.getOrgIdByGrpcode(currentUser
				.getBusigrpId());

		// reservation.setResPersonOrgId(orgId); //TODO
		reservation.setResPersonOrgId("6a84979158b942b78a8a5921cc30b8c3");
		reservation.setResStatus("0");
		reservation.setScheduleId(reservationVo.getScheduleId());
		reservation.setCaseCode(reservationVo.getCaseCode());
		reservation.setPropertyAddress(reservationVo.getPropertyAddress());
		reservation.setNumberOfParticipants(reservationVo
				.getNumberOfParticipants());
		reservation.setTransactItemCode(reservationVo.getTransactItemCode());
		reservation
				.setSpecialRequirement(reservationVo.getSpecialRequirement());

		reservation.setCreateTime(new Date());
		reservation.setCreateBy(currentUser.getId());
		reservation.setUpdateTime(new Date());
		reservation.setUpdateBy(currentUser.getId());

		return reservationService.saveReservation(reservation, reservationVo);
	}

	/**
	 * 进入我的预约页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "myReservationList")
	public String myReservationList(Model model, HttpServletRequest request) {
		SessionUser currentUser = uamSessionService.getSessionUser();

		// request.setAttribute("agentCode", currentUser.getId());
		request.setAttribute("agentCode", "E39F5661B6614F968F27E7BD24BA324A");

		return "mobile/signroom/reservation/myReservationList";
	}

	/**
	 * ajax获取交易中心列表信息
	 * 
	 * @return 交易中心列表信息
	 */
	@RequestMapping(value = "getTradeCenterList")
	@ResponseBody
	public List<TradeCenter> getTradeCenterList() {

		return tradeCenterService.getTradeCenterList();
	}

	/**
	 * ajax获取预约时间段信息
	 * 
	 * @return 预约时间段信息
	 */
	@RequestMapping(value = "getBespeakTime")
	@ResponseBody
	public List<String> getBespeakTime() {

		return reservationService.getBespeakTime();
	}

	/**
	 * ajax根据用户输入的产证地址信息获取相对应的产证地址列表,用于前端界面input autocomplete自动填充
	 * 
	 * @return 产证地址信息列表
	 */
	@RequestMapping(value = "getPropertyAddressList")
	@ResponseBody
	public List<PropertyAddrInfoVo> getPropertyAddressList(
			HttpServletRequest request) {
		String agentCode = request.getParameter("agentCode");

		PropertyAddrSearchVo propertyAddrSearchVo = new PropertyAddrSearchVo();
		propertyAddrSearchVo.setAgentCode(agentCode);

		List<PropertyAddrInfoVo> propertyAddrInfoVoList = toPropertyInfoService
				.getPropertyInfoListByInputValue(propertyAddrSearchVo);

		return propertyAddrInfoVoList;
	}

	/**
	 * ajax根据产证地址获取对应的案件编号
	 * 
	 * @return 案件编号
	 */
	@RequestMapping(value = "getCaseCodeByPropertyAddr")
	@ResponseBody
	public String getCaseCodeByPropertyAddr(HttpServletRequest request) {
		String propertyAddress = request.getParameter("propertyAddress");

		return toPropertyInfoService.getCaseCodeByPropertyAddr(propertyAddress);
	}

	/**
	 * ajax取消预约
	 * 
	 * @return 如果返回true,预约成功;返回false,预约失败。
	 */
	@RequestMapping(value = "cancelReservation")
	@ResponseBody
	public String cancelReservation(HttpServletRequest request) {
		Long resId = Long.parseLong(request.getParameter("resId"));

		return reservationService.cancelReservation(resId);
	}

}
