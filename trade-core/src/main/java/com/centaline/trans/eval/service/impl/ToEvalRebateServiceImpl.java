/**
 * 
 */
package com.centaline.trans.eval.service.impl;

import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.eval.entity.ToEvalRebate;
import com.centaline.trans.eval.repository.ToEvalRebateMapper;
import com.centaline.trans.eval.service.ToEvalRebateService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ToApproveRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ToEvalRebateServiceImpl implements ToEvalRebateService {

	@Autowired
	ToEvalRebateMapper toEvalRebateMapper;

	@Autowired
	ToApproveRecordService toApproveRecordService;

	@Autowired
	private WorkFlowManager workFlowManager;

	@Override
	public int insertSelective(ToEvalRebate record) {
		return toEvalRebateMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(ToEvalRebate record) {
		return toEvalRebateMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public ToEvalRebate findToEvalRebateByCaseCode(String caseCode) {
		return toEvalRebateMapper.findToEvalRebateByCaseCode(caseCode);
	}

	@Override
	public ToEvalRebate selectByPrimaryKey(Long pkid) {
		return toEvalRebateMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public void assistantApprove(ToEvalRebate rebate, ToApproveRecord record,boolean approve) {
		toEvalRebateMapper.updateByPrimaryKeySelective(rebate);
		toApproveRecordService.saveToApproveRecord(record);
		List<RestVariable> variables = new ArrayList<>();
		variables.add(new RestVariable("approve",approve));
		workFlowManager.submitTask(variables,record.getTaskId(),record.getProcessInstance(),record.getOperator(),record.getCaseCode());
	}
}
