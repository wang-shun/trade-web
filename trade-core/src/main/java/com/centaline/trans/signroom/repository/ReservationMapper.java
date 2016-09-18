package com.centaline.trans.signroom.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.signroom.entity.Reservation;

@MyBatisRepository
public interface ReservationMapper {

	/**
	 * 保存预约取号信息
	 * 
	 * @param reservation
	 *            预约取号信息对象
	 * @return 如果返回1,则说明保存成功;如果返回0,则说明保存失败。
	 */
	public int insertSelective(Reservation reservation);
}