package com.centaline.trans.eval.service;

import javax.servlet.http.HttpServletRequest;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.eval.entity.ToEvalReportProcess;

/**
 * @Description:评估主流程服务( 申请->上报)
 * @author：jinwl6
 * @date:2017年10月24日
 * @version:
 */
public interface EvaProcessService {
	 /**
	  * 评估申请初始化
	  * @param request
	  * @param caseCode
	  * @param evaCode
	  * @param taskitem
	  * @param businessKey
	  */
	 public void initApply(HttpServletRequest request, String caseCode,String evaCode,String taskitem,String businessKey);
	 
	 /**
	  * 评估上报初始化
	  * @param request
	  * @param caseCode
	  * @param evaCode
	  * @param taskitem
	  * @param businessKey
	  */
	 public void initReport(HttpServletRequest request,String taskitem,String businessKey,String evaCode,String source);
	 
	 /**
	  * 评估出具初始化
	  * @param request
	  * @param caseCode
	  * @param evaCode
	  * @param taskitem
	  * @param businessKey
	  */
	 public void initIssue(HttpServletRequest request,String taskitem,String businessKey,String evaCode,String source);
	 
	 /**评估使用初始化
	  * 
	  * @param request
	  * @param caseCode
	  * @param evaCode
	  * @param taskitem
	  * @param businessKey
	  */
	 public void initUsed(HttpServletRequest request,String taskitem,String businessKey,String evaCode,String source);
	
	/**
	 * 评估申请提交
	 * @param toEvalReportProcess
	 * @return
	 */
	AjaxResponse<?> submitEvalApply(ToEvalReportProcess toEvalReportProcess);
	
	
	/**
	 * 评估上报提交
	 * @param toEvalReportProcess
	 * @return
	 */
	AjaxResponse<?> submitReport(ToEvalReportProcess toEvalReportProcess,String taskId);
	
	/**
	 * 评估出具提交
	 * @param toEvalReportProcess
	 * @return
	 */
	AjaxResponse<?> submitIssue(ToEvalReportProcess toEvalReportProcess,String taskId);
	
	/**
	 * 评估使用提交
	 * @param toEvalReportProcess
	 * @return
	 */
	AjaxResponse<?> submitUsed(ToEvalReportProcess toEvalReportProcess,String taskId);
	
	/**
	 * 评估申请保存
	 * @param toEvalReportProcess
	 * @return
	 */
	AjaxResponse<?> saveEvalApply(ToEvalReportProcess toEvalReportProcess);
	
	
	/**
	 * 评估上报保存
	 * @param toEvalReportProcess
	 * @return
	 */
	AjaxResponse<?> saveReport(ToEvalReportProcess toEvalReportProcess,String taskId);
	
	/**
	 * 评估出具保存
	 * @param toEvalReportProcess
	 * @return
	 */
	AjaxResponse<?> saveIssue(ToEvalReportProcess toEvalReportProcess,String taskId);
	
	/**
	 * 评估使用保存
	 * @param toEvalReportProcess
	 * @return
	 */
	AjaxResponse<?> saveUsed(ToEvalReportProcess toEvalReportProcess,String taskId);
	
	/**
	 * 评估单入结算表
	 * @param toEvalReportProcess
	 * @return
	 */
	AjaxResponse<?> saveEvalToSettle(String evals);

}
