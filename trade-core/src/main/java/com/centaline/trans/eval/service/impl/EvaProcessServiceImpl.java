package com.centaline.trans.eval.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.enums.CaseParticipantEnum;
import com.centaline.trans.common.enums.EVAPricingStatusEnum;
import com.centaline.trans.common.enums.EvalPropertyEnum;
import com.centaline.trans.common.enums.EvalStatusEnum;
import com.centaline.trans.common.enums.FeeChangeTypeEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.evaPricing.entity.ToEvaPricingVo;
import com.centaline.trans.evaPricing.repository.ToEvaPricingMapper;
import com.centaline.trans.eval.entity.ToEvaInvoice;
import com.centaline.trans.eval.entity.ToEvaRefund;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.entity.ToEvalSettle;
import com.centaline.trans.eval.repository.ToEvaInvoiceMapper;
import com.centaline.trans.eval.repository.ToEvaRefundMapper;
import com.centaline.trans.eval.service.EvaProcessService;
import com.centaline.trans.eval.service.ToEvalRebateService;
import com.centaline.trans.eval.service.ToEvalReportProcessService;
import com.centaline.trans.eval.service.ToEvalSettleService;
import com.centaline.trans.task.entity.ToSign;
import com.centaline.trans.task.repository.ToSignMapper;

/**
 * @Description:评估主流程服务( 申请->上报)实现
 * @author：jinwl6
 * @date:2017年10月24日
 * @version:
 */
@Service
public class EvaProcessServiceImpl implements EvaProcessService {
	@Autowired
	private ToEvalReportProcessService toEvalReportProcessService;
	@Autowired(required = true)
	private PropertyUtilsService propertyUtilsService;
	@Autowired
	private UamSessionService  uamSessionService;
	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private TaskService taskService;
	@Autowired
	ToCaseParticipantMapper toCaseParticipantMapper;
	@Autowired
	ToEvalRebateService toEvalRebateService; //评估返利
	@Autowired
	private ToCaseService toCaseService;
	@Autowired
	private ToEvaPricingMapper toEvaPricingMapper;
	@Autowired
    private ToSignMapper toSignMapper;
	@Autowired
	private ToEvaInvoiceMapper toEvaInvoiceMapper;
	@Autowired
	private ToEvaRefundMapper toEvaRefundMapper;
	@Autowired
	ToEvalSettleService toEvalSettleService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void initApply(HttpServletRequest request, String caseCode, String evaCode, String taskitem,
			String businessKey) {
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		ToSign toSign = toSignMapper.findToSignByCaseCode(caseCode); 
		ToEvaPricingVo toEvaPricingVo=null;
		if(evaCode!=null && evaCode.length()>0){
		        toEvaPricingVo = toEvaPricingMapper.findEvaPricingDetailByEvaCode(evaCode);//查询询价信息
		}else {
			List<ToEvaPricingVo> teps = toEvaPricingMapper.findEvaPricingDetailByCaseCode(caseCode);
			if(teps != null && teps.size() > 0) {
				toEvaPricingVo = new ToEvaPricingVo();
				for (ToEvaPricingVo tep : teps) {
					if(tep.getCompleteTime() != null && EVAPricingStatusEnum.COMPLETE.getCode().equals(tep.getStatus())) {
						if(toEvaPricingVo.getCompleteTime() != null) {
							if(tep.getCompleteTime().after(toEvaPricingVo.getCompleteTime())) {
								toEvaPricingVo = tep;
							}
						}else {
							toEvaPricingVo = tep;
						}
					}
				}
				if(toEvaPricingVo.getCompleteTime() == null) {
					toEvaPricingVo = null;
				}
			}
		}
		ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.findToEvalReportProcessByCaseCode(caseCode);
		request.setAttribute("toEvalReportProcessVo", toEvalReportProcess);
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("toEvaPricingVo", toEvaPricingVo);
		request.setAttribute("caseCode", caseCode);
		request.setAttribute("conPrice", toSign==null?null:toSign.getConPrice());
	}
	
	@Override
	public void initReport(HttpServletRequest request,String taskitem,
			String businessKey,String evaCode,String source) {
		String caseCode = getCaseCodeByCondition(source,evaCode,businessKey);
		ToSign toSign = toSignMapper.findToSignByCaseCode(caseCode);
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		//查询评估申请信息
		ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.selecttoEvalReportProcessByCaseCodeAndStatus(caseCode,EvalStatusEnum.YSQ.getCode());
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("toEvalReportProcessVo", toEvalReportProcess);
		request.setAttribute("caseCode", caseCode);
		request.setAttribute("conPrice", toSign==null?null:toSign.getConPrice());
	}

