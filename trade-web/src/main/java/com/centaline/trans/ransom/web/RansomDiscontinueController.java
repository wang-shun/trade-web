package com.centaline.trans.ransom.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.common.i18n.Exception;
import org.apache.cxf.common.i18n.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.web.QuickQueryController;
import com.aist.common.quickQuery.web.vo.DatagridVO;
import com.aist.common.rapidQuery.paramter.ParamterHander;
import com.aist.common.rapidQuery.service.RapidQueryService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomDetailVo;
import com.centaline.trans.ransom.service.RansomListFormService;
import com.centaline.trans.ransom.service.RansomService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.LoanlostApproveService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

/**
 * <font color=red>流程引擎 任务处理</font>
 * 
 * @author wbwumf
 *
 *         2017年10月9日
 */
@Controller
@RequestMapping(value = "task/ransomDiscontinue")
public class RansomDiscontinueController {
	
	@Autowired(required = true)
	private ToCaseService toCaseService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private LoanlostApproveService loanlostApproveService;

	@Autowired(required = true)
	private RansomService ransomService;
	
	@Autowired(required = true)
	private QuickQueryController quickQueryController;

	@Autowired
	private RansomListFormService ransomListFormService;

	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired
	private PropertyUtilsService propertyUtilsService;
	
	@Autowired
	private ProcessInstanceService processInstanceService;
	
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	
	
