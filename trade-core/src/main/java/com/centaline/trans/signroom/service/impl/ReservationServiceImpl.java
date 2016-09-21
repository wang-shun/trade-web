package com.centaline.trans.signroom.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.signroom.entity.Reservation;
import com.centaline.trans.signroom.repository.ReservationMapper;
import com.centaline.trans.signroom.service.ReservationService;
import com.centaline.trans.signroom.vo.ReservationInfo;
import com.centaline.trans.signroom.vo.ReservationSearchVo;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationMapper reservationMapper;

	@Override
	public int saveReservation(Reservation reservation) {
		return reservationMapper.insertSelective(reservation);
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
}
