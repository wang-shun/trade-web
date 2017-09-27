package com.centaline.trans.eval.service;

import com.centaline.trans.eval.entity.ToEvaReportProcess;

/**
 * @Description:评估流程服务
 * @author：jinwl6
 * @date:2017年9月21日
 * @version:
 */
public interface ToEvaReportProcessService {
	
	 /**
	  * 保存评估申请信息
	  * @param toEvaReportProcess
	  * @return
	  */
	 int insertEvaApply(ToEvaReportProcess toEvaReportProcess);
	 
	 /**
	  * 根据案件编号查询评估申请信息
	  * @param caseCode
	  * @return
	  */
	 ToEvaReportProcess selectToEvaReportProcessByCaseCode(String caseCode);
	 
	 /**
	  * 保存评估上报信息
	  * @param toEvaReportProcess
	  * @return
	  */
	 int updateEvaReport(ToEvaReportProcess toEvaReportProcess);
	 
  
}
