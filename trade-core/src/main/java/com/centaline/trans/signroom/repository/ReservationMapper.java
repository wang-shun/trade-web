package com.centaline.trans.signroom.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.signroom.entity.Reservation;
import com.centaline.trans.signroom.vo.FreeRoomInfo;
import com.centaline.trans.signroom.vo.FreeRoomVo;
import com.centaline.trans.signroom.vo.ReservationInfo;
import com.centaline.trans.signroom.vo.ReservationSearchVo;
import com.centaline.trans.signroom.vo.TransactItemVo;

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

	/**
	 * 获取办理事项列表
	 * 
	 * @return 办理事项列表信息
	 */
	public List<TransactItemVo> getTransactItemList();

	/**
	 * 获取达到特定条件的闲置房间信息
	 * 
	 * @param freeRoomVo
	 *            闲置房间查询条件
	 * @return 闲置房间信息
	 */
	public FreeRoomInfo getFreeRoomByCondition(FreeRoomVo freeRoomVo);

	/**
	 * 更新预约单信息
	 * 
	 * @param freeRoomVo
	 *            闲置房间信息
	 * @return 如果返回1,更新成功;如果返回0,更新失败;
	 */
	public int updateReservationInfo(FreeRoomVo freeRoomVo);

	/**
	 * 根据预约单标识获取对应的预约单信息
	 * 
	 * @param resId
	 *            预约单标识
	 * @return 预约单信息
	 */
	public Reservation getReservationById(Long resId);

	/**
	 * 取消预约
	 * 
	 * @param resId
	 *            预约单标识
	 * @return 返回1,更新成功;返回0,更新失败。
	 */
	public int cancelReservation(Long resId);

	/**
	 * 根据三级市场人员的code获取所属的交易中心标识集合
	 * 
	 * @param grpCode
	 *            三级市场人员code
	 * @return 交易中心的标识集合
	 */
	public List<Long> getTradeCenterIdListByGrpCode(String grpCode);

	/**
	 * 签约室开始使用
	 * 
	 * @param resId
	 *            预约单标识
	 * @return 如果返回1,开始使用成功;返回0,开始使用失败。
	 */
	public int startUse(Long resId);

	/**
	 * 签约室结束使用
	 * 
	 * @param resId
	 *            预约单标识
	 * @return 如果返回1,结束使用成功;返回0,结束使用失败。
	 */
	public int endUse(Long resId);

}