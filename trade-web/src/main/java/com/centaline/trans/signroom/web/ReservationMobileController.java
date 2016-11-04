package com.centaline.trans.signroom.web;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.map.HashedMap;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.signroom.entity.Reservation;
import com.centaline.trans.signroom.entity.TradeCenter;
import com.centaline.trans.signroom.service.ReservationService;
import com.centaline.trans.signroom.service.RmSignRoomOrgMappingService;
import com.centaline.trans.signroom.service.TradeCenterService;
import com.centaline.trans.signroom.vo.FreeRoomInfo;
import com.centaline.trans.signroom.vo.PropertyAddrInfoVo;
import com.centaline.trans.signroom.vo.PropertyAddrSearchVo;
import com.centaline.trans.signroom.vo.ReservationInfo;
import com.centaline.trans.signroom.vo.ReservationSearchVo;
import com.centaline.trans.signroom.vo.ReservationVo;
import com.centaline.trans.signroom.vo.SignroomCondition;
import com.centaline.trans.signroom.vo.SignroomInfo;
import com.centaline.trans.signroom.vo.TransactItemVo;
import com.centaline.trans.utils.DateUtil;

/**
 * 预约取号微信端controller
 * 
 * @author yinjf2
 *
 */
@Controller
@RequestMapping(value = "weixin/signroom")
public class ReservationMobileController {

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

	@Autowired
	private UamBasedataService uamBasedataService;

	@Autowired
	private RmSignRoomOrgMappingService rmSignRoomOrgMappingService;

	/**
	 * 获取该用户两周内剩余预约次数
	 * 
	 * @return 剩余预约次数
	 */
	@RequestMapping(value = "getRemainBespeakNumber")
	@ResponseBody
	public int getRemainBespeakNumber() {
		SessionUser currentUser = uamSessionService.getSessionUser();

		int bespeakNumber = Integer.parseInt(uamBasedataService.getParam(
				"SIGN_ROOM_MANAGE", "SIGN_ROOM_ BESPEAK_NUMBER"));

		int remainBespeakNumber = bespeakNumber
				- reservationService.getUsedBespeakNumber(currentUser.getId());

		if (remainBespeakNumber <= 0) {
			remainBespeakNumber = 0;
		}

		return remainBespeakNumber;
	}

	/**
	 * 判断该用户是否有预约次数、是否有符合条件的房间、是否有小房间、无房间
	 * 
	 * @param reservationVo
	 *            前台参数
	 * @return 返回noBespeakNum,则没预约次数;返回hasMinRoom,则有小一点的房间;返回noRoom,则无房间。
	 */
	public String isValidPass(ReservationVo reservationVo) {
		String message = "isPass";

		// 判断当前用户是否有可使用的预约次数
		int remainBespeakNumber = getRemainBespeakNumber();

		if (remainBespeakNumber == 0) {
			message = "noBespeakNum";

			return message;
		}

		// 如果有预约次数,则按照正常预约流程走
		if ("normal".equals(reservationVo.getFlag())) {

			FreeRoomInfo freeRoomInfo = reservationService
					.getMatchFreeRoomByCondition(reservationVo); // 获取是否有符合条件的签约室房间信息

			// 如果没有符合条件的房间
			if (freeRoomInfo == null) {

				// 再查询是否有小一点的房间
				freeRoomInfo = reservationService
						.getMinFreeRoomByCondition(reservationVo);

				// 如果小房间还是没有,那就提示用户没有可用房间
				if (freeRoomInfo == null) {
					message = "noRoom";
				} else {
					message = "hasMinRoom";
				}

				return message;
			}
		}

		return message;
	}

