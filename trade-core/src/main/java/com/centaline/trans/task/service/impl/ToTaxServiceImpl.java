package com.centaline.trans.task.service.impl;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.task.entity.ToTax;
import com.centaline.trans.task.repository.ToTaxMapper;
import com.centaline.trans.task.service.ToTaxService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToTaxServiceImpl implements ToTaxService{


	@Autowired
	private ToTaxMapper toTaxMapper;
	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;

	@Override
	public boolean saveToTax(ToTax toTax) {
		if(StringUtils.isBlank(toTax.getCaseCode())) {
			return false;
		}
		if(toTax.getPkid() != null) {
			if(toTaxMapper.updateByPrimaryKeySelective(toTax) > 0) {
				return true;
			}
		} else {
			if(toTaxMapper.findToTaxByCaseCode(toTax.getCaseCode()) == null) {
				if(toTaxMapper.insertSelective(toTax) > 0) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public ToTax findToTaxByCaseCode(String caseCode) {
		return toTaxMapper.findToTaxByCaseCode(caseCode);
	}
	@Override
	public AjaxResponse saveAndSubmitTax(ToTax toTax, String taskId, String processInstanceId,String partCode) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		Boolean saveFlag = saveToTax(toTax);
		if(saveFlag){
			List<RestVariable> variables = new ArrayList<RestVariable>();
			ToCase toCase = toCaseService.findToCaseByCaseCode(toTax.getCaseCode());
			workFlowManager.submitTask(variables, taskId, processInstanceId,toCase.getLeadingProcessId(), toTax.getCaseCode());
			ajaxResponse.setSuccess(true);
		} else {
			ajaxResponse.setSuccess(false);
			ajaxResponse.setMessage("保存审税出错");
		}
		return ajaxResponse;
	}
}
