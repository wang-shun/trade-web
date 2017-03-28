/**
 * 
 */
package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.quickQuery.service.CustomDictService;

/**
 * 签约室管理--获取预约部分信息
 * 
 * @author yinjf2
 * @date 2016年8月5日
 */
@Service
public class QuickQueryGetResInfoServiceImpl implements CustomDictService {

	/**
	 * 根据签约室安排id集合获取安排信息和房间信息服务接口
	 */
	@Autowired
	private QuickQueryGetScheduleAndRoomInfoByScheduleIdServiceImpl quickQueryGetScheduleAndRoomInfoByScheduleId;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {

		// 根据签约室安排id集合获取安排信息和房间信息
		quickQueryGetScheduleAndRoomInfoByScheduleId.findDicts(keys);

		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

	@Override
	public Boolean isCacheable() {
		return false;
	}
}
