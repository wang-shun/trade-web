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

/**
 * @author yinjf2
 * @date 2016年8月5日
 */
@Service
public class QuickQueryGetTransactItemCodeListServiceImpl implements
		CustomDictService {

	@Autowired
	private ResFlowupService resFlowupService;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			List<String> transactItemList = new ArrayList<String>();

			Object transactItemCodeObj = key.get("transactItemCode");
			if (transactItemCodeObj != null) {
				String transactItemCode = transactItemCodeObj.toString();
				String[] arrayTransactItemCode = transactItemCode.split(",");

				for (int i = 0; i < arrayTransactItemCode.length; i++) {
					transactItemList.add(arrayTransactItemCode[i]);
				}
			}

			key.put("val", transactItemList);
		}

		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
