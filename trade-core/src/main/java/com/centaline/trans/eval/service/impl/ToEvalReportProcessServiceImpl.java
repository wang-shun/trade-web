package com.centaline.trans.eval.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.common.enums.EvalStatusEnum;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.repository.ToEvalReportProcessMapper;
import com.centaline.trans.eval.service.ToEvalReportProcessService;
import com.centaline.trans.utils.DateUtil;

@Service
public class ToEvalReportProcessServiceImpl implements ToEvalReportProcessService {
	
	@Autowired
	private ToEvalReportProcessMapper toEvalReportProcessMapper;
	@Autowired
	ToCaseMapper toCaseMapper;
	
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(ToEvalReportProcess record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(ToEvalReportProcess record) {
		return toEvalReportProcessMapper.insertSelective(record);
	}

	@Override
	public ToEvalReportProcess selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(ToEvalReportProcess record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(ToEvalReportProcess record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ToEvalReportProcess findToEvalReportProcessByCaseCode(String caseCode) {
		return toEvalReportProcessMapper.findToEvalReportProcessByCaseCode(caseCode);
	}

	@Override
	public ToEvalReportProcess findToEvalReportProcessByEvalCode(String evalCode) {
		return toEvalReportProcessMapper.findToEvalReportProcessByEvalCode(evalCode);
	}


	@Override
	public int updateChangeInfoByEvalCode(String evalCode,String changeInfo) {
		ToEvalReportProcess toEvalReportProcess = new ToEvalReportProcess();
		toEvalReportProcess.setChangeInfo(changeInfo);
		toEvalReportProcess.setEvaCode(evalCode);
		return toEvalReportProcessMapper.updateChangeInfoByEvalCode(toEvalReportProcess);
	}

	
	@Override
	public int updateStatusByEvalCode(String status,String evalCode) {
		ToEvalReportProcess toEvalReportProcess = new ToEvalReportProcess();
		toEvalReportProcess.setStatus(status);
		toEvalReportProcess.setEvaCode(evalCode);
		return toEvalReportProcessMapper.updateStatusByEvalCode(toEvalReportProcess);
	}
	
	@Override
	public int insertEvaApply(ToEvalReportProcess toEvalReportProcess) {
		BigDecimal ornginPrice = toEvalReportProcess.getOrnginPrice();
		BigDecimal inquiryResult = toEvalReportProcess.getInquiryResult();
		toEvalReportProcess.setOrnginPrice(ornginPrice.multiply(new BigDecimal(10000.00)));
		toEvalReportProcess.setInquiryResult(inquiryResult.multiply(new BigDecimal(10000.00)));
		toEvalReportProcess.setStatus(EvalStatusEnum.YSQ.getCode());
		toEvalReportProcess.setEvaCode(getEvalCode("120000"));//TODO 这里暂时写死城市代码
		toEvalReportProcess.setSysCreateTime(new Date());
		return toEvalReportProcessMapper.insertSelective(toEvalReportProcess);
	}

	@Override
	public int updateEvaReport(ToEvalReportProcess toEvalReportProcess) {
		return toEvalReportProcessMapper.updateEvaReportByEvaCode(toEvalReportProcess);
	}

	@Override
	public ToEvalReportProcess selectToEvaReportProcessByCaseCodeAndStatus(String caseCode,String status) {
		return toEvalReportProcessMapper.selectToEvaReportProcessByCaseCodeAndStatus(caseCode);
	}
	
	@Override
	public ToEvalReportProcess selectToEvaReportProcessByEvaCode(String evalCode) {
		return toEvalReportProcessMapper.findToEvalReportProcessByEvalCode(evalCode);
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

	/* (non-Javadoc)
	 * @see com.centaline.trans.eval.service.ToEvalReportProcessService#selecttoEvalReportProcessByCaseCodeAndStatus(java.lang.String, java.lang.String)
	 */
	@Override
	public ToEvalReportProcess selecttoEvalReportProcessByCaseCodeAndStatus(String caseCode, String status) {
		return toEvalReportProcessMapper.selectToEvaReportProcessByCaseCodeAndStatus(caseCode);
	}

	/* (non-Javadoc)
	 * @see com.centaline.trans.eval.service.ToEvalReportProcessService#updateEvaReportByEvaCode(com.centaline.trans.eval.entity.ToEvalReportProcess)
	 */
	@Override
	public int updateEvaReportByEvaCode(ToEvalReportProcess record) {
		return toEvalReportProcessMapper.updateEvaReportByEvaCode(record);
	}

}
