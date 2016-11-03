package com.centaline.trans.signroom.service;

import java.text.ParseException;
import java.util.List;

import com.centaline.trans.signroom.entity.Reservation;
import com.centaline.trans.signroom.vo.FreeRoomInfo;
import com.centaline.trans.signroom.vo.ReservationInfo;
import com.centaline.trans.signroom.vo.ReservationSearchVo;
import com.centaline.trans.signroom.vo.ReservationVo;
import com.centaline.trans.signroom.vo.SignroomCondition;
import com.centaline.trans.signroom.vo.SignroomInfo;
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

	/**
	 * 取消预约
	 * 
	 * @param resId
	 *            预约单标识
	 * @return 如果返回true,预约成功;返回false,预约失败。
	 */
	public String cancelReservation(Long resId);

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
	 * @return 返回1,操作成功;返回0,操作失败。
	 */
	public int startUse(Long resId);

	/**
	 * 签约室结束使用
	 * 
	 * @param resId
	 *            预约单标识
	 * @return 返回1,操作成功;返回0,操作失败。
	 */
	public int endUse(Long resId);

	/**
	 * 获取各个时间段签约室房间总数
	 * 
	 * @param signroomCondition
	 *            签约室条件
	 * @return 各个时间签约室房间总数信息
	 */
	public List<SignroomInfo> getSignRoomInfoList(
			SignroomCondition signroomCondition) throws ParseException;

	/**
	 * 获取是否有符合条件的签约室房间信息
	 * 
	 * @param reservationVo
	 *            预约信息对象
	 * @return 闲置的房间信息
	 */
	public FreeRoomInfo getMatchFreeRoomByCondition(ReservationVo reservationVo);

	/**
	 * 获取是否有更小的签约室房间信息
	 * 
	 * @param reservationVo
	 *            预约信息对象
	 * @return 闲置的房间信息
	 */
	public FreeRoomInfo getMinFreeRoomByCondition(ReservationVo reservationVo);

	/**
	 * 获取当前用户两周内已使用的用户次数(预约中、预约取消、预约过期)
	 * 
	 * @param currentUserId
	 *            当前用户标识
	 * @return 已使用预约次数
	 */
	public int getUsedBespeakNumber(String currentUserId);

	/**
	 * 变更签约室获取可用房间信息
	 * 
	 * @param reservationVo
	 *            签约室条件
	 * @return 可用房间xinxi列表
	 */
	public List<SignroomInfo> getUseableSignRoomList(ReservationVo reservationVo);

	/**
	 * 变更签约室
	 * 
	 * @param reservationVo
	 *            签约室条件
	 */
	public void changeRoom(ReservationVo reservationVo);

}
