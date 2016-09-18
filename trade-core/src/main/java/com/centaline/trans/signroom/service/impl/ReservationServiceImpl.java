package com.centaline.trans.signroom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.signroom.entity.Reservation;
import com.centaline.trans.signroom.repository.ReservationMapper;
import com.centaline.trans.signroom.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationMapper reservationMapper;

	@Override
	public int saveReservation(Reservation reservation) {
		return reservationMapper.insertSelective(reservation);
	}

}
