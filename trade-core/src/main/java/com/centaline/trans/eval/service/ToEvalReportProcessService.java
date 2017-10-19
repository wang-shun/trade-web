package com.centaline.trans.eval.service;

import com.centaline.trans.eval.entity.ToEvalReportProcess;
/***
 * 
 * @author wbwangxj
 *
 */
public interface ToEvalReportProcessService {
	int deleteByPrimaryKey(Long pkid);

    int insert(ToEvalReportProcess record);

    int insertSelective(ToEvalReportProcess record);

    ToEvalReportProcess selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvalReportProcess record);

    int updateByPrimaryKey(ToEvalReportProcess record);
    
    ToEvalReportProcess findToEvalReportProcessByCaseCode(String caseCode);
    
    ToEvalReportProcess findToEvalReportProcessByEvalCode(String evalCode);
    
    int updateEvalPropertyByCaseCode(String caseCode,String evalProperty);
    
    int updateEvalPropertyByEvalCode(String evalCode,String evalProperty);
    
    int deleteEvalPropertyByEvalCode(String evalCode);
    
    int updateStatusByEvalCode(String status,String evalCode);
    
    /**
	  * 保存评估申请信息
	  * @param toEvaReportProcess
	  * @return
	  * @author jinwl6
	  */
	 int insertEvaApply(ToEvalReportProcess toEvalReportProcess);
	 
	 /**
	  * 根据案件编号和和评估状态查询评估申请信息
	  * @param caseCode
	  * @return
	  */
	 ToEvalReportProcess selectToEvaReportProcessByCaseCodeAndStatus(String caseCode,String status);
	 
	 /**
	  * 保存评估上报信息
	  * @param toEvaReportProcess
	  * @return
	  */
	 int updateEvaReport(ToEvalReportProcess toEvalReportProcess);
	 
	 /**
	  * 根据评估单编号查询评估信息
	  * @param evalCode
	  * @return
	  */
	 ToEvalReportProcess selectToEvaReportProcessByEvaCode(String evalCode);
	 
	 /**
	  * 根据案件号和状态查询
	  * @param toEvalReportProcess
	  * @return
	  */
	 ToEvalReportProcess selecttoEvalReportProcessByCaseCodeAndStatus(String caseCode,String status);
	 
}