	@Override
	public void initIssue(HttpServletRequest request, String taskitem,
			String businessKey,String evaCode,String source) {

		String caseCode = getCaseCodeByCondition(source,evaCode,businessKey);
		ToSign toSign = toSignMapper.findToSignByCaseCode(caseCode);
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		//查询评估申请信息
		ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.selecttoEvalReportProcessByCaseCodeAndStatus(caseCode,EvalStatusEnum.YSB.getCode());
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("toEvalReportProcessVo", toEvalReportProcess);
		request.setAttribute("caseCode", caseCode);
		request.setAttribute("taskitem", taskitem);
		request.setAttribute("conPrice", toSign==null?null:toSign.getConPrice());
		
	}

	@Override
	public void initUsed(HttpServletRequest request, String taskitem,
			String businessKey,String evaCode,String source) {
		String caseCode = getCaseCodeByCondition(source,evaCode,businessKey);
		ToSign toSign = toSignMapper.findToSignByCaseCode(caseCode);
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		//查询评估申请
		ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.selecttoEvalReportProcessByCaseCodeAndStatus(caseCode,EvalStatusEnum.YCNBG.getCode());
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("toEvalReportProcessVo", toEvalReportProcess);
		request.setAttribute("caseCode", caseCode);
		request.setAttribute("conPrice", toSign==null?null:toSign.getConPrice());
	}
	
	@Override
	public AjaxResponse<?> submitEvalApply(ToEvalReportProcess toEvalReportProcess) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
		        SessionUser user = uamSessionService.getSessionUser();
				ToEvalReportProcess erp = toEvalReportProcessService.findToEvalReportProcessByCaseCode(toEvalReportProcess.getCaseCode());
				if(erp==null){
					//保存申请信息
					toEvalReportProcessService.insertEvaApply(toEvalReportProcess);
				}else{
					BigDecimal ornginPrice = toEvalReportProcess.getOrnginPrice();
					BigDecimal inquiryResult = toEvalReportProcess.getInquiryResult();
					toEvalReportProcess.setInquiryResult(inquiryResult.multiply(new BigDecimal(10000.00)));
					toEvalReportProcess.setStatus(EvalStatusEnum.YSQ.getCode());
					toEvalReportProcess.setEvaCode(erp.getEvaCode());
					toEvalReportProcess.setOrnginPrice(ornginPrice.multiply(new BigDecimal(10000.00)));
					toEvalReportProcessService.updateEvaReportByEvaCode(toEvalReportProcess);
				}
		