	/**
	 * 预约取号保存
	 * 
	 * @param reservationVo
	 *            预约取号前台传的参数对象
	 * @return 返回true,说明保存成功;返回false,保存失败。
	 * @throws ParseException
	 */
	@RequestMapping(value = "saveReservation")
	@ResponseBody
	public FreeRoomInfo saveReservation(ReservationVo reservationVo) {
		String message = isValidPass(reservationVo);

		FreeRoomInfo freeRoomInfo = null;
		if (!"isPass".equals(message)) {
			freeRoomInfo = new FreeRoomInfo();
			freeRoomInfo.setIsSuccess(message);

			return freeRoomInfo;
		}

		String isSuccss = "true";
		SessionUser currentUser = uamSessionService.getSessionUser();

		String dateStr = DateUtil.getFormatDate(new Date(), "yyMMdd");
		String resNo = uamBasedataService.nextSeqVal("QYSYY_CODE", dateStr);

		Reservation reservation = new Reservation();
		reservation.setResNo(resNo);
		reservation.setResType(reservationVo.getResType());
		reservation.setResPersonId(currentUser.getId());

		Map<String, Object> map = new HashedMap<String, Object>();
		map.put("pkid", reservationVo.getTradeCenterId());
		TradeCenter tradeCenter = tradeCenterService.getTradeCenter(map);

		reservation.setResPersonOrgId(currentUser.getServiceDepId());
		reservation.setSigningCenterId(reservationVo.getTradeCenterId());
		reservation.setSigningCenter(tradeCenter.getCenterName());
		reservation.setResStatus("0");
		reservation.setScheduleId(reservationVo.getScheduleId());

		PropertyAddrSearchVo propertyAddrSearchVo = new PropertyAddrSearchVo();
		propertyAddrSearchVo.setInputValue(reservationVo.getPropertyAddress());
		propertyAddrSearchVo.setAgentCode(currentUser.getId());

		reservation.setCaseCode(toPropertyInfoService
				.getCaseCodeByPropertyAddr(propertyAddrSearchVo));

		String serviceSpecialist = toPropertyInfoService
				.getServiceSpecialistByPropertyAddr(propertyAddrSearchVo);

		if (serviceSpecialist != null && !"".equals(serviceSpecialist)) {
			reservation.setServiceSpecialist(serviceSpecialist);
		} else {
			reservation.setServiceSpecialist(reservationVo
					.getServiceSpecialist());
		}

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

		try {
			freeRoomInfo = reservationService.saveReservation(reservation,
					reservationVo);

		} catch (Exception e) {
			isSuccss = "false";
			e.printStackTrace();
		}

		if (freeRoomInfo != null) {
			freeRoomInfo.setIsSuccess(isSuccss);
		} else {
			// 如果freeRoomInfo为null,说明saveReservation()里的程序出现了异常
			// 这时候将isSuccss设置为false,用来提示预约失败信息
			freeRoomInfo = new FreeRoomInfo();

			freeRoomInfo.setIsSuccess(isSuccss);
		}

		return freeRoomInfo;
	}

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

		List<Long> tradeCenterIdList = reservationService
				.getTradeCenterIdListByGrpCode(currentUser
						.getServiceCompanyCode());

		Long defaultTradeCenterId = 0L;
		if (tradeCenterIdList != null && tradeCenterIdList.size() > 0) {
			defaultTradeCenterId = tradeCenterIdList.get(0);
		}

		request.setAttribute("defaultTradeCenterId", defaultTradeCenterId);

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
	 * ajax获取各个时间段签约室房间总数
	 * 
	 * @return 签约室签约列表
	 * @throws ParseException
	 */
	@RequestMapping(value = "getSignRoomInfoListByDate")
	@ResponseBody
	public List<SignroomInfo> getSignRoomInfoListByDate(
			SignroomCondition signroomCondition) throws ParseException {

		List<SignroomInfo> signroomInfoList = reservationService
				.getSignRoomInfoList(signroomCondition);

		return signroomInfoList;
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
		SessionUser currentUser = uamSessionService.getSessionUser();

		List<Long> tradeCenterIdList = reservationService
				.getTradeCenterIdListByGrpCode(currentUser
						.getServiceCompanyCode());

		Long defaultTradeCenterId = 0L;
		if (tradeCenterIdList != null && tradeCenterIdList.size() > 0) {
			defaultTradeCenterId = tradeCenterIdList.get(0);
		} else { // 如果默认没有找到交易中心id,就默认给它市区(市区标识id为1)
			defaultTradeCenterId = 1L;
		}

		List<TransactItemVo> transactItemVoList = reservationService
				.getTransactItemList();

		int remainBespeakNumber = getRemainBespeakNumber();

		request.setAttribute("defaultTradeCenterId", defaultTradeCenterId);
		request.setAttribute("transactItemVoList", transactItemVoList);
		request.setAttribute("agentCode", currentUser.getId());
		request.setAttribute("remainBespeakNumber", remainBespeakNumber);

		return "mobile/signroom/reservation/bespeak";
	}