	/**
	 * 这里最终选择与stopApplyProcessFromRansomList方法区分开来，后者是赎楼列表中的中止申请按钮处理器
	 * @param caseCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "stopApplyProcess")
	public String stopApplyProcess(String caseCode, ServletRequest request) {
		Map<String, Object> task = null;
		String taskId = null;
		//获取赎楼中止流程信息
		task = getSingleRansomTaskInfo((HttpServletRequest)request, true, false);
		if(task != null) {
			taskId = (String) task.get("ID");
			request.setAttribute("taskId", taskId);
			request.setAttribute("processInstanceId", task.get("INST_CODE"));
		}else {
			return null;
		}
		//赎楼案件信息
		ToRansomCaseVo ransomCase = ransomListFormService.getRansomCase(caseCode);
		if(ransomCase != null) {
			request.setAttribute("ransomCase", ransomCase);
		}
		//公共信息
		getTaskBaseInfo(request, caseCode);
		return "ransom/ransomDiscontinue";
	}
	
	/**
	 * 赎楼中止流程申请
	 * by wbshume
	 * @param caseCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "stopApplyProcessFromRansomList")
	public String stopApplyProcessFromRansomList(String caseCode, ServletRequest request) {
		Map<String, Object> task = null;
		String taskId = null;
		//查询当前caseCode赎楼流程是否有对应的中止流程,若有则不进入赎楼中止申请
		task = getSingleRansomTaskInfo((HttpServletRequest)request, true, false);
		if(task != null) {
			return null;
		}
		//查询是否有对应的赎楼流程,若有则挂起对应的【赎楼流程】
		task = null; taskId= null;
		task = getSingleRansomTaskInfo((HttpServletRequest)request, false, false);
		if(task != null) {//这里节省了一个变量名,用 taskId 表示 PROC_INST_ID_
			taskId = (String) task.get("INST_CODE");
			workFlowManager.activateOrSuspendProcessInstance(taskId, false);
		}else {
			return null;
		}
		//开启赎楼中止流程
		SessionUser user = uamSessionService.getSessionUser();
		Map<String,Object> defValsMap = new HashMap<String,Object>();
		defValsMap.put("sessionUser", user.getUsername());
		defValsMap.put("warrant", user.getUsername());
		String processDfId = propertyUtilsService.getProcessDfId("ransom_suspend");
		StartProcessInstanceVo pVo = processInstanceService.startWorkFlowByDfId(processDfId, caseCode, defValsMap);
		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey("ransom_suspend");
		wf.setCaseCode(caseCode);
		wf.setBizCode(caseCode);
		wf.setProcessOwner(user.getId());
		wf.setProcessDefinitionId(processDfId);
		wf.setInstCode(pVo.getId());
		wf.setStatus(WorkFlowStatus.ACTIVE.getCode());
		toWorkFlowService.insertSelective(wf);
		//获取刚开启的【赎楼中止流程】taskId
		taskId = null;task = null;
		task = getSingleRansomTaskInfo((HttpServletRequest)request, true, false);
		if(task != null) {
			taskId = (String) task.get("ID");
			request.setAttribute("taskId", taskId);
			request.setAttribute("processInstanceId", task.get("INST_CODE"));
		}
		//赎楼案件信息
		ToRansomCaseVo ransomCase = ransomListFormService.getRansomCase(caseCode);
		if(ransomCase != null) {
			request.setAttribute("ransomCase", ransomCase);
		}
		//公共信息
		getTaskBaseInfo(request, caseCode);
		return "ransom/ransomDiscontinue";
	}

	/**
	 * 赎楼中止提交
	 * @param discontinueVo
	 * @param request
	 * @return
	 * update by wbshume
	 */
	@RequestMapping(value = "submitDiscontinue")
	@ResponseBody
	public boolean submitDiscontinue(ToRansomCaseVo ransomCase, ServletRequest request, ProcessInstanceVO processInstanceVO) {
		
		ToCase toCase = toCaseService.findToCaseByCaseCode(ransomCase.getCaseCode());
		
		//点击中止时即保存中止原因信息
		ToRansomCaseVo ransomCaseVo = ransomListFormService.getRansomCase(ransomCase.getCaseCode());
		if(StringUtils.isBlank(ransomCaseVo.getCaseCode())) {
			ransomListFormService.addRansomDetail(ransomCase);
		}else {
			ransomListFormService.updateRansomDiscountinue(ransomCase);
		}
		List<RestVariable> variables = new ArrayList<RestVariable>();
		return workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), toCase.getLeadingProcessId(), ransomCaseVo.getCaseCode());
	}
	
	
	/**
	 * 赎楼页面公共信息
	 * @param request
	 * @param caseCode
	 */
	void getTaskBaseInfo(ServletRequest request, String caseCode) {
		SessionUser user =uamSessionService.getSessionUser(); 
		// 公共基本信息
		ToRansomDetailVo detailVo = ransomService.getRansomDetail(caseCode);
		if(detailVo != null) {
			request.setAttribute("detailVo", detailVo);
			request.setAttribute("caseCode", detailVo.getCaseCode());
		}
		request.setAttribute("approveType", "3");
		request.setAttribute("operator", user.getId());
	}
	
	
	/**
	 * 赎楼中止审批入口
	 * by wbshume
	 * @param caseCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "aprroProcess")
	public String aprroProcess(String caseCode, ServletRequest request) {
		// 公共基本信息
		getTaskBaseInfo(request, caseCode);
		
		//赎楼案件信息
		ToRansomCaseVo ransomCase = ransomListFormService.getRansomCase(caseCode);
		request.setAttribute("ransomCase", ransomCase);
		
		//已有审批信息
		
		return "ransom/ransomExamine";
	}
	
	/**
	 * 赎楼中止审批提交
	 * by wbshume
	 * @param caseCode
	 * @param request
	 * @return
	 * 三个不同审批结果：
	 * 	驳回：		流程退回 【赎楼中止申请】 到责任人，重新填写 【赎楼中止申请】
	 * 	审批通过：		结束当前 【赎楼中止】 流程和 对应的 【赎楼流程】
	 * 	审批不通过：	结束当前 【赎楼中止】 流程，将之前挂起的 【赎楼流程】重启
	 */
	@RequestMapping(value = "aprroSubmit")
	@ResponseBody
	public Boolean aprroSubmit(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String examContent, String remark) {
		
		saveToApproveRecord(processInstanceVO, loanlostApproveVO, examContent, remark);
		/*流程引擎相关*/
		RestVariable restVariable = new RestVariable();
		List<RestVariable> variables = new ArrayList<RestVariable>();
		restVariable.setName("ransomAprro");
		restVariable.setValue(!examContent.equals("reject"));
		variables.add(restVariable);
		if(!StringUtils.isBlank(remark)) {
			RestVariable restVariable1 = new RestVariable();
			restVariable1.setName("ransomAprroRemark");
			restVariable1.setValue(remark);
			variables.add(restVariable1);
		}
		SessionUser user = uamSessionService.getSessionUser();
		
		//通过：删除 [赎楼流程]
		if("pass".equals(examContent)) {
			Map<String, Object> task = getSingleRansomTaskInfo(request, false, true);
			if(task != null) {
				workFlowManager.deleteProcess((String) task.get("INST_CODE"));
				ransomService.deleteRansomApplyByRansomCode((String) task.get("RANSOM_CODE"));
			}
		}
		/*else if("reject".equals(examContent)) {
			
		}*/
		//不通过：[赎楼中止流程] 结束，重启 [赎楼流程]
		else if("noPass".equals(examContent)) {
			Map<String, Object> task = getSingleRansomTaskInfo(request, false, true);
			if(task != null) {
				workFlowManager.activateOrSuspendProcessInstance((String) task.get("INST_CODE"), true);
			}
		}
		
		//赎楼中止 流程下一步
		return workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), 
				user.getId(), processInstanceVO.getCaseCode());
	}
	
	/**
	 * 保存审批记录
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
		if(loanLost.equals("pass")) {
			toApproveRecord.setContent("通过,审批意见为"+loanLost_response);
		}else if(loanLost.equals("noPass")){
			toApproveRecord.setContent("不通过,审批意见为"+loanLost_response);
		}else {
			toApproveRecord.setContent("驳回,审批意见为"+loanLost_response);
		}
		toApproveRecord.setOperator(loanlostApproveVO.getOperator());
		loanlostApproveService.saveLoanlostApprove(toApproveRecord);
		return toApproveRecord;
	}
	
	private Map<String ,Object> getSingleRansomTaskInfo(HttpServletRequest request, boolean isSuspend, boolean isSuspended) {
		Map<String, String[]> paramMap = ParamterHander.getParameters(request);
        Map<String, Object> paramObj = new HashMap<String, Object>();
        ParamterHander.mergeParamter(paramMap, paramObj);
        if(isSuspend) {//查询赎楼中止流程数据
        	String processDfId = propertyUtilsService.getProcessDfId("ransom_suspend");
        	paramObj.put("PROCESS_DEFINITION_ID_RANSOM_SUSPEND", processDfId);
        }else {//查询赎楼流程数据
        	String processDfId = propertyUtilsService.getProcessDfId("ransom_process");
        	paramObj.put("PROCESS_DEFINITION_ID_RANSOM", processDfId);
        }
        if(isSuspended) {
        	paramObj.put("isSuspended", true);
        }else {
        	paramObj.put("isSuspended", false);
        }
        SessionUser user = uamSessionService.getSessionUser();
        paramObj.put("sessionUserId", user.getId());
        JQGridParam gp = new JQGridParam();
        gp.setPage(1);
        gp.setRows(1);
        gp.putAll(paramObj);
        gp.setQueryId("queryRansomTaskListItemList");
        DatagridVO taskPage = quickQueryController.findPage(gp, request);
		if(taskPage != null && taskPage.getRows() != null && taskPage.getRows().size() > 0) {
			return (Map<String ,Object>)taskPage.getRows().get(0);
		}
		return null;
	}
	
}