				//启动流程引擎
				ProcessInstance process = new ProcessInstance();
				process.setBusinessKey(toEvalReportProcess.getEvaCode());
		    	process.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_PROCESS.getCode()));
				//流程引擎相关
		    	ToCaseParticipant toCaseParticipant = new ToCaseParticipant();
		    	toCaseParticipant.setCaseCode(toEvalReportProcess.getCaseCode());
				toCaseParticipant.setPosition(CaseParticipantEnum.ASSISTANT.getCode());
				ToCaseParticipant tp =  toCaseParticipantMapper.selectByCondition(toCaseParticipant).get(0);
				
		    	Map<String, Object> defValsMap = new HashMap<String,Object>();
		    	defValsMap.put(CaseParticipantEnum.LOAN.getCode(), user.getUsername());
		    	defValsMap.put(CaseParticipantEnum.ASSISTANT.getCode(), tp.getUserName());
		    	
		    	StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId(propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_PROCESS.getCode()), 
		    			toEvalReportProcess.getEvaCode(),defValsMap);
		    	
		    	//入交易系统工作流表
		    	ToWorkFlow toWorkFlow = new ToWorkFlow();
		    	toWorkFlow.setInstCode(processInstance.getId());
		    	toWorkFlow.setBusinessKey(WorkFlowEnum.EVAL_PROCESS.getCode());
		    	toWorkFlow.setProcessDefinitionId(processInstance.getProcessDefinitionId());
		    	toWorkFlow.setProcessOwner(user.getId());
		    	toWorkFlow.setCaseCode(toEvalReportProcess.getCaseCode());
		    	toWorkFlow.setBizCode(toEvalReportProcess.getEvaCode());
		    	toWorkFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
		    	toWorkFlowService.insertSelective(toWorkFlow);
		    	
		    	//申请人、办理人入表
		    	
		    	
		    	TaskVo taskvo = (TaskVo) taskService.listTasks(processInstance.getId()).getData().get(0);
		    	taskService.submitTask(String.valueOf(taskvo.getId()));
		
				//启动评估返利流程 by:yinchao 2017-10-21
				toEvalRebateService.startRebateFlow(toEvalReportProcess.getCaseCode(),toEvalReportProcess.getEvaCode());
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage(e.getMessage());
			logger.error("评估申请提交失败！",e);
		}
		return response;
	}

	@Override
	public AjaxResponse<?> submitReport(ToEvalReportProcess toEvalReportProcess,String taskId) {
		   AjaxResponse<String> response = new AjaxResponse<String>();
		   try{
			    //评估上报保存
				toEvalReportProcess.setStatus(EvalStatusEnum.YSB.getCode());
				toEvalReportProcessService.updateEvaReport(toEvalReportProcess);
				//提交任务
				Map<String, Object> variables = new HashMap<String, Object>();
				SessionUser user = uamSessionService.getSessionUser();
				variables.put("assistant", user.getUsername());
				taskService.submitTask(taskId,variables);
		   }catch(Exception e){
			   response.setSuccess(false);
			   response.setMessage(e.getMessage());
			   logger.error("评估上报提交失败！",e);
		   }
		return response;
	}

	@Override
	public AjaxResponse<?> submitIssue(ToEvalReportProcess toEvalReportProcess, String taskId) {
		   AjaxResponse<String> response = new AjaxResponse<String>();
		   try{
				//评估出具信息保存
			     BigDecimal evaPrice = toEvalReportProcess.getEvaPrice();
				toEvalReportProcess.setStatus(EvalStatusEnum.YCNBG.getCode());
				toEvalReportProcess.setEvaPrice(evaPrice.multiply(new BigDecimal(10000.00)));
				toEvalReportProcessService.updateEvaReport(toEvalReportProcess);
				//提交任务
				SessionUser user = uamSessionService.getSessionUser();
				//提交任务
				Map<String, Object> variables = new HashMap<String, Object>();
				variables.put("assistant", user.getUsername());
				taskService.submitTask(taskId,variables);
		   }catch(Exception e){
			   response.setSuccess(false);
			   response.setMessage(e.getMessage());
			   logger.error("出具评估报告提交失败！",e);
		   }
		return response;
	}

	@Override
	public AjaxResponse<?> submitUsed(ToEvalReportProcess toEvalReportProcess, String taskId,String processInstanceId) {
		   AjaxResponse<String> response = new AjaxResponse<String>();
		   try{
		        //评估使用信息保存
				toEvalReportProcess.setStatus(EvalStatusEnum.YSYBG.getCode());
				toEvalReportProcess.setSysFinshTime(new Date());
				toEvalReportProcessService.updateEvaReport(toEvalReportProcess);
				taskService.submitTask(taskId,null);
				ToWorkFlow wf = new ToWorkFlow();
				wf.setBusinessKey(WorkFlowEnum.EVAL_PROCESS.getCode());
				wf.setInstCode(processInstanceId);
				wf.setStatus(WorkFlowStatus.COMPLETE.getCode());
				toWorkFlowService.updateWorkFlowByInstCode(wf);
		   }catch(Exception e){
			   response.setSuccess(false);
			   response.setMessage(e.getMessage());
			   logger.error("评估使用提交失败！",e);
		   }
		return response;
	}

	@Override
	public AjaxResponse<?> saveEvalApply(ToEvalReportProcess toEvalReportProcess) {
		 AjaxResponse<String> response = new AjaxResponse<String>();
		 try{
				ToEvalReportProcess erp = toEvalReportProcessService.findToEvalReportProcessByCaseCode(toEvalReportProcess.getCaseCode());
				BigDecimal ornginPrice = toEvalReportProcess.getOrnginPrice();
				BigDecimal inquiryResult = toEvalReportProcess.getInquiryResult();
				toEvalReportProcess.setInquiryResult(inquiryResult.multiply(new BigDecimal(10000.00)));
				toEvalReportProcess.setEvaCode(erp.getEvaCode());
				toEvalReportProcess.setOrnginPrice(ornginPrice.multiply(new BigDecimal(10000.00)));
				toEvalReportProcessService.updateEvaReportByEvaCode(toEvalReportProcess);
		 }catch(Exception e){
			 response.setSuccess(false);
			   response.setMessage(e.getMessage());
			   logger.error("评估申请保存失败！",e);
		 }
		return response;
	}

	@Override
	public AjaxResponse<?> saveReport(ToEvalReportProcess toEvalReportProcess, String taskId) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		 try{
			    //评估上报保存
				toEvalReportProcess.setStatus(EvalStatusEnum.YSB.getCode());
				toEvalReportProcessService.updateEvaReport(toEvalReportProcess);
		 }catch(Exception e){
			 response.setSuccess(false);
			   response.setMessage(e.getMessage());
			   logger.error("评估申请保存失败！",e);
		 }
		return response;
	}

	@Override
	public AjaxResponse<?> saveIssue(ToEvalReportProcess toEvalReportProcess, String taskId) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		 try{
			 //评估出具信息保存
		     BigDecimal evaPrice = toEvalReportProcess.getEvaPrice();
			 toEvalReportProcess.setStatus(EvalStatusEnum.YCNBG.getCode());
			 toEvalReportProcess.setEvaPrice(evaPrice.multiply(new BigDecimal(10000.00)));
			 toEvalReportProcessService.updateEvaReport(toEvalReportProcess);
		 }catch(Exception e){
			 response.setSuccess(false);
			   response.setMessage(e.getMessage());
			   logger.error("评估申请保存失败！",e);
		 }
		return response;
	}

	@Override
	public AjaxResponse<?> saveUsed(ToEvalReportProcess toEvalReportProcess, String taskId) {
		
		AjaxResponse<String> response = new AjaxResponse<String>();
		 try{
			    //评估使用信息保存
				toEvalReportProcess.setStatus(EvalStatusEnum.YSYBG.getCode());
				toEvalReportProcess.setSysFinshTime(new Date());
				toEvalReportProcessService.updateEvaReport(toEvalReportProcess);
		 }catch(Exception e){
			 response.setSuccess(false);
			   response.setMessage(e.getMessage());
			   logger.error("评估申请保存失败！",e);
		 }
		return response;
	}

	@Override
	public AjaxResponse<?> saveEvalToSettle(String evals) {
		   AjaxResponse<String> response = new AjaxResponse<String>();
		   ToEvalReportProcess toEvalReportProcess = null;
          //待结算的评估编号list
		  List<String> list = JSONObject.parseArray(evals, String.class);
		  if(list==null){
			  response.setSuccess(false);
			  response.setMessage("传入参数为空");
			  return response;
		  }
		  
		  for(String evaCode:list){
			  ToEvalSettle toEvalSettle = new ToEvalSettle();
			  
			  toEvalReportProcess = toEvalReportProcessService.findToEvalReportProcessByEvalCode(evaCode);
			  toEvalReportProcess.setEvalProperty(EvalPropertyEnum.PGBD.getCode());
			  toEvalReportProcess.setEvaCode(evaCode);
			  toEvalSettle.setCaseCode(toEvalReportProcess.getCaseCode());
			  toEvalReportProcessService.updateEvaReport(toEvalReportProcess);
			  ToEvaInvoice toEvaInvoice = toEvaInvoiceMapper.selectByEvaCode(evaCode);
			  ToEvaRefund toEvaRefund = toEvaRefundMapper.selectByCaseCode(toEvalReportProcess.getCaseCode());
			  if(EvalStatusEnum.YBD.getCode().equals(toEvalReportProcess.getStatus())){
				  toEvalSettle.setFeeChangeReason(FeeChangeTypeEnum.BD.getCode());
			  }else if(toEvaRefund!=null){//TODO 待确认退报告是否意味退费
				  toEvalSettle.setFeeChangeReason(FeeChangeTypeEnum.TBG.getCode());
			  }else if(toEvaInvoice!=null){//TODO 待确认发票申请状态
				  toEvalSettle.setFeeChangeReason(FeeChangeTypeEnum.FPSD.getCode());
			  }else{
				  toEvalSettle.setFeeChangeReason(FeeChangeTypeEnum.FPSD.getCode());
			  }
			  toEvalSettleService.insertWaitAccount(toEvalSettle.getCaseCode(),evaCode,toEvalSettle.getFeeChangeReason());
		  }
		return  response;
	}
	
	/**
	 * 不同的请求源获取caseCode方式不一样
	 * @param source
	 * @param businessKey
	 * @return
	 */
	private String  getCaseCodeByCondition(String source,String evaCode,String businessKey){
		String caseCode = null;
		if("evalDetails".equals(source)){
			caseCode = toEvalReportProcessService.findToEvalReportProcessByEvalCode(evaCode).getCaseCode();
		}else{
			caseCode = toEvalReportProcessService.findToEvalReportProcessByEvalCode(businessKey).getCaseCode();
		}
		return caseCode;
	}
}
