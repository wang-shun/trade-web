package com.centaline.trans.signroom.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.centaline.trans.signroom.entity.Reservation;
import com.centaline.trans.signroom.entity.RmRoomSchedule;
import com.centaline.trans.signroom.entity.RmSignRoom;
import com.centaline.trans.signroom.repository.ResFlowupMapper;
import com.centaline.trans.signroom.repository.ReservationMapper;
import com.centaline.trans.signroom.repository.RmRoomScheduleMapper;
import com.centaline.trans.signroom.repository.RmSignRoomMapper;
import com.centaline.trans.signroom.service.ReservationService;
import com.centaline.trans.signroom.vo.ChangeRoomResult;
import com.centaline.trans.signroom.vo.FreeRoomInfo;
import com.centaline.trans.signroom.vo.FreeRoomVo;
import com.centaline.trans.signroom.vo.ReservationInfo;
import com.centaline.trans.signroom.vo.ReservationSearchVo;
import com.centaline.trans.signroom.vo.ReservationVo;
import com.centaline.trans.signroom.vo.RoomProp;
import com.centaline.trans.signroom.vo.SignroomCondition;
import com.centaline.trans.signroom.vo.SignroomInfo;
import com.centaline.trans.signroom.vo.TransactItemVo;
import com.centaline.trans.utils.DateUtil;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationMapper reservationMapper;

	@Autowired
	private RmRoomScheduleMapper rmRoomScheduleMapper;

	@Autowired
	private RmSignRoomMapper rmSignRoomMapper;

	@Autowired
	private ResFlowupMapper resFlowupMapper;

	@Autowired
	private UamBasedataService uamBasedataService;

	@Autowired
	private UamSessionService uamSessionService;

	@Override
	public FreeRoomInfo saveReservation(Reservation reservation,
			ReservationVo reservationVo) {
		FreeRoomInfo freeRoomInfo = null;

		reservationMapper.insertSelective(reservation);

		Long resId = reservation.getPkid(); // 预约单id

		freeRoomInfo = getFreeRoomByCondition(reservationVo); // 获取闲置的房间信息
		freeRoomInfo.setResNo(reservation.getResNo());

		FreeRoomVo freeRoomVo = new FreeRoomVo();
		freeRoomVo.setResId(resId);
		freeRoomVo.setScheduleId(freeRoomInfo.getScheduleId());

		rmRoomScheduleMapper.updateFreeRoomStatus(freeRoomVo); // 更新闲置房间的使用状态

		freeRoomVo = new FreeRoomVo();
		freeRoomVo.setScheduleId(freeRoomInfo.getScheduleId());
		freeRoomVo.setResId(resId);
		freeRoomVo.setNumberOfPeople(freeRoomInfo.getNumberOfPeople());

		reservationMapper.updateReservationInfo(freeRoomVo); // 更新预约单的信息

		return freeRoomInfo;
	}

	public Integer getMinNum(int numberOfPeople, int numberOfParticipants) {
		int min = 0;

		if (numberOfPeople > numberOfParticipants) {
			min = numberOfParticipants;
		} else if (numberOfPeople < numberOfParticipants) {
			min = numberOfPeople;
		} else {
			min = numberOfPeople;
		}

		return min;
	}

	@Override
	public List<String> getBespeakTime() {
		String strBespeakTime = reservationMapper.getBespeakTime();

		List<String> bespeakTimeList = new ArrayList<String>();

		if (strBespeakTime != null && !"".equals(strBespeakTime)) {
			String[] arrayBespeakTime = strBespeakTime.split(",");

			for (int i = 0; i < arrayBespeakTime.length; i++) {
				bespeakTimeList.add(arrayBespeakTime[i]);
			}
		}

		return bespeakTimeList;
	}

	@Override
	public String getOrgIdByGrpcode(String grpCode) {
		return reservationMapper.getOrgIdByGrpcode(grpCode);
	}

	@Override
	public List<ReservationInfo> getSignRoomInfoListByCondition(
			ReservationSearchVo reservationSearchVo) {
		return reservationMapper
				.getSignRoomInfoListByCondition(reservationSearchVo);
	}

	@Override
	public List<TransactItemVo> getTransactItemList() {
		return reservationMapper.getTransactItemList();
	}

	@Override
	public FreeRoomInfo getFreeRoomByCondition(ReservationVo reservationVo) {
		FreeRoomInfo freeRoomInfo = null;
		// 如果是正常预约的情况
		if ("normal".equals(reservationVo.getFlag())) {
			freeRoomInfo = reservationMapper
					.getMatchFreeRoomByCondition(reservationVo);
			// 如果是接受小一点的房间情况
		} else if ("accept".equals(reservationVo.getFlag())) {
			freeRoomInfo = reservationMapper
					.getMinFreeRoomByCondition(reservationVo);
		}

		if (freeRoomInfo != null) {
			freeRoomInfo.setSelDate(reservationVo.getSelDate());
			freeRoomInfo.setBespeakTime(reservationVo.getBespeakTime());
		}

		return freeRoomInfo;
	}

	@Override
	public String cancelReservation(Long resId) {
		String result = "true";

		try {
			Reservation reservation = reservationMapper
					.getReservationById(resId);

			reservationMapper.cancelReservation(resId); // 将预约单的状态更改为已取消状态
			rmRoomScheduleMapper
					.updateToFreeStatus(reservation.getScheduleId()); // 将已预约的房间重新恢复到闲置状态
		} catch (Exception e) {
			result = "false";
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public List<Long> getTradeCenterIdListByGrpCode(String grpCode) {
		return reservationMapper.getTradeCenterIdListByGrpCode(grpCode);
	}

	@Override
	public int startUse(Long resId) {
		return reservationMapper.startUse(resId);
	}

	@Override
	public int endUse(Long resId) {
		int result = 1;

		Date checkedOutTime = new Date();

		try {
			Reservation reservation = reservationMapper
					.getReservationById(resId);
			reservation.setCheckedOutTime(checkedOutTime);

			// 如果提前预约签退,则要同步原先预约信息的签退时间
			if ("5".equals(reservation.getResStatus())) {
				Long scheduleId = Long.parseLong(reservation.getScheduleId());
				RmRoomSchedule rmRoomSchedule = rmRoomScheduleMapper
						.getRmRoomScheduleByPkid(scheduleId);

				ReservationVo reservationVo = new ReservationVo();

				// 设置条件
				reservationVo.setResPersonId(reservation.getResPersonId());
				reservationVo.setCheckInTime(reservation.getCheckInTime());
				reservationVo.setRoomId(rmRoomSchedule.getRoomId());

				// 根据房间号相同、签到时间相同、预约人id相同获取原先的预约记录
				Reservation oldReservation = reservationMapper
						.getOldReservation(reservationVo);

				// 如果存在原先预约记录
				if (oldReservation != null) {
					// 设置签退时间
					oldReservation.setCheckedOutTime(checkedOutTime);

					// 同步原先预约记录的签退时间
					reservationMapper.endUse(oldReservation);
				}
			}

			// 更新提前使用的预约记录的签退时间
			reservationMapper.endUse(reservation);
		} catch (Exception e) {
			result = 0;
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public List<SignroomInfo> getSignRoomInfoList(
			SignroomCondition signroomCondition) throws ParseException {
		List<String> bespeakTimeList = getBespeakTime();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Date currentDateTime = new Date();
		List<SignroomInfo> signroomInfoList = new ArrayList<SignroomInfo>();
		if (bespeakTimeList != null && bespeakTimeList.size() > 0) {
			for (String bespeakTime : bespeakTimeList) {

				String strStartDateTime = signroomCondition.getSelDate() + " "
						+ bespeakTime.substring(0, bespeakTime.indexOf("-"));

				String strEndDateTime = signroomCondition.getSelDate()
						+ " "
						+ bespeakTime.substring(bespeakTime.indexOf("-") + 1,
								bespeakTime.length());

				Date startDateTime = sdf.parse(strStartDateTime);

				if (currentDateTime.getTime() <= startDateTime.getTime()) {
					SignroomInfo signroomInfo = new SignroomInfo();

					signroomCondition.setStartDateTime(strStartDateTime);
					signroomCondition.setEndDateTime(strEndDateTime);

					int signroomNumber = reservationMapper
							.getSignRoomInfoListByDate(signroomCondition);

					signroomInfo.setBespeakTime(bespeakTime);
					signroomInfo.setSignroomNumber(signroomNumber);

					signroomInfoList.add(signroomInfo);
				}

			}
		}

		return signroomInfoList;
	}

	@Override
	public FreeRoomInfo getMatchFreeRoomByCondition(ReservationVo reservationVo) {
		return reservationMapper.getMatchFreeRoomByCondition(reservationVo);
	}

	@Override
	public FreeRoomInfo getMinFreeRoomByCondition(ReservationVo reservationVo) {
		return reservationMapper.getMinFreeRoomByCondition(reservationVo);
	}

	@Override
	public int getUsedBespeakNumber(String currentUserId) {
		return reservationMapper.getUsedBespeakNumber(currentUserId);
	}

	@Override
	public List<SignroomInfo> getUseableSignRoomList(ReservationVo reservationVo) {
		List<Integer> roomAccommodationList = reservationMapper
				.getRoomAccommodationList(reservationVo);

		SignroomInfo signroomInfo = null;
		List<SignroomInfo> signroomInfoList = new ArrayList<SignroomInfo>();
		if (roomAccommodationList != null && roomAccommodationList.size() > 0) {
			for (Integer roomAccommodation : roomAccommodationList) {
				signroomInfo = new SignroomInfo();
				signroomInfo.setNumberOfPeople(roomAccommodation);

				reservationVo.setNumberOfPeople(roomAccommodation);
				List<RoomProp> roomPropList = reservationMapper
						.getRoomPropList(reservationVo);

				signroomInfo.setRoomPropList(roomPropList);

				signroomInfoList.add(signroomInfo);
			}
		}

		return signroomInfoList;
	}

	@Override
	public ChangeRoomResult changeRoom(ReservationVo reservationVo) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		ChangeRoomResult changeRoomResult = new ChangeRoomResult();

		Long resId = Long.parseLong(reservationVo.getResId());
		Reservation reservation = reservationMapper.getReservationById(resId);

		Long oldScheduleId = Long.parseLong(reservation.getScheduleId());
		Long newScheduleId = Long.parseLong(reservationVo.getScheduleId());

		RmRoomSchedule newRmRoomSchedule = rmRoomScheduleMapper
				.getRmRoomScheduleByPkid(newScheduleId);

		RmSignRoom newRmSignRoom = rmSignRoomMapper
				.getSignRoomInfoById(newRmRoomSchedule.getRoomId());

		reservationVo.setNumberOfPeople(newRmSignRoom
				.getNumbeOfAccommodatePeople());
		reservationVo.setNewScheduleId(newScheduleId);

		reservationMapper.changeRoom(reservationVo); // 将T_RM_RESERVATION表的SCHEDULE_ID进行更改
		rmRoomScheduleMapper.updateRoomStatusToFree(oldScheduleId); // 将原先的房间状态更改成空闲状态
		rmRoomScheduleMapper.updateRoomStatusToUsed(reservationVo); // 将新房间状态改成已预约状态

		// 更换签约室并启用
		if ("changeAndSave".equals(reservationVo.getFlag())) {
			reservationMapper.startUse(resId);

			reservation = reservationMapper.getReservationById(resId);
			changeRoomResult.setOperateTime(sdf.format(reservation
					.getCheckInTime()));
		}

		return changeRoomResult;
	}

	@Override
	public Reservation getReservationById(Long resId) {
		return reservationMapper.getReservationById(resId);
	}

	public FreeRoomInfo getFreeRoomByRoomNoAndCurTime(Long roomId) {
		FreeRoomInfo freeRoomInfo = null;

		// 获取当前时间
		Date currentTime = getCurrentTime();

		// 根据当前时间获取当前预约时间段
		Map<String, String> resTimeMap = getResTime(currentTime);

		if (resTimeMap != null && resTimeMap.size() > 0) {
			// 获取预约开始时间(格式:yyyy-MM-mm HH:mm)
			String beginResTime = resTimeMap.get("resBeginDateTime");

			// 获取预约结束时间(格式:yyyy-MM-mm HH:mm)
			String endResTime = resTimeMap.get("resEndDateTime");

			// 设置查询条件
			ReservationVo reservationVo = new ReservationVo();
			reservationVo.setRoomId(roomId);
			reservationVo.setBeginResTime(beginResTime);
			reservationVo.setEndResTime(endResTime);

			// 获取当前时间、同一房间号是否有空闲的房间
			freeRoomInfo = reservationMapper
					.getSignRoomByRoomAndCurtime(reservationVo);
		}

		return freeRoomInfo;
	}

	@Override
	public String isHasFreeRoomByCurrentTimeAndRoomNo(Long roomId) {
		String result = "true";

		// 获取当前时间、同一房间号是否有空闲的房间
		FreeRoomInfo freeRoomInfo = getFreeRoomByRoomNoAndCurTime(roomId);

		if (freeRoomInfo != null) {
			// 判断房间状态是否空置
			freeRoomInfo = judgeUseStatus(freeRoomInfo);

			String useStatus = freeRoomInfo.getUseStatus();

			if (!"N".equals(useStatus)) {
				result = "false";
			}
		} else {
			// 如果没有房间,则设置result为false,代表无房间
			result = "false";
		}

		return result;
	}

	private FreeRoomInfo judgeUseStatus(FreeRoomInfo freeRoomInfo) {
		Long startTime = freeRoomInfo.getStartDate().getTime();// 该时间段的开始时间
		Long createTime = null;
		if (freeRoomInfo.getCreateTime() != null) {
			createTime = freeRoomInfo.getCreateTime().getTime();// 真实预约时间
		}
		Long curTime = new Date().getTime();// 当前时间

		if (freeRoomInfo.getResStatus() == null) {
			freeRoomInfo.setUseStatus("N");
		} else if (freeRoomInfo.getResStatus() != null
				&& "4".equals(freeRoomInfo.getResStatus().trim())) {// 预约已取消状态为空置
			freeRoomInfo.setUseStatus("N");
		} else {
			if (freeRoomInfo.getCheckInTime() != null) {// 是否已签到
				if (freeRoomInfo.getCheckOutTime() != null) {// 是否已签退
					freeRoomInfo.setUseStatus("N");

				} else {
					freeRoomInfo.setUseStatus("1");// 使用中
				}
			} else {// 未签到的话就判断是否已超出时间段开始时间半个小时
				Long second = null;
				if (createTime != null && (createTime > startTime)) {
					second = (curTime - createTime) / 1000 / 60;// 取得两者时间查转成分钟
					if (second > 30) {// 超过三十分钟的话状态就为空置
						freeRoomInfo.setUseStatus("N");
					} else {
						freeRoomInfo.setUseStatus("0");
					}
				} else {
					second = (curTime - startTime) / 1000 / 60;// 取得两者时间查转成分钟
					if (second > 30) {// 超过三十分钟的话状态就为空置
						freeRoomInfo.setUseStatus("N");
					} else {
						freeRoomInfo.setUseStatus("0");
					}
				}

			}
		}

		return freeRoomInfo;
	}

	@Override
	public String startUseInAdvance(ReservationVo reservationVo) {
		String result = "true";

		// 获取当前时间、同一房间号是否有空闲的房间
		FreeRoomInfo freeRoomInfo = getFreeRoomByRoomNoAndCurTime(reservationVo
				.getRoomId());

		if (freeRoomInfo != null) {
			// 判断使用状态
			freeRoomInfo = judgeUseStatus(freeRoomInfo);

			if ("N".equals(freeRoomInfo.getUseStatus())) {
				// 根据预约id获取预约信息
				Reservation oldReservation = reservationMapper
						.getReservationById(Long.parseLong(reservationVo
								.getResId()));

				result = temporaryAssignment(freeRoomInfo, oldReservation);
			}

		}

		return result;
	}

	/**
	 * 提前使用---临时分配房间信息
	 * 
	 * @param freeRoomInfo
	 *            闲置房间信息
	 * @param oldReservation
	 *            旧预约信息
	 */
	public String temporaryAssignment(FreeRoomInfo freeRoomInfo,
			Reservation oldReservation) {
		String result = "true";
		SessionUser currentUser = uamSessionService.getSessionUser();

		try {
			String dateStr = DateUtil.getFormatDate(new Date(), "yyMMdd");
			String resNo = uamBasedataService.nextSeqVal("QYSYY_CODE", dateStr);

			Date currentTime = new Date();

			Reservation reservation = new Reservation();
			reservation.setResNo(resNo);
			reservation.setResType("1");
			reservation.setResPersonId(oldReservation.getResPersonId());
			reservation.setResPersonName(oldReservation.getResPersonName());
			reservation.setResPersonMobile(oldReservation.getResPersonMobile());
			reservation.setResPersonOrgId(oldReservation.getResPersonOrgId());
			reservation.setSigningCenterId(oldReservation.getSigningCenterId());
			reservation.setSigningCenter(oldReservation.getSigningCenter());
			reservation.setResStatus("1");
			reservation.setScheduleId(freeRoomInfo.getScheduleId());
			reservation.setCaseCode(oldReservation.getCaseCode());
			reservation.setServiceSpecialist(oldReservation
					.getServiceSpecialist());
			reservation.setPropertyAddress(oldReservation.getPropertyAddress());
			reservation.setNumberOfPeople(oldReservation.getNumberOfPeople());
			reservation.setScheduleId(freeRoomInfo.getScheduleId());
			reservation.setNumberOfParticipants(oldReservation
					.getNumberOfParticipants());
			reservation.setTransactItemCode(oldReservation
					.getTransactItemCode());
			reservation.setSpecialRequirement(oldReservation
					.getSpecialRequirement());
			reservation.setCheckInTime(currentTime);

			reservation.setCreateTime(currentTime);
			reservation.setCreateBy(currentUser.getId());
			reservation.setUpdateTime(currentTime);
			reservation.setUpdateBy(currentUser.getId());
			reservation.setIsDelete(0);

			// 保存预约信息
			reservationMapper.insertSelective(reservation);

			// 获取预约单id
			Long resId = reservation.getPkid();

			FreeRoomVo freeRoomVo = new FreeRoomVo();
			freeRoomVo.setResId(resId);
			freeRoomVo.setScheduleId(freeRoomInfo.getScheduleId());

			// 更新临时分配房间的使用状态
			rmRoomScheduleMapper.updateFreeRoomStatus(freeRoomVo);

			// 判断是否提前预约的时间段跟正常预约的时间段是否是相邻时间段
			if (isAdjacentTime(Long.parseLong(oldReservation.getScheduleId()),
					Long.parseLong(freeRoomInfo.getScheduleId()))) {
				// 更新之前预约房间的预约状态改为提前使用
				oldReservation.setResStatus("5"); // 设置之前预约的记录状态为提前使用状态
				oldReservation.setCheckInTime(currentTime);

				reservationMapper.updateStatusToUseInAdvance(oldReservation);
			} else {
				// 在删除原预约信息之前,将房间状态改为空置状态
				rmRoomScheduleMapper.updateRoomStatusToFree(Long
						.parseLong(oldReservation.getScheduleId()));

				// 删除原预约信息
				reservationMapper.deleteReservationById(oldReservation
						.getPkid());
			}

		} catch (Exception e) {
			result = "false";
			e.printStackTrace();

		}

		return result;

	}

	/**
	 * 
	 * @param oldScheduleId
	 *            原先签约安排id
	 * @param newScheduleId
	 *            旧原先签约安排id
	 * @return 返回true,是相邻时间段;返回false,不是相邻时间段。
	 */
	private boolean isAdjacentTime(Long oldScheduleId, Long newScheduleId) {
		// 获取原先的签约室安排信息
		RmRoomSchedule oldRoomSchedule = rmRoomScheduleMapper
				.getRmRoomScheduleByPkid(oldScheduleId);

		// 获取提前使用的签约室安排信息
		RmRoomSchedule newRoomSchedule = rmRoomScheduleMapper
				.getRmRoomScheduleByPkid(newScheduleId);

		// 获取原先的签约室安排的预约开始时间
		long oldStartResTime = (oldRoomSchedule.getStartDate()).getTime();

		// 获取提前使用的签约室安排的预约开始时间
		long newStartResTime = (newRoomSchedule.getStartDate()).getTime();

		// 两时间相减,得到小时数
		long hour = (oldStartResTime - newStartResTime) / 3600000;

		return hour > 2 ? false : true;
	}

	/**
	 * 获取当前时间处于哪个预约时间段
	 * 
	 * @param currentDateTime
	 *            当前时间
	 * @return 预约时间段信息
	 */
	private Map<String, String> getResTime(Date currentDateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String currentDate = sdf.format(currentDateTime);

		String strResDateTime1 = currentDate + " 09:00";
		String strResDateTime2 = currentDate + " 11:00";
		String strResDateTime3 = currentDate + " 13:00";
		String strResDateTime4 = currentDate + " 15:00";
		String strResDateTime5 = currentDate + " 17:00";
		String strResDateTime6 = currentDate + " 19:00";
		String strResDateTime7 = currentDate + " 21:00";

		Map<String, String> resTimeMap = new HashMap<String, String>();

		try {
			Date resDateTime1 = sdf1.parse(strResDateTime1);
			Date resDateTime2 = sdf1.parse(strResDateTime2);
			Date resDateTime3 = sdf1.parse(strResDateTime3);
			Date resDateTime4 = sdf1.parse(strResDateTime4);
			Date resDateTime5 = sdf1.parse(strResDateTime5);
			Date resDateTime6 = sdf1.parse(strResDateTime6);
			Date resDateTime7 = sdf1.parse(strResDateTime7);

			long resTime1 = resDateTime1.getTime();
			long resTime2 = resDateTime2.getTime();
			long resTime3 = resDateTime3.getTime();
			long resTime4 = resDateTime4.getTime();
			long resTime5 = resDateTime5.getTime();
			long resTime6 = resDateTime6.getTime();
			long resTime7 = resDateTime7.getTime();
			long currentTime = currentDateTime.getTime();

			// 当前日期 09:00 <= 当前时间 <= 当前日期 11:00
			if (currentTime >= resTime1 && currentTime < resTime2) {
				resTimeMap.put("resBeginDateTime", currentDate + " 09:00");
				resTimeMap.put("resEndDateTime", currentDate + " 11:00");
			}

			// 当前日期 11:00 <= 当前时间 <= 当前日期 13:00
			if (currentTime >= resTime2 && currentTime < resTime3) {
				resTimeMap.put("resBeginDateTime", currentDate + " 11:00");
				resTimeMap.put("resEndDateTime", currentDate + " 13:00");
			}

			// 当前日期 13:00 <= 当前时间 <= 当前日期 15:00
			if (currentTime >= resTime3 && currentTime < resTime4) {
				resTimeMap.put("resBeginDateTime", currentDate + " 13:00");
				resTimeMap.put("resEndDateTime", currentDate + " 15:00");
			}

			// 当前日期 15:00 <= 当前时间 <= 当前日期 17:00
			if (currentTime >= resTime4 && currentTime < resTime5) {
				resTimeMap.put("resBeginDateTime", currentDate + " 15:00");
				resTimeMap.put("resEndDateTime", currentDate + " 17:00");
			}

			// 当前日期 17:00 <= 当前时间 <= 当前日期 19:00
			if (currentTime >= resTime5 && currentTime < resTime6) {
				resTimeMap.put("resBeginDateTime", currentDate + " 17:00");
				resTimeMap.put("resEndDateTime", currentDate + " 19:00");
			}

			// 当前日期 19:00 <= 当前时间 <= 当前日期 21:00
			if (currentTime >= resTime6 && currentTime <= resTime7) {
				resTimeMap.put("resBeginDateTime", currentDate + " 19:00");
				resTimeMap.put("resEndDateTime", currentDate + " 21:00");
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return resTimeMap;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return 当前时间
	 */
	private Date getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar calCurrent = Calendar.getInstance();
		int year = calCurrent.get(Calendar.YEAR);
		int month = calCurrent.get(Calendar.MONTH) + 1;
		int date = calCurrent.get(Calendar.DATE);
		int hour = calCurrent.get(Calendar.HOUR_OF_DAY);
		int minute = calCurrent.get(Calendar.MINUTE);

		String strMonth = month > 10 ? String.valueOf(month) : "0" + month;
		String strDate = date > 10 ? String.valueOf(date) : "0" + date;
		String strHour = hour > 10 ? String.valueOf(hour) : "0" + hour;
		String strMinute = minute > 10 ? String.valueOf(minute) : "0" + minute;

		String strCurrentTime = year + "-" + strMonth + "-" + strDate + " "
				+ strHour + ":" + strMinute;

		Date currentTime = null;
		try {
			currentTime = sdf.parse(strCurrentTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return currentTime;
	}
}
