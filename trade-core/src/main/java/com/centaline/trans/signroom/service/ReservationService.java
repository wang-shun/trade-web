package com.centaline.trans.signroom.service;

import java.util.List;

import com.centaline.trans.signroom.entity.Reservation;
import com.centaline.trans.signroom.vo.FreeRoomInfo;
import com.centaline.trans.signroom.vo.ReservationInfo;
import com.centaline.trans.signroom.vo.ReservationSearchVo;
import com.centaline.trans.signroom.vo.ReservationVo;
import com.centaline.trans.signroom.vo.TransactItemVo;

public interface ReservationService {

	/**
	 * 保存预约取号信息
	 * 
	 * @param reservation
	 *            预约取号信息对象
	 * @param reservationVo
	 *            从前台传到后台的预约信息
	 * @return 返回预约房间信息
	 */
	public FreeRoomInfo saveReservation(Reservation reservation,
			ReservationVo reservationVo);

	/**
	 * 获取预约时间段信息
	 * 
	 * @return 返回预约时间段列表
	 */
	public List<String> getBespeakTime();

	/**
	 * 根据店组code获取所在机构id
	 * 
	 * @param grpCode
	 *            店组code
	 * @return 所在机构id
	 */
	public String getOrgIdByGrpcode(String grpCode);

	/**
	 * 获取某个时间段的签约室预约信息
	 * 
	 * @param reservationSearchVo
	 *            签约室预约查询条件
	 * 
	 * @return 签约室预约列表信息
	 */
	public List<ReservationInfo> getSignRoomInfoListByCondition(
			ReservationSearchVo reservationSearchVo);

	/**
	 * 获取办理事项列表
	 * 
	 * @return 办理事项列表信息
	 */
	public List<TransactItemVo> getTransactItemList();

	/**
	 * 获取达到特定条件的闲置房间信息
	 * 
	 * @param reservationVo
	 * 
	 * @return 闲置房间信息
	 */
	public FreeRoomInfo getFreeRoomByCondition(ReservationVo reservationVo);

}
