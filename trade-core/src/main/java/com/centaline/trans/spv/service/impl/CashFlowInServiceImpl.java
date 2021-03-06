package com.centaline.trans.spv.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.enums.SpvCashFlowApplyStatusEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.common.vo.FileUploadVO;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.entity.ToSpvAduit;
import com.centaline.trans.spv.entity.ToSpvCashFlow;
import com.centaline.trans.spv.entity.ToSpvCashFlowApply;
import com.centaline.trans.spv.entity.ToSpvReceipt;
import com.centaline.trans.spv.repository.ToSpvAduitMapper;
import com.centaline.trans.spv.repository.ToSpvCashFlowApplyAttachMapper;
import com.centaline.trans.spv.repository.ToSpvCashFlowApplyMapper;
import com.centaline.trans.spv.repository.ToSpvCashFlowMapper;
import com.centaline.trans.spv.repository.ToSpvReceiptMapper;
import com.centaline.trans.spv.service.CashFlowInService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.spv.vo.SpvBaseInfoVO;
import com.centaline.trans.spv.vo.SpvChargeInfoVO;
import com.centaline.trans.spv.vo.SpvRecordedsVO;
import com.centaline.trans.spv.vo.SpvReturnCashflowVO;

@Service
public class CashFlowInServiceImpl implements CashFlowInService {
	@Autowired
	private ToSpvService toSpvService;	
	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired
	private PropertyUtilsServiceImpl propertyUtilsService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private UamSessionService uamSessionService;	
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired
	private ToSpvCashFlowApplyMapper toSpvCashFlowApplyMapper;	
	@Autowired
	private ToSpvCashFlowMapper toSpvCashFlowMapper;
	@Autowired
	private ToSpvAduitMapper toSpvAduitMapper;
	@Autowired
	private ToSpvReceiptMapper toSpvReceiptMapper;
	@Autowired
	private ToSpvCashFlowApplyAttachMapper toSpvCashFlowApplyAttachMapper;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired(required = true)
	private UamUserOrgService uamUserOrgService;


	@Override
	public void cashFlowInApplyProcess(HttpServletRequest request, String source, String instCode, String taskId,
			String handle, String businessKey) {
		String spvCode;
        SpvBaseInfoVO spvBaseInfoVO;
        SpvChargeInfoVO spvChargeInfoVO = toSpvService.findSpvChargeInfoVOByCashFlowApplyCodeByIn(businessKey);

        if(null != spvChargeInfoVO  && null != spvChargeInfoVO.getToSpvCashFlowApply() && !StringUtils.isBlank(spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode())){
        	spvCode = spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode();
        	ToSpv toSpv = toSpvService.findToSpvBySpvCode(spvCode);
        	spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(toSpv.getPkid());
        	request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
        	request.setAttribute("spvChargeInfoVO", spvChargeInfoVO);
        }        
	}

