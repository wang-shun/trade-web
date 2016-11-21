package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.entity.ToServChangeHistroty;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.ToServChangeHistrotyService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.LoanlostApproveService;
import com.centaline.trans.task.service.ServiceChangeService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;
import com.centaline.trans.task.vo.ServiceChangeApplyVO;

/**
 * 业务变更
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/task/serviceChangeService")
public class ServiceChangeApplyController {

	@Autowired(required = true)
	private ToCaseService toCaseService;

	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private LoanlostApproveService loanlostApproveService;
	
	@Autowired
	private ToServChangeHistrotyService toServChangeHistrotyService;
	
	@Autowired
	private ServiceChangeService serviceChangeService;
	
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private UamSessionService uamSessionService;
	
	
	@RequestMapping("apply/process")
	public String toApplyProcess(HttpServletRequest request, HttpServletResponse response,
			String caseCode, String source, String taskitem, String processInstanceId) {
		SessionUser user = uamSessionService.getSessionUser();
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("approveType", "4");
		request.setAttribute("operator", user != null ? user.getId() : "");
		
 		request.setAttribute("list", serviceChangeService.queryDelServChangeHistroty(caseCode));
 		request.setAttribute("addServ", serviceChangeService.queryAddServChangeHistroty(caseCode));
		return "task/taskServiceChangeApply";
	}
	 @RequestMapping("approve/process")
	public String toApproveProcess(HttpServletRequest request, HttpServletResponse response,
		String caseCode, String source, String taskitem, String processInstanceId) {
		SessionUser user = uamSessionService.getSessionUser();
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("approveType", "4");
		request.setAttribute("operator", user != null ? user.getId() : "");
		
		request.setAttribute("newxm", serviceChangeService.qureyServChangeHistrotyInfo(caseCode).get("newServChange"));
 		request.setAttribute("delxm", serviceChangeService.qureyServChangeHistrotyInfo(caseCode).get("delServChange"));
		 return "task/taskServiceChangeApprove";
	}
	
	

	/**
	 * 业务变更审批
	 * @param request
	 * @param processInstanceVO
	 * @param loanlostApproveVO
	 * @param Approve_Result
	 * @param content
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="changeServiceApprove")
	@ResponseBody
	public AjaxResponse<T> changeServiceApprove(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String Approve_Result, String content) {
		int updateType = 0;
		ToWorkFlow toWorkFlow = new ToWorkFlow();
		toWorkFlow.setBusinessKey(WorkFlowEnum.SRV_BUSSKEY.getCode());
		toWorkFlow.setCaseCode(processInstanceVO.getCaseCode());
		ToWorkFlow nwf=toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(toWorkFlow);
		if(Approve_Result.equals("true")) {
			updateType = serviceChangeService.updateServItemAndProcessor(processInstanceVO.getCaseCode(), Approve_Result);
		} else {
			updateType = serviceChangeService.updateServChangeHistroty(processInstanceVO.getCaseCode());
		}
//		ToApproveRecord toApproveRecord = 
		saveToApproveRecord(processInstanceVO, loanlostApproveVO, Approve_Result, content);

		if(updateType > 11 || !Approve_Result.equals("true")) {
			/*流程引擎相关*/
			List<RestVariable> variables = new ArrayList<RestVariable>();
			RestVariable restVariable = new RestVariable();
			restVariable.setName("Approve_Result");
			restVariable.setValue(Approve_Result.equals("true"));
			variables.add(restVariable);
			
			ToCase toCase = toCaseService.findToCaseByCaseCode(processInstanceVO.getCaseCode());	
			workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), 
					toCase.getLeadingProcessId(), processInstanceVO.getCaseCode());
		}

		
		nwf.setStatus(WorkFlowStatus.COMPLETE.getCode());
		toWorkFlowService.updateByPrimaryKeySelective(nwf);
		
		if(updateType > 10) {
			return AjaxResponse.success("操作完成。");
		} else {
			return AjaxResponse.success("操作失败。");
		}
	}
	
	/**
	 * 业务变更申请
	 * @param request
	 * @param processInstanceVO 流程引擎相关变来那个
	 * @param loanlostApproveVO 审核记录相关参数
	 * @param serviceChangeApplyVO 
	 * @return
	 */
	@RequestMapping(value="changeServiceApply")
	@ResponseBody
    public boolean changeServiceApply(HttpServletRequest request, ProcessInstanceVO processInstanceVO, 
    		LoanlostApproveVO loanlostApproveVO, ServiceChangeApplyVO serviceChangeApplyVO) {
		
		if(processInstanceVO.getProcessInstanceId() == null && processInstanceVO.getCaseCode() != null) {
    		ToWorkFlow toWorkFlow = new ToWorkFlow();
    		toWorkFlow.setCaseCode(processInstanceVO.getCaseCode());
    		toWorkFlow.setBusinessKey(WorkFlowEnum.SRV_BUSSKEY.getCode());
    		String instCode = toWorkFlowService.queryToWorkFlowByCaseCodeBusKey(toWorkFlow).getInstCode();
    		processInstanceVO.setProcessInstanceId(instCode);
    	}
		
		try {
			/*流程引擎相关*/
			List<RestVariable> variables = new ArrayList<RestVariable>();
	
			ToCase toCase = toCaseService.findToCaseByCaseCode(processInstanceVO.getCaseCode());	
			workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), 
					toCase.getLeadingProcessId(), processInstanceVO.getCaseCode());
		} catch (Exception e) {
			return false;
		}
		
    	List<Long> pkidList =  serviceChangeApplyVO.getPkid();
		if(pkidList != null) {
	    	for(int i=0; i<pkidList.size(); i++) {
				/*更新服务项变更表*/
	    		ToServChangeHistroty toServChangeHistroty = new ToServChangeHistroty();
	    		if(pkidList.get(i) != null) {
	    			toServChangeHistroty.setPkid(pkidList.get(i));
	    			toServChangeHistroty.setReason(serviceChangeApplyVO.getReason().get(i));
	    			toServChangeHistrotyService.updateByPrimaryKeySelective(toServChangeHistroty);
	    		}
				/*保存申请记录*/
	    		saveToApproveRecord(processInstanceVO, loanlostApproveVO, serviceChangeApplyVO.getReason().get(i));
	    	}
		} else {
			/*保存申请记录*/
    		saveToApproveRecord(processInstanceVO, loanlostApproveVO, "新增提交");
		}
		
		return true;
	}
	
	/**
	 * 保存审核记录(审批)
	 * @param processInstanceVO
	 * @param loanlostApproveVO
	 * @param loanLost
	 * @param loanLost_response
	 */
	private ToApproveRecord saveToApproveRecord(ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO,
			String loanLost, String loanLost_response) {
		ToApproveRecord toApproveRecord = new ToApproveRecord();
		toApproveRecord.setProcessInstance(processInstanceVO.getProcessInstanceId());
		toApproveRecord.setPartCode(processInstanceVO.getPartCode());
		toApproveRecord.setOperatorTime(new Date());
		toApproveRecord.setApproveType(loanlostApproveVO.getApproveType());
		toApproveRecord.setCaseCode(processInstanceVO.getCaseCode());
		boolean b = loanLost.equals("true");
		boolean c = loanLost_response == null || loanLost_response.intern().length() == 0;
		toApproveRecord.setContent((b?"通过":"不通过") + (c?",没有审批意见。":",审批意见为"+loanLost_response));
		toApproveRecord.setOperator(loanlostApproveVO.getOperator());
		loanlostApproveService.saveLoanlostApprove(toApproveRecord);
		return toApproveRecord;
	}
	
	/**
	 * 保存审核记录（申请）
	 * @param processInstanceVO
	 * @param loanlostApproveVO
	 * @param loanLost_response
	 */
	private ToApproveRecord saveToApproveRecord(ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO,
			String loanLost_response) {
		ToApproveRecord toApproveRecord = new ToApproveRecord();
		toApproveRecord.setProcessInstance(processInstanceVO.getProcessInstanceId());
		toApproveRecord.setPartCode(processInstanceVO.getPartCode());
		toApproveRecord.setOperatorTime(new Date());
		toApproveRecord.setApproveType(loanlostApproveVO.getApproveType());
		toApproveRecord.setCaseCode(processInstanceVO.getCaseCode());
		toApproveRecord.setContent((loanLost_response));
		toApproveRecord.setOperator(loanlostApproveVO.getOperator());
		loanlostApproveService.saveLoanlostApprove(toApproveRecord);
		return toApproveRecord;
	}
	
}
