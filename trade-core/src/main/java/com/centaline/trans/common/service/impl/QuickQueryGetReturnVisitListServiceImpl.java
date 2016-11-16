/**
 * 
 */
package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.centaline.trans.transplan.entity.TtsReturnVisitRegistration;
import com.centaline.trans.transplan.service.TransplanServiceFacade;

/**
 * @author zhoujp
 * @date 2016年10月19日
 */
public class QuickQueryGetReturnVisitListServiceImpl implements
		CustomDictService {

	@Autowired
	private TransplanServiceFacade toTransplanOperateService;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			List<TtsReturnVisitRegistration> returnVistiRegistrations = new ArrayList<TtsReturnVisitRegistration>();
			Object batchId = key.get("batchId");
			if (batchId != null) {
				returnVistiRegistrations = toTransplanOperateService.queryReturnVisitRegistrations(Long.parseLong(batchId.toString()));
			}
			key.put("val", returnVistiRegistrations);
		}
		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}
	@Override
	public Boolean isCacheable(){
    	return false;
    }

}
