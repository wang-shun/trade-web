/**
 * 
 */
package com.centaline.trans.common.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.centaline.trans.signroom.vo.ResFlowupVo;

/**
 * 根据签约室安排id集合获取安排信息和房间信息
 * 
 * @author yinjf2
 */
@Service
public class QuickQueryGetScheduleAndRoomInfoByScheduleIdServiceImpl implements
		CustomDictService {

	@Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;

	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

	private static String sql = "SELECT r.PKID AS resId,"
			+ "r.CHECK_IN_TIME AS checkInTime,r.CHECKED_OUT_TIME AS checkOutTime,r.TRANSACT_ITEM_CODE as transactItemCode,"
			+ "(SELECT rs.START_DATE FROM sctrans.T_RM_ROOM_SCHEDULE rs WHERE rs.PKID = r.SCHEDULE_ID) AS startTime,"
			+ "(SELECT rs.END_DATE FROM sctrans.T_RM_ROOM_SCHEDULE rs WHERE rs.PKID = r.SCHEDULE_ID) AS endTime,"
			+ "(SELECT rs.ROOM_ID FROM sctrans.T_RM_ROOM_SCHEDULE rs WHERE rs.PKID = r.SCHEDULE_ID) AS roomId,"
			+ "DATEDIFF(minute,(SELECT END_DATE FROM sctrans.T_RM_ROOM_SCHEDULE WHERE PKID = r.SCHEDULE_ID),GETDATE()) AS timeDifference,"
			+ "(SELECT sr.ROOM_NO FROM sctrans.T_RM_SIGN_ROOM sr WHERE sr.PKID = (SELECT rs.ROOM_ID FROM sctrans.T_RM_ROOM_SCHEDULE rs WHERE rs.PKID = r.SCHEDULE_ID)) AS roomNO,"
			+ "(SELECT sr.ROOM_TYPE FROM sctrans.T_RM_SIGN_ROOM sr WHERE sr.PKID = (SELECT rs.ROOM_ID FROM sctrans.T_RM_ROOM_SCHEDULE rs WHERE rs.PKID = r.SCHEDULE_ID)) AS roomType"
			+ " FROM sctrans.T_RM_RESERVATION r"
			+ " WHERE r.PKID IN (:resIdList)";

	private static String followUpSql = "SELECT rfu.RES_ID AS resId,u.REAL_NAME AS realName,"
			+ "CONVERT(varchar(100), rfu.CREATE_TIME, 120) as createDateTime,rfu.COMMENT as comment"
			+ " FROM sctrans.T_RM_RES_FLOW_UP rfu"
			+ " LEFT JOIN sctrans.SYS_USER u ON rfu.CREATE_BY = u.ID"
			+ " WHERE rfu.RES_ID IN (:resIdList)"
			+ " order by rfu.RES_ID ASC,rfu.CREATE_TIME desc";

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		List<String> transactItemList = null;
		List<ResFlowupVo> resFlowupList = null;
		ResFlowupVo resFlowupVo = null;
		List<String> resIdList = new ArrayList<String>();

		// 循环将签约室安排id放置到集合中
		for (Map<String, Object> key : keys) {
			Object resIdKey = key.get("resId");

			if (resIdKey != null) {
				resIdList.add(resIdKey.toString());
			}

		}

		if (resIdList.size() > 0) {
			// 新建sql条件参数
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("resIdList", resIdList);
			// 获取安排信息和房间信息
			List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql,
					params);

			// 获取案件跟进列表
			List<Map<String, Object>> follupInfoMapList = jdbcTemplate
					.queryForList(followUpSql, params);

			// 如果预约信息列表和跟进信息列表无记录,则直接返回
			if ((mapList == null || mapList.size() == 0)
					&& (follupInfoMapList == null || follupInfoMapList.size() == 0)) {
				return keys;
			}

			// 如果有列表信息,就将每一行对应的信息绑定起来
			for (Map<String, Object> key : keys) {
				String resId = String.valueOf(key.get("resId"));

				if (mapList != null && mapList.size() > 0) {
					transactItemList = new ArrayList<String>();

					for (Map<String, Object> map : mapList) {
						String rId = String.valueOf(map.get("resId"));

						if (resId.equals(rId)) {
							key.put("startTime", map.get("startTime"));
							key.put("actStartTime",
									sdf.format(map.get("startTime")));

							key.put("endTime", map.get("endTime"));
							key.put("actEndTime",
									sdf.format(map.get("endTime")));

							if (map.get("checkInTime") != null)
								key.put("actCheckInTime",
										sdf.format(map.get("checkInTime")));

							if (map.get("checkOutTime") != null)
								key.put("actCheckOutTime",
										sdf.format(map.get("checkOutTime")));

							key.put("roomId", map.get("roomId"));
							key.put("roomNO", map.get("roomNO"));
							key.put("roomType", map.get("roomType"));
							key.put("timeDifference", map.get("timeDifference"));

							if (map.get("transactItemCode") != null) {
								String transactItemCode = map.get(
										"transactItemCode").toString();
								String[] arrayTransactItemCode = transactItemCode
										.split(",");

								for (int i = 0; i < arrayTransactItemCode.length; i++) {
									transactItemList
											.add(arrayTransactItemCode[i]);
								}

								key.put("transactItemCodeList",
										transactItemList);
							}

							break;
						}
					}
				}

				if (follupInfoMapList != null && follupInfoMapList.size() > 0) {
					resFlowupList = new ArrayList<ResFlowupVo>();

					for (Map<String, Object> follupInfoMap : follupInfoMapList) {
						String rResId = String.valueOf(follupInfoMap
								.get("resId"));

						if (resId.equals(rResId)) {
							resFlowupVo = new ResFlowupVo();

							resFlowupVo.setComment(follupInfoMap.get("comment")
									.toString());

							resFlowupVo.setCreateDateTime(follupInfoMap.get(
									"createDateTime").toString());

							resFlowupVo.setRealName(follupInfoMap.get(
									"realName").toString());

							resFlowupList.add(resFlowupVo);
						}

						key.put("flowupInfoList", resFlowupList);
					}

				}
			}
		}

		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
