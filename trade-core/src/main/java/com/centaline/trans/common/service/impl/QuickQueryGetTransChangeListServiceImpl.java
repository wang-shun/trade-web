/**
 * 
 */
package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.centaline.trans.transplan.service.ToTransplanOperateService;
import com.centaline.trans.transplan.vo.TsTransPlanHistoryVO;

/**
 * @author zhoujp
 * @date 2016年11月15日
 */
public class QuickQueryGetTransChangeListServiceImpl implements
		CustomDictService {

	@Autowired
	private ToTransplanOperateService toTransplanOperateService;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			List<TsTransPlanHistoryVO> ttphbs = new ArrayList<TsTransPlanHistoryVO>();
			Object batchId = key.get("batchId");
			Object caseCode = key.get("CASE_CODE");
			if (batchId != null && caseCode!=null) {
				Map map = new HashMap();
				TsTransPlanHistoryVO tsTransPlanHistoryVO = new TsTransPlanHistoryVO();
				tsTransPlanHistoryVO.setBatchId(Long.parseLong(batchId.toString()));
				tsTransPlanHistoryVO.setCaseCode(caseCode.toString());
				ttphbs = toTransplanOperateService.queryTtsTransPlanHistorys(tsTransPlanHistoryVO);
			}
			key.put("val", ttphbs);
		}
		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
