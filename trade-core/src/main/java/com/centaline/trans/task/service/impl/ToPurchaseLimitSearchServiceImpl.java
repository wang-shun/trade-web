package com.centaline.trans.task.service.impl;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.task.entity.ToPurchaseLimitSearch;
import com.centaline.trans.task.repository.ToPurchaseLimitSearchMapper;
import com.centaline.trans.task.service.ToPurchaseLimitSearchService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToPurchaseLimitSearchServiceImpl implements ToPurchaseLimitSearchService {

	@Autowired
	private ToPurchaseLimitSearchMapper toPurchaseLimitSearchMapper;

	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;

	@Autowired
	private TgGuestInfoService tgGuestInfoService;

	@Override
	public boolean saveToPurchaseLimitSearch( ToPurchaseLimitSearch toPurchaseLimitSearch) {
		if(toPurchaseLimitSearch.getCaseCode()==null || toPurchaseLimitSearch.getCaseCode().intern().length() == 0) {
			return false;
		}
		if(toPurchaseLimitSearch.getPkid() != null) {
			if(toPurchaseLimitSearchMapper.updateByPrimaryKeySelective(toPurchaseLimitSearch) > 0) {
				return true;
			}
		} else {
			if(toPurchaseLimitSearchMapper.findToPlsByCaseCode(toPurchaseLimitSearch.getCaseCode()) == null) {
				if(toPurchaseLimitSearchMapper.insertSelective(toPurchaseLimitSearch) > 0) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public ToPurchaseLimitSearch findToPlsByCaseCode(String caseCode) {
		return toPurchaseLimitSearchMapper.findToPlsByCaseCode(caseCode);
	}

	@Override
	public AjaxResponse saveAndSubmitPurchaseLimit(ToPurchaseLimitSearch toPurchaseLimitSearch, String taskId, String processInstanceId) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		Boolean saveFlag = saveToPurchaseLimitSearch(toPurchaseLimitSearch);

		if(saveFlag){
			List<RestVariable> variables = new ArrayList<RestVariable>();
			ToCase toCase = toCaseService.findToCaseByCaseCode(toPurchaseLimitSearch.getCaseCode());
			workFlowManager.submitTask(variables, taskId, processInstanceId,toCase.getLeadingProcessId(),toPurchaseLimitSearch.getCaseCode());
			int result = tgGuestInfoService.sendMsgHistory(toPurchaseLimitSearch.getCaseCode(),toPurchaseLimitSearch.getPartId());
			if (result >0) {
				ajaxResponse.setMessage("查限购保存成功");
			}else {
				ajaxResponse.setMessage("短信发送失败, 请您线下手工再次发送！");
			}
			ajaxResponse.setSuccess(true);
		} else {
			ajaxResponse.setSuccess(false);
			ajaxResponse.setMessage("保存查限购出错");
		}
		return ajaxResponse;
	}

}
