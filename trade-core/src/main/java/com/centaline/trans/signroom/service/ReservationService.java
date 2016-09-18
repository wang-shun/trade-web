package com.centaline.trans.signroom.service;

import com.centaline.trans.signroom.entity.Reservation;

public interface ReservationService {

	/**
	 * 保存预约取号信息
	 * 
	 * @param reservation
	 *            预约取号信息对象
	 * @return 如果返回1,则说明保存成功;如果返回0,则说明保存失败。
	 */
	public int saveReservation(Reservation reservation);

}
