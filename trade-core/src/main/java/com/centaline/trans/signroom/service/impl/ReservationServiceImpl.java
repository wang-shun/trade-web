package com.centaline.trans.signroom.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.signroom.entity.Reservation;
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

	@Override
	public FreeRoomInfo saveReservation(Reservation reservation,
			ReservationVo reservationVo) {
		String isSuccss = "true";
		FreeRoomInfo freeRoomInfo = null;

		try {
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

		} catch (Exception e) {
			isSuccss = "false";
			e.printStackTrace();
		}

		if (freeRoomInfo != null)
			freeRoomInfo.setIsSuccess(isSuccss);

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
		Long tradeCenterId = reservationVo.getTradeCenterId();
		String selDate = reservationVo.getSelDate(); // 预约日期
		String bespeakTime = reservationVo.getBespeakTime(); // 预约时间段
		int numberOfParticipants = reservationVo.getNumberOfParticipants(); // 参与人数

		FreeRoomVo freeRoomVo = new FreeRoomVo();
		freeRoomVo.setTradeCenterId(tradeCenterId);
		freeRoomVo
				.setNumberOfParticipants(reservationVo.getActNumberOfPeople());

		String formatStartDate = "";
		String formatEndDate = "";
		if (bespeakTime != null && !"".equals(bespeakTime)) {
			formatStartDate = selDate + " "
					+ bespeakTime.substring(0, bespeakTime.indexOf("-"));

			formatEndDate = selDate
					+ " "
					+ bespeakTime.substring(bespeakTime.indexOf("-") + 1,
							bespeakTime.length());
		}

		freeRoomVo.setStartDate(formatStartDate);
		freeRoomVo.setEndDate(formatEndDate);

		FreeRoomInfo freeRoomInfo = reservationMapper
				.getFreeRoomByCondition(freeRoomVo);

		if (freeRoomInfo != null) {
			freeRoomInfo.setSelDate(selDate);
			freeRoomInfo.setBespeakTime(bespeakTime);
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
}
