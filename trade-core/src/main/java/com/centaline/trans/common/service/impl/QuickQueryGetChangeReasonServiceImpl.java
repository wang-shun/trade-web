/**
 * 
 */
package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.centaline.trans.transplan.entity.TtsReturnVisitRegistration;
import com.centaline.trans.transplan.service.TransplanServiceFacade;

/**
 * 组成形如“公积金贷款：原因”
 * @author zhoujp
 * @date 2016年11月15日
 */
public class QuickQueryGetChangeReasonServiceImpl implements
		CustomDictService {

	@Autowired
	private UamBasedataService uamBasedataService;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			Object partCode = key.get("PART_CODE");
			Object changeRes = key.get("CHANGE_REASON");
			StringBuilder  sb = new StringBuilder ("");
			if(partCode!=null){
				sb.append(uamBasedataService.getDictValue("part_code", partCode.toString()));
			}
			if (changeRes != null) {
				sb.append("："+changeRes);
			}
			key.put("val", sb.toString());
		}
		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}
	

}