	/**
	 * 预约取号保存
	 * 
	 * @param reservationVo
	 *            预约取号前台传的参数对象
	 * @return 返回true,说明保存成功;返回false,保存失败。
	 * @throws ParseException
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

		String dateStr = DateUtil.getFormatDate(new Date(), "yyMMdd");
		String resNo = uamBasedataService.nextSeqVal("QYSYY_CODE", dateStr);

		Reservation reservation = new Reservation();
		reservation.setResNo(resNo);
		reservation.setResType(reservationVo.getResType());
		reservation.setResPersonId(currentUser.getId());

		Map<String, Object> map = new HashedMap<String, Object>();
		map.put("pkid", reservationVo.getTradeCenterId());
		TradeCenter tradeCenter = tradeCenterService.getTradeCenter(map);

		reservation.setResPersonOrgId(currentUser.getServiceDepId());
		reservation.setSigningCenterId(reservationVo.getTradeCenterId());
		reservation.setSigningCenter(tradeCenter.getCenterName());
		reservation.setResStatus("0");
		reservation.setScheduleId(reservationVo.getScheduleId());

		PropertyAddrSearchVo propertyAddrSearchVo = new PropertyAddrSearchVo();
		propertyAddrSearchVo.setInputValue(reservationVo.getPropertyAddress());
		propertyAddrSearchVo.setAgentCode(currentUser.getId());

		reservation.setCaseCode(toPropertyInfoService
				.getCaseCodeByPropertyAddr(propertyAddrSearchVo));

		String serviceSpecialist = toPropertyInfoService
				.getServiceSpecialistByPropertyAddr(propertyAddrSearchVo);

		if (serviceSpecialist != null && !"".equals(serviceSpecialist)) {
			reservation.setServiceSpecialist(serviceSpecialist);
		} else {
			reservation.setServiceSpecialist(reservationVo
					.getServiceSpecialist());
		}

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
		int remainBespeakNumber = getRemainBespeakNumber();

		request.setAttribute("agentCode", currentUser.getId());
		request.setAttribute("remainBespeakNumber", remainBespeakNumber);

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
	 * 管理界面ajax根据产证地址获取对应的案件编号
	 * 
	 * @return 案件编号
	 */
	@RequestMapping(value = "getCaseCodeByPropertyAddrBack")
	@ResponseBody
	public String getCaseCodeByPropertyAddrBack(HttpServletRequest request) {
		String propertyAddress = request.getParameter("propertyAddress");
		String agentCode = request.getParameter("agentCode");

		PropertyAddrSearchVo propertyAddrSearchVo = new PropertyAddrSearchVo();
		if (!StringUtil.isBlank(propertyAddress)) {
			propertyAddrSearchVo.setInputValue(propertyAddress);
		}
		if (!StringUtil.isBlank(agentCode)) {
			propertyAddrSearchVo.setAgentCode(agentCode);
		}
		return toPropertyInfoService
				.getCaseCodeByPropertyAddr(propertyAddrSearchVo);
	}

	/**
	 * ajax根据产证地址获取对应的案件编号
	 * 
	 * @return 案件编号
	 */
	@RequestMapping(value = "getCaseCodeByPropertyAddr")
	@ResponseBody
	public String getCaseCodeByPropertyAddr(HttpServletRequest request) {
		SessionUser currentUser = uamSessionService.getSessionUser();
		String propertyAddress = request.getParameter("propertyAddress");

		PropertyAddrSearchVo propertyAddrSearchVo = new PropertyAddrSearchVo();
		propertyAddrSearchVo.setInputValue(propertyAddress);
		propertyAddrSearchVo.setAgentCode(currentUser.getId());

		return toPropertyInfoService
				.getCaseCodeByPropertyAddr(propertyAddrSearchVo);
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

	/**
	 * ajax根据产证地址获取对应案件的服务顾问(交易顾问)
	 * 
	 * @return 服务顾问(交易顾问)名称
	 */
	@RequestMapping(value = "getServiceSpecialistByPropertyAddr")
	@ResponseBody
	public String getServiceSpecialistByPropertyAddr(HttpServletRequest request) {
		SessionUser currentUser = uamSessionService.getSessionUser();
		String propertyAddress = request.getParameter("propertyAddress");

		PropertyAddrSearchVo propertyAddrSearchVo = new PropertyAddrSearchVo();
		propertyAddrSearchVo.setInputValue(propertyAddress);
		propertyAddrSearchVo.setAgentCode(currentUser.getId());

		return toPropertyInfoService
				.getServiceSpecialistByPropertyAddr(propertyAddrSearchVo);
	}

}
