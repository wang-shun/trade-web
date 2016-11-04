package com.centaline.trans.signroom.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.signroom.entity.Reservation;
import com.centaline.trans.signroom.entity.RmRoomSchedule;
import com.centaline.trans.signroom.entity.RmSignRoom;
import com.centaline.trans.signroom.repository.ResFlowupMapper;
import com.centaline.trans.signroom.repository.ReservationMapper;
import com.centaline.trans.signroom.repository.RmRoomScheduleMapper;
import com.centaline.trans.signroom.repository.RmSignRoomMapper;
import com.centaline.trans.signroom.service.ReservationService;
import com.centaline.trans.signroom.vo.FreeRoomInfo;
import com.centaline.trans.signroom.vo.FreeRoomVo;
import com.centaline.trans.signroom.vo.ReservationInfo;
import com.centaline.trans.signroom.vo.ReservationSearchVo;
import com.centaline.trans.signroom.vo.ReservationVo;
import com.centaline.trans.signroom.vo.RoomProp;
import com.centaline.trans.signroom.vo.SignroomCondition;
import com.centaline.trans.signroom.vo.SignroomInfo;
import com.centaline.trans.signroom.vo.TransactItemVo;

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

	private Logger logger = LoggerFactory
			.getLogger(ReservationServiceImpl.class);

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
		// Long tradeCenterId = reservationVo.getTradeCenterId();
		// String selDate = reservationVo.getSelDate(); // 预约日期
		// String bespeakTime = reservationVo.getBespeakTime(); // 预约时间段
		// int numberOfParticipants = reservationVo.getNumberOfParticipants();
		// // 参与人数

		// FreeRoomVo freeRoomVo = new FreeRoomVo();
		// freeRoomVo.setTradeCenterId(tradeCenterId);
		// freeRoomVo
		// .setNumberOfParticipants(reservationVo.getActNumberOfPeople());

		// String formatStartDate = "";
		// String formatEndDate = "";
		// if (bespeakTime != null && !"".equals(bespeakTime)) {
		// formatStartDate = selDate + " " + bespeakTime.substring(0,
		// bespeakTime.indexOf("-"));
		// formatEndDate = selDate + " " +
		// bespeakTime.substring(bespeakTime.indexOf("-") +
		// 1,bespeakTime.length());
		// }

		// freeRoomVo.setStartDate(reservationVo.getStartDate());
		// freeRoomVo.setEndDate(reservationVo.getEndDate());

		FreeRoomInfo freeRoomInfo = null;
		if ("normal".equals(reservationVo.getFlag())) {
			freeRoomInfo = reservationMapper
					.getMatchFreeRoomByCondition(reservationVo);
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
		return reservationMapper.endUse(resId);
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
	public void changeRoom(ReservationVo reservationVo) {
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
		}
	}
}
