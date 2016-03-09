package com.centaline.trans.mortgage.service;

import java.util.List;

import com.centaline.trans.mortgage.entity.ToEvaReport;
import com.centaline.trans.remote.vo.MortgageAttamentVo;
import com.centaline.trans.task.vo.ProcessInstanceVO;

public interface ToEvaReportService {

	/**
	 * 保存报告信息
	 * @param toEvaReport
	 */
	void saveToEvaReport(ToEvaReport toEvaReport);
	
	/**
	 * 根据询价编号查询报告单信息
	 * @param toEvaReport
	 * @return
	 */
	List<ToEvaReport> findToEvaReportByEvaCode(ToEvaReport toEvaReport);
	
	/**
	 * 修改报告单信息
	 * @param toEvaReport
	 */
	void updateToEvaReport(ToEvaReport toEvaReport);
	
	/**
	 * 根据案件编号查询最终报告单
	 * @param caseCode
	 * @return
	 */
	ToEvaReport findFinalComByCaseCode(String caseCode);
	
	ToEvaReport findByProcessId(String processId);

	
	/**
	 * 根据主键查询报告单信息
	 * @param pkid
	 * @return
	 */
	ToEvaReport findToEvaReportById(Long pkid);
	
	/**
	 * 提交报告申请（非egu）
	 * @param toEvaReport
	 * @param processInstanceVO
	 * @param mortgageAttament
	 */
	void submitEvaReport(ToEvaReport toEvaReport,ProcessInstanceVO processInstanceVO,MortgageAttamentVo mortgageAttament);
}
