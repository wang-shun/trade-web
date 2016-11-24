/**
 * 
 */
package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.quickQuery.service.CustomDictService;
import com.centaline.trans.signroom.service.ResFlowupService;
import com.centaline.trans.signroom.vo.ResFlowupVo;

/**
 * @author yinjf2
 * @date 2016年8月5日
 */
@Service
public class QuickQueryGetResInfoServiceImpl implements CustomDictService {

	@Autowired
	private ResFlowupService resFlowupService;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			List<ResFlowupVo> resFlowupList = new ArrayList<ResFlowupVo>();
			List<String> transactItemList = new ArrayList<String>();

			Object checkInTimeObj = key.get("checkInTime");
			Object checkOutTimeObj = key.get("checkOutTime");
			Object startTimeObj = key.get("startTime");
			Object endTimeObj = key.get("endTime");
			Object transactItemCodeObj = key.get("transactItemCode");
			Object resIdObj = key.get("resId");

			String actCheckInTime = ""; // 格式化签到时间
			String actCheckOutTime = ""; // 格式化签退时间
			String actStartTime = ""; // 格式化预约开始时间
			String actEndTime = ""; // 格式化预约结束时间

			// 格式化签到时间
			if (checkInTimeObj != null) {
				String strCheckInTime = checkInTimeObj.toString();

				actCheckInTime = strCheckInTime.substring(
						strCheckInTime.indexOf(" ") + 1,
						strCheckInTime.lastIndexOf(":"));
			}

			// 格式化签退时间
			if (checkOutTimeObj != null) {
				String strCheckOutTime = checkOutTimeObj.toString();

				actCheckOutTime = strCheckOutTime.substring(
						strCheckOutTime.indexOf(" ") + 1,
						strCheckOutTime.lastIndexOf(":"));
			}

			// 格式化预约开始时间
			if (startTimeObj != null) {
				String strStartTime = startTimeObj.toString();

				actStartTime = strStartTime.substring(
						strStartTime.indexOf(" ") + 1,
						strStartTime.lastIndexOf(":"));
			}

			// 格式化预约结束时间
			if (endTimeObj != null) {
				String strEndTime = endTimeObj.toString();

				actEndTime = strEndTime.substring(strEndTime.indexOf(" ") + 1,
						strEndTime.lastIndexOf(":"));
			}

			// 获取办理事项信息
			if (transactItemCodeObj != null) {
				String transactItemCode = transactItemCodeObj.toString();
				String[] arrayTransactItemCode = transactItemCode.split(",");

				for (int i = 0; i < arrayTransactItemCode.length; i++) {
					transactItemList.add(arrayTransactItemCode[i]);
				}
			}

			// 获取跟进信息列表
			if (resIdObj != null) {
				Long resId = Long.parseLong(resIdObj.toString());

				resFlowupList = resFlowupService.getResFlowupListByResId(resId);
			}

			key.put("actCheckInTime", actCheckInTime);
			key.put("actCheckOutTime", actCheckOutTime);
			key.put("actStartTime", actStartTime);
			key.put("actEndTime", actEndTime);
			key.put("transactItemCodeList", transactItemList);
			key.put("flowupInfoList", resFlowupList);
		}

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
