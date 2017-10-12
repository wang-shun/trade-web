package com.centaline.trans.eval.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.common.enums.EvalStatusEnum;
import com.centaline.trans.eval.entity.ToEvaReportProcess;
import com.centaline.trans.eval.repository.ToEvaReportProcessMapper;
import com.centaline.trans.eval.service.ToEvaReportProcessService;
import com.centaline.trans.utils.DateUtil;

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
	@Autowired
	ToCaseMapper toCaseMapper;
	
	@Override
	public int insertEvaApply(ToEvaReportProcess toEvaReportProcess) {
		toEvaReportProcess.setStatus(EvalStatusEnum.YSQ.getCode());
		toEvaReportProcess.setEvaCode(getEvalCode("120000"));//TODO 这里暂时写死城市代码
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
	
	/**
	 * 获取EvalCode方法
	 *
	 * @return
	 */
	private String getEvalCode(String cityCode) {
		StringBuilder s = new StringBuilder();
		//TODO 各个城市的案件头
		switch (cityCode) {
			case "120000":
				s.append("PG-TJ-");
				break;//天津
			case "110000":
				s.append("PG-BJ-");
				break;//北京
			default:
				s.append("PGCode");
				break;
		}
		s.append(DateUtil.getFormatDate(new Date(), "yyyyMM"));
		s.append(toCaseMapper.nextCaseCodeNumber());
		return s.toString();
	}

}
