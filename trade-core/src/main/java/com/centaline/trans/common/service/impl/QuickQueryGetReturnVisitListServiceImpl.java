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
import com.centaline.trans.cases.service.ToTradeChangedCaseService;
import com.centaline.trans.cases.vo.CaseReturnVisitRegistrationVO;
import com.centaline.trans.signroom.vo.ResFlowupVo;

/**
 * @author zhoujp
 * @date 2016年10月19日
 */
public class QuickQueryGetReturnVisitListServiceImpl implements
		CustomDictService {

	@Autowired
	private ToTradeChangedCaseService toTradeChangedCaseService;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			List<CaseReturnVisitRegistrationVO> returnVistiRegistrations = new ArrayList<CaseReturnVisitRegistrationVO>();
			Object historyId = key.get("historyId");
			if (historyId != null) {
				returnVistiRegistrations = toTradeChangedCaseService.queryReturnVisitRegistrations(Long.parseLong(historyId.toString()));
			}
			key.put("val", returnVistiRegistrations);
		}
		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
