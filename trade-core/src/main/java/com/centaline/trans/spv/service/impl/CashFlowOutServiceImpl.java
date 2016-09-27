package com.centaline.trans.spv.service.impl;

import java.beans.SimpleBeanInfo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.janino.IClass.IField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.AppTypeEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.entity.ToSpvAduit;
import com.centaline.trans.spv.entity.ToSpvDeDetail;
import com.centaline.trans.spv.service.CashFlowOutService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.spv.vo.SpvBaseInfoVO;
import com.centaline.trans.spv.vo.SpvChargeInfoVO;

@Service
public class CashFlowOutServiceImpl implements CashFlowOutService {
	@Autowired
	private ToSpvService toSpvService;	
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired
	private PropertyUtilsServiceImpl propertyUtilsService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	
	@Autowired
	private UamPermissionService uamPermissionService;	
	@Autowired
	private UamSessionService uamSessionService;	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private UamBasedataService uamBasedataService;

	@Override
	public void cashFlowOutPage(HttpServletRequest request, String source, String instCode, String taskId,
			String handle, String businessKey) {
        String spvCode;
        SpvBaseInfoVO spvBaseInfoVO;
        SpvChargeInfoVO spvChargeInfoVO = toSpvService.findSpvChargeInfoVOByCashFlowApplyCode(businessKey);

        if(spvChargeInfoVO != null && spvChargeInfoVO.getToSpvCashFlowApply() != null && !StringUtils.isBlank(spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode())){
        	spvCode = spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode();
        	ToSpv toSpv = toSpvService.findToSpvBySpvCode(spvCode);
        	spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(toSpv.getPkid());
        	request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
        	request.setAttribute("spvChargeInfoVO", spvChargeInfoVO);
        }        
	}

