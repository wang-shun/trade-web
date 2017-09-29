package com.centaline.trans.eval.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.enums.EvalStatusEnum;
import com.centaline.trans.eval.entity.ToEvaReportProcess;
import com.centaline.trans.eval.repository.ToEvaReportProcessMapper;
import com.centaline.trans.eval.service.ToEvaReportProcessService;

/**
 * @Description:TODO
 * @author：jinwl6
 * @date:2017年9月21日
 * @version:
 */
@Service
public class ToEvaReportProcessServiceImpl implements ToEvaReportProcessService {
    
	@Autowired
	ToEvaReportProcessMapper toEvaReportProcessMapper;
	
	@Override
	public int insertEvaApply(ToEvaReportProcess toEvaReportProcess) {
		toEvaReportProcess.setStatus(EvalStatusEnum.YSQ.getCode());
		return toEvaReportProcessMapper.insertSelective(toEvaReportProcess);
	}

	@Override
	public int updateEvaReport(ToEvaReportProcess toEvaReportProcess) {
		return toEvaReportProcessMapper.updateByPrimaryKeySelective(toEvaReportProcess);
	}

	@Override
	public ToEvaReportProcess selectToEvaReportProcessByCaseCodeAndStatus(String caseCode,String status) {
		return toEvaReportProcessMapper.selectToEvaReportProcessByCaseCodeAndStatus(caseCode);
	}

}
