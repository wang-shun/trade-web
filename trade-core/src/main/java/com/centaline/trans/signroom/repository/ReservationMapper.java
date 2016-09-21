package com.centaline.trans.signroom.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.signroom.entity.Reservation;
import com.centaline.trans.signroom.vo.ReservationInfo;
import com.centaline.trans.signroom.vo.ReservationSearchVo;

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

	/**
	 * 获取预约时间段信息
	 * 
	 * @return 返回用,隔开的时间段字符串
	 */
	public String getBespeakTime();

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
}