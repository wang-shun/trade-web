package com.centaline.trans.signroom.web;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
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
import com.centaline.trans.signroom.vo.TransactItemVo;
import com.centaline.trans.utils.DateUtil;

/**
 * 预约取号微信端controller
 * 
 * @author yinjf2
 *
 */
@Controller
@RequestMapping(value = "mobile/reservation")
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
		// List<Long> tradeCenterIdList = reservationService
		// .getTradeCenterIdListByGrpCode("033H054");

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
	 * 进入预约取号页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bespeakUI")
	public String bespeakUI(Model model, HttpServletRequest request) {
		SessionUser sessionUser = uamSessionService.getSessionUser();

		Long tradeCenterId = Long.parseLong(request
				.getParameter("defaultTradeCenterId"));
		String selDate = request.getParameter("inputSelDate");
		String bespeakTime = request.getParameter("inputBespeakTime");
		List<TransactItemVo> transactItemVoList = reservationService
				.getTransactItemList();

		request.setAttribute("transactItemVoList", transactItemVoList);
		request.setAttribute("tradeCenterId", tradeCenterId);
		request.setAttribute("selDate", selDate);
		request.setAttribute("bespeakTime", bespeakTime);
		request.setAttribute("agentCode", sessionUser.getId());
		// request.setAttribute("agentCode",
		// "E39F5661B6614F968F27E7BD24BA324A");

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
	public FreeRoomInfo save(ReservationVo reservationVo) throws ParseException {
		FreeRoomInfo freeRoomInfo = reservationService
				.getFreeRoomByCondition(reservationVo); // 获取闲置的房间信息

		if (freeRoomInfo == null) {
			freeRoomInfo = new FreeRoomInfo();
			freeRoomInfo.setIsSuccess("noRoom");

			return freeRoomInfo;
		}

		SessionUser currentUser = uamSessionService.getSessionUser();
		User resPerson = uamUserOrgService.getUserById(reservationVo
				.getResPersonId());

		String dateStr = DateUtil.getFormatDate(new Date(), "yyMMdd");
		String resNo = uamBasedataService.nextSeqVal("QYSYY_CODE", dateStr);

		Reservation reservation = new Reservation();
		reservation.setResNo(resNo);
		reservation.setResType(reservationVo.getResType());
		reservation.setResPersonId(reservationVo.getResPersonId());

		Map<String, Object> map = new HashedMap<String, Object>();
		map.put("pkid", reservationVo.getTradeCenterId());
		TradeCenter tradeCenter = tradeCenterService.getTradeCenter(map);

		reservation.setResPersonOrgId(resPerson.getOrgId());
		reservation.setSigningCenterId(reservationVo.getTradeCenterId());
		reservation.setSigningCenter(tradeCenter.getCenterName());
		reservation.setResStatus("0");
		reservation.setScheduleId(reservationVo.getScheduleId());

		PropertyAddrSearchVo propertyAddrSearchVo = new PropertyAddrSearchVo();
		propertyAddrSearchVo.setInputValue(reservationVo.getPropertyAddress());
		propertyAddrSearchVo.setAgentCode(currentUser.getId());

		reservation.setCaseCode(toPropertyInfoService
				.getCaseCodeByPropertyAddr(propertyAddrSearchVo));
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

		request.setAttribute("agentCode", currentUser.getId());
		// request.setAttribute("agentCode",
		// "E39F5661B6614F968F27E7BD24BA324A");

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

}