	/**
	 * 驳回入账申请再次提交
	 */
	@Override
	public void cashFlowInApplyDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvRecordedsVO spvRecordedsVO, String businessKey, Boolean chargeInAppr) throws Exception {

		Map<String, Object> variables = new HashMap<String, Object>();
		String spvApplyCode = null;
		if(null == spvRecordedsVO){throw new BusinessException("入账申请信息为空！");}
		/**申请人信息**/
		SessionUser user = uamSessionService.getSessionUser();
		if(null == user || StringUtils.isBlank(user.getId())|| StringUtils.isBlank(user.getUsername())|| StringUtils.isBlank(user.getServiceDepId())){
			throw new BusinessException("查询不到申请人详细信息！");
		}
		/**查询处理人信息**/
		User riskControlDirector = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(user.getServiceDepId(), "JYFKZJ");
		if(null == riskControlDirector || StringUtils.isBlank(riskControlDirector.getId())|| StringUtils.isBlank(riskControlDirector.getUsername())){
			throw new BusinessException("查询不到处理人！");
		}
		
		/**保存数据**/  
		toSpvService.saveSpvChargeInfoVObyIn(spvRecordedsVO,handle,spvApplyCode);
		/**1.查询申请*/
		ToSpvCashFlowApply toSpvCashFlowApply = toSpvCashFlowApplyMapper.selectByCashFlowApplyCode(spvRecordedsVO.getBusinessKey());
		if(null == toSpvCashFlowApply || null == toSpvCashFlowApply.getPkid()){throw new BusinessException("申请信息pkid为空！");}
		List<ToSpvCashFlow> toSpvCashFlowList = toSpvCashFlowMapper.selectByCashFlowApplyId(toSpvCashFlowApply.getPkid());
		if(null != toSpvCashFlowList){
			for(ToSpvCashFlow toSpvCashFlow:toSpvCashFlowList){
				toSpvCashFlow.setStatus(SpvCashFlowApplyStatusEnum.DIRECTORADUIT.getCode());
				toSpvCashFlow.setUpdateBy(user.getId());
				toSpvCashFlow.setUpdateTime(new Date());
				toSpvCashFlowMapper.updateByPrimaryKeySelective(toSpvCashFlow);
			}
		}
		
		toSpvCashFlowApply.setStatus(SpvCashFlowApplyStatusEnum.DIRECTORADUIT.getCode());
		toSpvCashFlowApply.setUpdateBy(user.getId());
		toSpvCashFlowApply.setUpdateTime(new Date());
		toSpvCashFlowApply.setApplyAuditor(riskControlDirector.getId());
		toSpvCashFlowApplyMapper.updateByPrimaryKeySelective(toSpvCashFlowApply);/**更新状态**/
		
		variables.put("RiskControlOfficer", user.getUsername());
		variables.put("RiskControlDirector", riskControlDirector.getUsername());
		taskService.submitTask(taskId, variables);
		
	}

	@Override
	public void cashFlowInDirectorAduitProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
        
        String spvCode;
        SpvBaseInfoVO spvBaseInfoVO;
        SpvChargeInfoVO spvChargeInfoVO = toSpvService.findSpvChargeInfoVOByCashFlowApplyCodeByIn(businessKey);
    	spvCode = (String) request.getAttribute("spvCode");
    	if(null == spvCode && spvChargeInfoVO != null && !StringUtils.isBlank(spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode())){
    		spvCode = spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode();
    	}
    	ToSpv toSpv = toSpvService.findToSpvBySpvCode(spvCode);
    	spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(null == toSpv?null:toSpv.getPkid());
    	if(spvChargeInfoVO != null){       	
        	request.setAttribute("spvChargeInfoVO", spvChargeInfoVO);  
    	}

    	request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);    
        
	}
	/**
	 * 入账审批处理
	 */
	@Override
	public void cashFlowInDirectorAduitDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvRecordedsVO spvRecordedsVO, String businessKey, Boolean chargeInAppr) throws Exception {
		    
		    if(null == spvRecordedsVO || StringUtils.isBlank(spvRecordedsVO.getBusinessKey())){throw new BusinessException("BusinessKey为空！");}
		
			SessionUser user = uamSessionService.getSessionUser();
			Map<String, Object> variables = new HashMap<String, Object>();
			String statusType="";
			/**1.查询申请*/
			ToSpvCashFlowApply toSpvCashFlowApply = toSpvCashFlowApplyMapper.selectByCashFlowApplyCode(spvRecordedsVO.getBusinessKey());
			
			ToSpvAduit toSpvAduit = new ToSpvAduit();
			toSpvAduit.setApplyId(toSpvCashFlowApply.getPkid().toString());
			toSpvAduit.setActProcId(taskId);/**流程实例**/
			toSpvAduit.setTaskDefKey(instCode);/**流程节点**/
			toSpvAduit.setTaskId(taskId);/**任务ID**/
			toSpvAduit.setOperator(user.getId());/**操作人**/
			
			String resultType="";/**审核结果**/
			switch (handle) {
           		case "apply": resultType = "风控专员"; break;
                case "directorAduit":  resultType = "总监审批"; break;
                case "financeAduit": resultType = "财务审核"; break;
            }
			
			if(chargeInAppr){
				toSpvAduit.setResult(resultType+"通过");
				statusType = SpvCashFlowApplyStatusEnum.FINANCEADUIT.getCode();
			}else{
				toSpvAduit.setResult(resultType+"驳回");
				statusType = SpvCashFlowApplyStatusEnum.APPLY.getCode();
				toSpvCashFlowApply.setApplyAuditor("");	
			}
			
			toSpvAduit.setContent(spvRecordedsVO.getTurndownContent());/**内容**/
			toSpvAduit.setCreateBy(user.getId());
			toSpvAduit.setCreateTime(new Date());
			toSpvAduitMapper.insertSelective(toSpvAduit);/**保存审核记录**/

			List<ToSpvCashFlow> toSpvCashFlowList = toSpvCashFlowMapper.selectByCashFlowApplyId(toSpvCashFlowApply.getPkid());
			if(null != toSpvCashFlowList)
				for(ToSpvCashFlow toSpvCashFlow:toSpvCashFlowList){
					toSpvCashFlow.setStatus(statusType);
					toSpvCashFlow.setUpdateBy(user.getId());
					toSpvCashFlow.setUpdateTime(new Date());
					toSpvCashFlowMapper.updateByPrimaryKeySelective(toSpvCashFlow);/**更新状态**/
				}
			
			toSpvCashFlowApply.setStatus(statusType);
			toSpvCashFlowApply.setUpdateBy(user.getId());
			toSpvCashFlowApply.setUpdateTime(new Date());
			toSpvCashFlowApplyMapper.updateByPrimaryKeySelective(toSpvCashFlowApply);/**更新状态**/
			
			variables.put("directorAduit",chargeInAppr);
			taskService.submitTask(taskId, variables);
			
	}

	@Override
	public void cashFlowInFinanceAduitProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
		String spvCode;
        SpvBaseInfoVO spvBaseInfoVO;
        SpvChargeInfoVO spvChargeInfoVO = toSpvService.findSpvChargeInfoVOByCashFlowApplyCodeByIn(businessKey);

        if(spvChargeInfoVO != null && spvChargeInfoVO.getToSpvCashFlowApply() != null && !StringUtils.isBlank(spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode())){
        	spvCode = spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode();
        	ToSpv toSpv = toSpvService.findToSpvBySpvCode(spvCode);
        	spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(toSpv.getPkid());
        	request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
        	request.setAttribute("spvChargeInfoVO", spvChargeInfoVO);
        }        
	}

	@Override
	public void cashFlowInFinanceAduitDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvRecordedsVO spvRecordedsVO, String businessKey, Boolean chargeInAppr) throws Exception {
		
		    Map<String, Object> variables = new HashMap<String, Object>();
		    String statusType = "";
		    String resultType="";/**审核结果**/
			if(null == spvRecordedsVO || StringUtils.isBlank(spvRecordedsVO.getBusinessKey())){throw new BusinessException("BusinessKey为空");}
			if(null == taskId){throw new BusinessException("taskId为空");}
			
			SessionUser user = uamSessionService.getSessionUser();
			if(null == user || StringUtils.isBlank(user.getId())){throw new BusinessException("user信息为空");}
			
			TaskVo task = workFlowManager.getHistoryTask(taskId);
			/**1.查询申请**/
			ToSpvCashFlowApply toSpvCashFlowApply = toSpvCashFlowApplyMapper.selectByCashFlowApplyCode(spvRecordedsVO.getBusinessKey());
			
			if(null == toSpvCashFlowApply || null == toSpvCashFlowApply.getPkid()){throw new BusinessException("申请信息查询为空！");}
			
			ToSpvAduit toSpvAduit = new ToSpvAduit();
			toSpvAduit.setApplyId(toSpvCashFlowApply.getPkid().toString());
			toSpvAduit.setActProcId(taskId);/**流程实例**/
			toSpvAduit.setTaskDefKey(instCode);/**流程节点**/
			toSpvAduit.setTaskId(taskId);/**任务ID**/
			toSpvAduit.setOperator(user.getId());/**操作人**/
			
			switch (handle) {
	       		case "apply": resultType = "风控专员"; break;
	            case "directorAduit": resultType = "总监审批"; break;
	            case "financeAduit":  resultType = "财务审核";  break;
			}
			
			if(chargeInAppr){
				toSpvAduit.setResult(resultType+"通过");
				statusType = SpvCashFlowApplyStatusEnum.AUDITCOMPLETED.getCode();
				ToWorkFlow workFlow = toWorkFlowService.queryWorkFlowByInstCode(instCode);//更新状态
				workFlow.setStatus(WorkFlowStatus.COMPLETE.getCode());
				toWorkFlowService.updateByPrimaryKeySelective(workFlow);
				if(!StringUtils.isBlank(toSpvCashFlowMapper.getUserIdByUserName(task.getAssignee()))){
					toSpvCashFlowApply.setFtPostAuditor(toSpvCashFlowMapper.getUserIdByUserName(task.getAssignee()));//设置处理人
				}
			}else{
				toSpvAduit.setResult(resultType+"驳回");
				statusType = SpvCashFlowApplyStatusEnum.APPLY.getCode();
				toSpvCashFlowApply.setApplyAuditor("");	
			}
			
			toSpvAduit.setContent(spvRecordedsVO.getTurndownContent());/**内容**/
			toSpvAduit.setCreateBy(user.getId());
			toSpvAduit.setCreateTime(new Date());
			toSpvAduitMapper.insertSelective(toSpvAduit);
			
			List<ToSpvCashFlow> toSpvCashFlowList = toSpvCashFlowMapper.selectByCashFlowApplyId(toSpvCashFlowApply.getPkid());
			if(null != toSpvCashFlowList)
				for(ToSpvCashFlow toSpvCashFlow:toSpvCashFlowList){
					toSpvCashFlow.setStatus(statusType);
					toSpvCashFlow.setUpdateBy(user.getId());
					toSpvCashFlow.setUpdateTime(new Date());
					if(chargeInAppr){ toSpvCashFlow.setCloseTime(new Date());/**完成时间**/ }
					toSpvCashFlowMapper.updateByPrimaryKeySelective(toSpvCashFlow);
				}
			
			
			toSpvCashFlowApply.setStatus(statusType);
			toSpvCashFlowApply.setUpdateBy(user.getId());
			toSpvCashFlowApply.setUpdateTime(new Date());
			toSpvCashFlowApplyMapper.updateByPrimaryKeySelective(toSpvCashFlowApply);
			
			variables.put("financeAduit",chargeInAppr);
			taskService.submitTask(taskId, variables);
	}

	
	private String createSpvApplyCode() {
		return uamBasedataService.nextSeqVal("SPV_CODE", new SimpleDateFormat("yyyyMMdd").format(new Date()));
	}
	/**
	 * 入账提交申请创建流程
	 * 
	 */
	@Override
	public void cashFlowInPageDeal(HttpServletRequest request, String handle, SpvRecordedsVO spvRecordedsVO) throws Exception {
		
		Map<String, Object> vars = new HashMap<String, Object>();
		SessionUser user = uamSessionService.getSessionUser();
		/**创建spvApplyCode**/
		String spvApplyCode = createSpvApplyCode();
		
		if( StringUtils.isBlank(user.getServiceDepId())){ throw new BusinessException("申请人部门为空！"); }
		if(null == spvRecordedsVO || null == spvRecordedsVO.getItems()) {throw new BusinessException("申请入账流水信息不存在！");}
		if(null == spvApplyCode ) throw new BusinessException("创建spvApplyCode失败！");
		if(StringUtils.isBlank(spvRecordedsVO.getSpvConCode())){throw new BusinessException("监管合约信息不存在！");}
		/**保存数据**/
		toSpvService.saveSpvChargeInfoVObyIn(spvRecordedsVO,handle,spvApplyCode); 
		
		User riskControlDirector = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(user.getServiceDepId(), "JYFKZJ");
		if(null == riskControlDirector || StringUtils.isBlank(riskControlDirector.getUsername()) 
				|| StringUtils.isBlank(user.getUsername())){
			throw new BusinessException("查询不到处理人！");
		}
		vars.put("RiskControlOfficer", user.getUsername());
		vars.put("RiskControlDirector", riskControlDirector.getUsername());
		/**开启流程**/
		StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId( 
				propertyUtilsService.getSpvCashflowInProcess(), spvApplyCode, vars);

		/**插入工作流表**/
		ToWorkFlow workFlow = new ToWorkFlow();
		workFlow.setBusinessKey("SpvCashflowInProcess");
		workFlow.setCaseCode(spvRecordedsVO.getCaseCode());
		workFlow.setInstCode(processInstance.getId());
		workFlow.setProcessDefinitionId(propertyUtilsService.getSpvCashflowInProcess());
		workFlow.setProcessOwner(user.getId());
		workFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
		workFlow.setBizCode(spvApplyCode);
		workFlow.setCaseCode(spvRecordedsVO.getCaseCode());
		toWorkFlowService.insertSelective(workFlow);
		
		/**提交申请任务**/
		PageableVo pageableVo = taskService.listTasks(processInstance.getId(), false);
		List<TaskVo> taskList = pageableVo.getData();
		for (TaskVo task : taskList) {
			if ("SpvCashflowInApply".equals(task.getTaskDefinitionKey())) {
				taskService.complete(task.getId() + "");
			}
		}
	}
	
	@Override
	public void cashFlowOutApprDealAppDelete(HttpServletRequest request, String instCode, String pkid,
			String handle, SpvRecordedsVO spvRecordedsVO, String businessKey, Boolean chargeInAppr) throws Exception {

		SessionUser user = uamSessionService.getSessionUser();
		
		ToSpvCashFlow toSpvCashFlow = toSpvCashFlowMapper.selectByPrimaryKey(Long.valueOf(pkid));
		toSpvCashFlow.setUpdateBy(user.getId());
		toSpvCashFlow.setUpdateTime(new Date());
		toSpvCashFlow.setIsDeleted("1");
		toSpvCashFlowMapper.updateByPrimaryKey(toSpvCashFlow);
		
	}
	@Override
	public void saveAttachments(FileUploadVO fileUploadVO,String cashFlowCode) {
		SessionUser user = uamSessionService.getSessionUser();
		
		ToSpvCashFlow toSpvCashFlow = toSpvCashFlowMapper.selectByPrimaryKey(Long.valueOf(cashFlowCode));
		
		if(fileUploadVO.getPkIdArr() != null && !fileUploadVO.getPkIdArr().isEmpty()) {
			delAttachment(fileUploadVO.getPkIdArr());
		}
		
		if(fileUploadVO.getPictureNo() != null && !fileUploadVO.getPictureNo().isEmpty()){
			if(null != toSpvCashFlow)
			for(int i=0; i<fileUploadVO.getPictureNo().size(); i++) {
				ToSpvReceipt attach = new ToSpvReceipt();
				attach.setAttachId(fileUploadVO.getPictureNo().get(i));
				attach.setCashflowId(toSpvCashFlow.getPkid().toString());
				attach.setType("in");
				int length = fileUploadVO.getPicName().get(i).length();
				int index = fileUploadVO.getPicName().get(i).lastIndexOf(".");
				attach.setType(fileUploadVO.getPicName().get(i).substring(index+1, length));
				attach.setComment(fileUploadVO.getPicName().get(i));
				attach.setIsDeleted("0");
				attach.setCreateBy(user.getId());
				attach.setCreateTime(new Date());
				toSpvReceiptMapper.insertSelective(attach);
			}	
		}
		
	}
	@Override
	public void delAttachment(List<Long> pkIdArr) {
		for(Long pkid:pkIdArr) {
			toSpvCashFlowApplyAttachMapper.setIsDeletedByPrimaryKey(pkid);
		}
	}
	/**
	 * 删除入账申请数据
	 */
	@Override
	public void cashFlowOutApprDeleteCashFlowAll(HttpServletRequest request, String instCode, String pkid,
			String handle) throws Exception {
		SessionUser user = uamSessionService.getSessionUser();
		if(StringUtils.equals("apply", handle) && !StringUtils.isBlank(instCode)){
			ToSpvCashFlowApply toSpvCashFlowApply = toSpvCashFlowApplyMapper.selectByPrimaryKey(Long.valueOf(pkid));
			List<ToSpvCashFlow> toSpvCashFlowList = toSpvCashFlowMapper.selectByCashFlowApplyId(toSpvCashFlowApply.getPkid());
			for(ToSpvCashFlow toSpvCashFlow:toSpvCashFlowList){
				toSpvCashFlow.setUpdateBy(user.getId());
				toSpvCashFlow.setUpdateTime(new Date());
				toSpvCashFlow.setIsDeleted("1");
				toSpvCashFlowMapper.updateByPrimaryKey(toSpvCashFlow);//删除入账流水
				List<ToSpvReceipt> toSpvReceiptList = toSpvReceiptMapper.selectByCashFlowId(toSpvCashFlow.getPkid().toString());
				for(ToSpvReceipt toSpvReceipt:toSpvReceiptList){
					toSpvReceipt.setUpdateBy(user.getId());
					toSpvReceipt.setUpdateTime(new Date());
					toSpvReceipt.setIsDeleted("1");
					toSpvReceiptMapper.updateByPrimaryKeySelective(toSpvReceipt);//删除入账流水附件
				}
			}
			toSpvCashFlowApply.setUpdateBy(user.getId());
			toSpvCashFlowApply.setUpdateTime(new Date());
			toSpvCashFlowApply.setIsDeleted("1");
			toSpvCashFlowApplyMapper.updateByPrimaryKey(toSpvCashFlowApply);//删除入账申请
			processInstanceService.deleteProcess(instCode);//删除流程
		}
	}

	@Override
	public void saveSpvRecordsVO(SpvRecordedsVO spvrevo) {
		toSpvService.saveSpvRecordedsVO(spvrevo); 
	}

}
