package com.centaline.trans.eloan.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.eloan.entity.ToCloseLoan;
import com.centaline.trans.eloan.repository.ToCloseLoanMapper;
import com.centaline.trans.eloan.service.ToCloseLoanService;

@Service
public class ToCloseLoanServiceImpl implements ToCloseLoanService{

	@Autowired
	private ToCloseLoanMapper toCloseLoanMapper;
	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;

	@Override
	public Boolean saveToCloseLoan(ToCloseLoan toCloseLoan) {
		if(!StringUtils.isBlank(toCloseLoan.getCaseCode())) {
			toCloseLoan.setUncloseMoney(toCloseLoan.getUncloseMoney().multiply(new BigDecimal(10000)));
			if(toCloseLoan.getPkid() == null) {
				if(toCloseLoanMapper.findToCloseLoanByCaseCode(toCloseLoan.getCaseCode()) == null) {
				toCloseLoanMapper.insertSelective(toCloseLoan);
				}
			} else {
				toCloseLoanMapper.updateByPrimaryKeySelective(toCloseLoan);
			}
			return true;
		}
		return false;
	}

	@Override
	public ToCloseLoan qureyToCloseLoan(String caseCode) {
		ToCloseLoan toCloseLoan = toCloseLoanMapper.findToCloseLoanByCaseCode(caseCode);
		if(toCloseLoan!=null){
			toCloseLoan.setUncloseMoney(toCloseLoan.getUncloseMoney()!=null?toCloseLoan.getUncloseMoney().divide(new BigDecimal(10000)):null);
		}
		return toCloseLoan;
	}
	@Override
	public String getLoanLostTypeValue(String caseCode) {
		return toCloseLoanMapper.getLoanLostTypeValue(caseCode);
	}

	@Override
	public AjaxResponse saveAndSubmitCloseLoan(ToCloseLoan toCloseLoan, String taskId, String processInstanceId) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		saveToCloseLoan(toCloseLoan);
		/* 流程引擎相关 */
		List<RestVariable> variables = new ArrayList<RestVariable>();
		ToCase toCase = toCaseService.findToCaseByCaseCode(toCloseLoan.getCaseCode());
		workFlowManager.submitTask(variables, taskId, processInstanceId, toCase.getLeadingProcessId(), toCloseLoan.getCaseCode());
		ajaxResponse.setSuccess(true);
		return ajaxResponse;
	}

}