	@Override
	public void cashFlowOutPageDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey) throws Exception {
		//判断流程是否已存在
		ToWorkFlow  toWorkFlow = toWorkFlowService.queryWorkFlowByInstCode(instCode);
		if(toWorkFlow != null && !WorkFlowStatus.ACTIVE.getCode().equals(toWorkFlow.getStatus())){
			throw new BusinessException("流程已经开启!");
		}
		
		if(spvChargeInfoVO == null || spvChargeInfoVO.getToSpvCashFlowApply() == null) throw new BusinessException("申请信息不存在！");
		
		String cashflowApplyCode = spvChargeInfoVO.getToSpvCashFlowApply().getCashflowApplyCode();
		//创建spvApplyCode
		String spvApplyCode = createSpvApplyCode();
		if(StringUtils.isBlank(cashflowApplyCode)){
			cashflowApplyCode = spvApplyCode;
		}
		//保存数据
		toSpvService.saveSpvChargeInfoVO(spvChargeInfoVO); 
		
		Map<String, Object> vars = new HashMap<String, Object>();
		//开启流程
		StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId(
				propertyUtilsService.getSpvProcessDfKey(), cashflowApplyCode, vars);

		// 提交申请任务
		PageableVo pageableVo = taskService.listTasks(processInstance.getId(), false);
		List<TaskVo> taskList = pageableVo.getData();
		for (TaskVo task : taskList) {
			if ("CashflowOutApply".equals(task.getTaskDefinitionKey())) {
				taskService.complete(task.getId() + "");
			}
		}
	}

	@Override
	public void cashFlowOutApplyProcess(HttpServletRequest request, String source, String instCode, String taskId,
			String handle, String businessKey) {
		String spvCode;
        SpvBaseInfoVO spvBaseInfoVO;
        SpvChargeInfoVO spvChargeInfoVO = toSpvService.findSpvChargeInfoVOByCashFlowApplyCode(businessKey);

        if(spvChargeInfoVO != null && spvChargeInfoVO.getToSpvCashFlowApply() != null && !StringUtils.isBlank(spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode())){
        	spvCode = spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode();
        	ToSpv toSpv = toSpvService.findToSpvBySpvCode(spvCode);
        	spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(toSpv.getPkid());
        	request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
        	request.setAttribute("spvChargeInfoVO", spvChargeInfoVO);
        }        
	}

	@Override
	public void cashFlowOutApplyDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey) throws Exception {
		
	    toSpvService.saveSpvChargeInfoVO(spvChargeInfoVO);    
		
		Map<String, Object> variables = new HashMap<String, Object>();
		taskService.submitTask(taskId, variables);
	}

	@Override
	public void cashFlowOutDirectorAduitProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
		String spvCode;
        SpvBaseInfoVO spvBaseInfoVO;
        SpvChargeInfoVO spvChargeInfoVO = toSpvService.findSpvChargeInfoVOByCashFlowApplyCode(businessKey);

        if(spvChargeInfoVO != null && spvChargeInfoVO.getToSpvCashFlowApply() != null && !StringUtils.isBlank(spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode())){
        	spvCode = spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode();
        	ToSpv toSpv = toSpvService.findToSpvBySpvCode(spvCode);
        	spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(toSpv.getPkid());
        	request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
        	request.setAttribute("spvChargeInfoVO", spvChargeInfoVO);
        }        
	}

	@Override
	public void cashFlowOutDirectorAduitDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey, Boolean chargeOutAppr)
			throws Exception {

		    toSpvService.saveSpvChargeInfoVO(spvChargeInfoVO);    
		
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("directorAduit",chargeOutAppr);
			taskService.submitTask(taskId, variables);
	}

	@Override
	public void cashFlowOutFinanceAduitProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
		String spvCode;
        SpvBaseInfoVO spvBaseInfoVO;
        SpvChargeInfoVO spvChargeInfoVO = toSpvService.findSpvChargeInfoVOByCashFlowApplyCode(businessKey);

        if(spvChargeInfoVO != null && spvChargeInfoVO.getToSpvCashFlowApply() != null && !StringUtils.isBlank(spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode())){
        	spvCode = spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode();
        	ToSpv toSpv = toSpvService.findToSpvBySpvCode(spvCode);
        	spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(toSpv.getPkid());
        	request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
        	request.setAttribute("spvChargeInfoVO", spvChargeInfoVO);
        }        
	}

	@Override
	public void cashFlowOutFinanceAduitDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey, Boolean chargeOutAppr)
			throws Exception {
            
		    toSpvService.saveSpvChargeInfoVO(spvChargeInfoVO);
		
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("financeAduit",chargeOutAppr);
			taskService.submitTask(taskId, variables);
	}

	@Override
	public void cashFlowOutFinanceSecondAduitProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
		String spvCode;
        SpvBaseInfoVO spvBaseInfoVO;
        SpvChargeInfoVO spvChargeInfoVO = toSpvService.findSpvChargeInfoVOByCashFlowApplyCode(businessKey);

        if(spvChargeInfoVO != null && spvChargeInfoVO.getToSpvCashFlowApply() != null && !StringUtils.isBlank(spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode())){
        	spvCode = spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode();
        	ToSpv toSpv = toSpvService.findToSpvBySpvCode(spvCode);
        	spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(toSpv.getPkid());
        	request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
        	request.setAttribute("spvChargeInfoVO", spvChargeInfoVO);
        }        
	}

	@Override
	public void cashFlowOutFinanceSecondAduitDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey, Boolean chargeOutAppr)
			throws Exception {
            
		    toSpvService.saveSpvChargeInfoVO(spvChargeInfoVO);
		
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("financeSecondAduit",chargeOutAppr);
			taskService.submitTask(taskId, variables);
	}

	@Override
	public void cashFlowOutDealProcess(HttpServletRequest request, String source, String instCode, String taskId,
			String handle, String businessKey) {
		String spvCode;
        SpvBaseInfoVO spvBaseInfoVO;
        SpvChargeInfoVO spvChargeInfoVO = toSpvService.findSpvChargeInfoVOByCashFlowApplyCode(businessKey);

        if(spvChargeInfoVO != null && spvChargeInfoVO.getToSpvCashFlowApply() != null && !StringUtils.isBlank(spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode())){
        	spvCode = spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode();
        	ToSpv toSpv = toSpvService.findToSpvBySpvCode(spvCode);
        	spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(toSpv.getPkid());
        	
        	request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
        	request.setAttribute("spvChargeInfoVO", spvChargeInfoVO);
        }        
	}

	@Override
	public void cashFlowOutDeal(HttpServletRequest request, String instCode, String taskId, String handle,
			SpvChargeInfoVO spvChargeInfoVO, String businessKey, Boolean chargeOutAppr) throws Exception {
		
		    toSpvService.saveSpvChargeInfoVO(spvChargeInfoVO);
		
			Map<String, Object> variables = new HashMap<String, Object>();
			taskService.submitTask(taskId, variables);
	}	
	
	private String createSpvApplyCode() {
		return uamBasedataService.nextSeqVal("SPV_APPLY_CODE", new SimpleDateFormat("yyyyMM").format(new Date()));
	}

}
