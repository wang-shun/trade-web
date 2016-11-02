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
public class QuickQueryGetFlowupInfoListServiceImpl implements
		CustomDictService {

	@Autowired
	private ResFlowupService resFlowupService;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			List<ResFlowupVo> resFlowupList = new ArrayList<ResFlowupVo>();

			Object resIdObj = key.get("resId");
			if (resIdObj != null) {
				Long resId = Long.parseLong(resIdObj.toString());

				resFlowupList = resFlowupService.getResFlowupListByResId(resId);

			}

			key.put("val", resFlowupList);
		}

		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
