package com.centaline.trans.mortgage.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.message.core.remote.UamMessageService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.mgr.service.ToSupDocuService;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.repository.ToMortgageMapper;
import com.centaline.trans.mortgage.service.LoanerProcessService;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.service.ToApproveRecordService;
import com.centaline.trans.task.service.UnlocatedTaskService;

@Service
@Transactional
public class LoanerProcessServiceImpl implements LoanerProcessService {

	@Autowired
	private ToMortgageMapper toMortgageMapper;

	@Autowired
	private ToSupDocuService toSupDocuService;
	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	MessageService messageService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private UnlocatedTaskService unlocatedTaskService;
	@Autowired
	private ToApproveRecordService toApproveRecordService;

	@Autowired(required = true)
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private PropertyUtilsServiceImpl propertyUtilsService;
	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired
	private UamTemplateService uamTemplateService;
	@Qualifier("uamMessageServiceClient")
	@Autowired
	private UamMessageService uamMessageService;

	@Autowired
	private ToMortgageService toMortgageService;

	@Autowired
	private TaskService taskService;

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-03-24
	 * 
	 * @des:启动信贷员流程
	 */
	@Override
	public AjaxResponse<String> startLoanerOrderWorkFlow(String caseCode,String loanerUserId,String loanerOrgId,String bankOrgCode,int bankLevel,String isMainLoanBank) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		/* 流程引擎相关 */
		List<RestVariable> variables = new ArrayList<RestVariable>();
		SessionUser user = uamSessionService.getSessionUser();

		try {
			ToWorkFlow toWorkFlow = new ToWorkFlow();
			toWorkFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getName());
			toWorkFlow.setCaseCode(caseCode);
			// 查询流程表记录
			ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(toWorkFlow);
			if (null != record) {
				throw new BusinessException("启动失败,该流程正在运行！");
			}
			User loaner = uamUserOrgService.getUserById(loanerUserId);

			variables.add(new RestVariable("loanerUserId", loaner.getUsername()));
			variables.add(new RestVariable("bankLevel", bankLevel));
			variables.add(new RestVariable("sessionUserId", user.getUsername())); // 派单人
			// 设置流程变量的2中方式
			RestVariable restBankLevel = new RestVariable();
			restBankLevel.setName("bankLevelApprove");
			restBankLevel.setValue(false);
			variables.add(restBankLevel); // 预设值消息信息
			variables.add(new RestVariable("mainBankChoose", false)); // 默认启动流程时，不走作废程序

			// 启动流程
			ProcessInstance process = new ProcessInstance(propertyUtilsService.getProcessLoanerDfKey(), caseCode,variables);
			StartProcessInstanceVo vo = workFlowManager.startCaseWorkFlow(process, loaner.getUsername(), caseCode);

			// 启动流程之后 把交易顾问派单流程直接推送完
			@SuppressWarnings("rawtypes")
			PageableVo pageableVo = taskService.listTasks(vo.getId(), false);// Loaner_Process:4:1030016
			List<TaskVo> taskList = pageableVo.getData();
			for (TaskVo task : taskList) {
				if ("LoanerSendOrder".equals(task.getTaskDefinitionKey())) {// ZY-AJ-201605-1460
					taskService.complete(task.getId() + "");
					break;
				}
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("caseCode", caseCode);
			map.put("loanerId", loanerUserId);
			// ToMortgage toMortgageInfo =
			// toMortgageMapper.findToMortgageByCaseCodeAndLoanerId(map);
			ToMortgage toMortgageInfo = toMortgageMapper.findToMortgageByCaseCodeAndDisTime(caseCode);
			String bizCode = "";

			// 添加贷款表中的 信贷员、派单员等信息
			ToMortgage toMortgage = new ToMortgage();
			toMortgage.setCaseCode(caseCode);
			toMortgage.setDispachUserId(user.getId());
			toMortgage.setDispachTime(new Date());
			toMortgage.setLoanerId(loanerUserId);
			toMortgage.setLoanerProcessInstCode(vo.getId());
			toMortgage.setIsActive("1");
			toMortgage.setIsDelegateYucui("1");
			toMortgage.setIsMainLoanBank(isMainLoanBank);// 0 备选银行,1 主选银行
			toMortgage.setFinOrgCode(bankOrgCode);// 此处设置银行Code才能保证 先启的银行审批流程
			toMortgage.setBankLevel(String.valueOf(bankLevel));
			if (null != toMortgageInfo) {
				toMortgage.setPkid(toMortgageInfo.getPkid());
				toMortgageMapper.update(toMortgage);
				bizCode = toMortgageInfo.getPkid() == null ? ""
						: toMortgageInfo.getPkid().toString();
			} else {
				toMortgageMapper.insertSelective(toMortgage);
			}
			// 插入工作流表
			ToWorkFlow workFlow = new ToWorkFlow();
			workFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getName());
			workFlow.setCaseCode(caseCode);
			workFlow.setBizCode(bizCode);
			workFlow.setInstCode(vo.getId());
			workFlow.setProcessDefinitionId(propertyUtilsService.getProcessLoanerDfKey());
			workFlow.setProcessOwner(user.getId());
			workFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
			toWorkFlowService.insertSelective(workFlow);

			// 派单给BC级别银行的信贷员之后,启动银行分级审批
			//bankLevel == 1 B级银行不审批  变更
			if (bankLevel == 9) {
				toMortgageService.startTmpBankWorkFlow(caseCode, vo.getId());
			}

			response.setSuccess(true);
			response.setMessage("恭喜，交易顾问派单成功！");
		} catch (BusinessException e) {
			response.setSuccess(false);
			throw new BusinessException("Sorry,交易顾问派单失败！");
		}
		return response;
	}
	
	
	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-04-12
	 * 
	 * @des:启动信贷员流程
	 */
	@Override
	public AjaxResponse<String> newStartLoanerOrderWorkFlow(ToMortgage toMortgage) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		/* 流程引擎相关 */
		List<RestVariable> variables = new ArrayList<RestVariable>();
		SessionUser user = uamSessionService.getSessionUser();
		
		String caseCode = toMortgage.getCaseCode() ==null?"":toMortgage.getCaseCode();
		String loanerUserId = toMortgage.getLoanerId() == null ?"":toMortgage.getLoanerId();
		String  bankLevel = toMortgage.getBankLevel()== null ?"":toMortgage.getBankLevel();
		try {
			ToWorkFlow toWorkFlow = new ToWorkFlow();
			toWorkFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getName());
			toWorkFlow.setCaseCode(caseCode);
			// 查询流程表记录
			ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(toWorkFlow);
			if (null != record) {
				throw new BusinessException("启动失败,该流程正在运行！");
			}
			User loaner = uamUserOrgService.getUserById(loanerUserId);

			variables.add(new RestVariable("loanerUserId", loaner.getUsername()));
			variables.add(new RestVariable("bankLevel", toMortgage.getBankLevel() == null?"":toMortgage.getBankLevel()));
			variables.add(new RestVariable("sessionUserId", user.getUsername())); // 派单人
			// 设置流程变量的2中方式
			RestVariable restBankLevel = new RestVariable();
			restBankLevel.setName("bankLevelApprove");
			restBankLevel.setValue(false);
			variables.add(restBankLevel); // 预设值消息信息
			variables.add(new RestVariable("mainBankChoose", false)); // 默认启动流程时，不走作废程序

			// 启动流程
			ProcessInstance process = new ProcessInstance(propertyUtilsService.getProcessLoanerDfKey(), caseCode,variables);
			StartProcessInstanceVo vo = workFlowManager.startCaseWorkFlow(process, loaner.getUsername(), caseCode);

			// 启动流程之后 把交易顾问派单流程直接推送完
			@SuppressWarnings("rawtypes")
			PageableVo pageableVo = taskService.listTasks(vo.getId(), false);// Loaner_Process:4:1030016
			List<TaskVo> taskList = pageableVo.getData();
			for (TaskVo task : taskList) {
				if ("LoanerSendOrder".equals(task.getTaskDefinitionKey())) {// ZY-AJ-201605-1460
					taskService.complete(task.getId() + "");
					break;
				}
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("caseCode", caseCode);
			map.put("loanerId", loanerUserId);
			// ToMortgage toMortgageInfo =
			// toMortgageMapper.findToMortgageByCaseCodeAndLoanerId(map);
			ToMortgage toMortgageInfo = toMortgageMapper.findToMortgageByCaseCodeAndDisTime(caseCode);
			String bizCode = "";

			// 添加贷款表中的 信贷员、派单员等信息			
			toMortgage.setCaseCode(caseCode);
			toMortgage.setDispachUserId(user.getId());
			toMortgage.setDispachTime(new Date());
			toMortgage.setLoanerId(loanerUserId);
			toMortgage.setLoanerProcessInstCode(vo.getId());
			toMortgage.setIsActive("1");
			toMortgage.setIsDelegateYucui("1");		
			toMortgage.setBankLevel(bankLevel);
			
			
			if(toMortgage.getMortTotalAmount() != null){
				toMortgage.setMortTotalAmount(toMortgage.getMortTotalAmount().multiply(new BigDecimal(10000)));
			}
			if(toMortgage.getComAmount() != null){
				toMortgage.setComAmount(toMortgage.getComAmount().multiply(new BigDecimal(10000)));
			}
			if(toMortgage.getPrfAmount() != null){
				toMortgage.setPrfAmount(toMortgage.getPrfAmount().multiply(new BigDecimal(10000)));
			}
			if(toMortgage.getIfReportBeforeLend() == null){
				toMortgage.setIfReportBeforeLend("0");
			}
			if(toMortgage.getIsLoanerArrive() == null){
				toMortgage.setIsLoanerArrive("0");
			}
			if(toMortgage.getIsTmpBank() == null){
				toMortgage.setIsTmpBank("0");
			}
			
			
			if (null != toMortgageInfo) {
				toMortgage.setPkid(toMortgageInfo.getPkid());
				toMortgageMapper.update(toMortgage);
				bizCode = toMortgageInfo.getPkid() == null ? ""
						: toMortgageInfo.getPkid().toString();
			} else {
				toMortgageMapper.insertSelective(toMortgage);
			}
			// 插入工作流表
			ToWorkFlow workFlow = new ToWorkFlow();
			workFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getName());
			workFlow.setCaseCode(caseCode);
			workFlow.setBizCode(bizCode);
			workFlow.setInstCode(vo.getId());
			workFlow.setProcessDefinitionId(propertyUtilsService.getProcessLoanerDfKey());
			workFlow.setProcessOwner(user.getId());
			workFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
			toWorkFlowService.insertSelective(workFlow);

			// 派单给BC级别银行的信贷员之后,启动银行分级审批
			//bankLevel == 1 B级银行不审批  变更
			if ("9".equals(bankLevel)) {
				toMortgageService.startTmpBankWorkFlow(caseCode, vo.getId());
			}

			response.setSuccess(true);
			response.setMessage("恭喜，交易顾问派单成功！");
		} catch (BusinessException e) {
			response.setSuccess(false);
			throw new BusinessException("Sorry,交易顾问派单失败！");
		}
		return response;
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-03-28
	 * 
	 * @des:信贷员是否接单确认
	 */
	@Override
	public boolean isLoanerAcceptCase(boolean isLonaerAcceptCase,
			String taskId, String processInstanceId, String caseCode) {

		boolean loanerAccetpFlag = false;
		if ((null == caseCode || "".equals(caseCode))
				|| (null == taskId || "".equals(taskId))
				|| (null == processInstanceId || "".equals(processInstanceId))) {
			throw new BusinessException("信贷员接单确认请求参数异常！");
		}

		SessionUser user = uamSessionService.getSessionUser();
		List<RestVariable> variables = new ArrayList<RestVariable>();

		try {
			// 信贷员接单
			if (isLonaerAcceptCase == true) {
				variables.add(new RestVariable("loanerAccept", true));

				ToMortgage toMortgage = toMortgageMapper
						.findToMortgageByCaseCodeAndDisTime(caseCode);
				if (null != toMortgage) {
					ToMortgage toMortgageForUpdate = new ToMortgage();
					toMortgageForUpdate.setPkid(toMortgage.getPkid());
					toMortgageForUpdate.setLoanerAcceptTime(new Date());
					if (user != null)
						toMortgageForUpdate.setLoanerId(user.getId());
					toMortgageMapper.update(toMortgageForUpdate);
				}
			} else {
				variables.add(new RestVariable("loanerAccept", false));
			}
			// 提交流程
			workFlowManager.submitTask(variables, taskId, processInstanceId,
					null, caseCode);
			loanerAccetpFlag = true;

		} catch (BusinessException e) {
			throw new BusinessException("信贷员接单流程推进失败！");
		}
		return loanerAccetpFlag;
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-03-28
	 * 
	 * @des:信贷员是否接单 银行审批
	 */
	@Override
	public boolean isBankAcceptCase(boolean isBankAcceptCase, String taskId,String processInstanceId, String caseCode) {

		boolean bankAccetpFlag = false;

		if ((null == caseCode || "".equals(caseCode))
				|| (null == taskId || "".equals(taskId))
				|| (null == processInstanceId || "".equals(processInstanceId))) {
			throw new BusinessException("信贷员接单银行确认请求参数异常！");
		}

		SessionUser user = uamSessionService.getSessionUser();
		List<RestVariable> variables = new ArrayList<RestVariable>();
		try {
			// 信贷员接单
			if (isBankAcceptCase == true) {
				variables.add(new RestVariable("bankBusinessApprove", true));
				// 审批通过 更改流程状态
				// 更新流程状态为'4'：已完成
				ToWorkFlow workFlow = new ToWorkFlow();
				workFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getName());
				workFlow.setCaseCode(caseCode);
				ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(workFlow);
				if (record != null) {
					record.setStatus(WorkFlowStatus.COMPLETE.getCode());
					toWorkFlowService.updateByPrimaryKeySelective(record);
				}

				ToMortgage toMortgage = toMortgageMapper.findToMortgageByCaseCodeAndDisTime(caseCode);
				if (null != toMortgage) {
					ToMortgage toMortgageForUpdate = new ToMortgage();
					toMortgageForUpdate.setPkid(toMortgage.getPkid());
					toMortgageForUpdate.setBankApproveTime(new Date());
					if (user != null)
						toMortgageForUpdate.setBankApproveUserId(user.getId());
					// 临时银行 1 审批通过，现在银行信贷员审核通过为 3
					toMortgageForUpdate.setTmpBankStatus("3");
					toMortgageMapper.update(toMortgageForUpdate);
				}

			} else {
				variables.add(new RestVariable("bankBusinessApprove", false));
			}
			// 提交流程
			workFlowManager.submitTask(variables, taskId, processInstanceId,null, caseCode);
			bankAccetpFlag = true;
		} catch (BusinessException e) {

			throw new BusinessException("信贷员接单流程推进失败！");
		}

		return bankAccetpFlag;
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-03-28
	 * 
	 * @des:交易顾问派单 流程关闭
	 */
	@Override
	public void loanerProcessDelete(String caseCode, String taskId,	String processInstanceId) {
		List<RestVariable> variables = new ArrayList<RestVariable>();
		if ((null == caseCode || "".equals(caseCode))
				|| (null == taskId || "".equals(taskId))
				|| (null == processInstanceId || "".equals(processInstanceId))) {
			throw new BusinessException("结束交易顾问派单流程请求参数异常！");
		}
		try {
			// 结束流程
			variables.add(new RestVariable("mainBankChoose", true));
	    	//提交流程
	        workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);
	        //更新流程表的状态
	        ToWorkFlow workFlow = new ToWorkFlow();
	        workFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getName());//Loaner_Process
	        workFlow.setCaseCode(caseCode);
	        ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(workFlow);
	        if (record != null) {
	            record.setStatus(WorkFlowStatus.COMPLETE.getCode());
	            toWorkFlowService.updateByPrimaryKeySelective(record);
	        }
		}catch(BusinessException e) {
	      	throw new BusinessException("交易顾问派单流程结束异常！");
	     }
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-03-28
	 * 
	 * @des:交易顾问派单信息提交
	 */
	@Override
	public void loanerProcessSubmit(ToMortgage toMortgage, String caseCode,
			String taskId, String processInstanceId, int bankLevel) {
		if ((null == caseCode || "".equals(caseCode))
				|| (null == taskId || "".equals(taskId))
				|| (null == processInstanceId || "".equals(processInstanceId))) {
			throw new BusinessException("交易顾问派单流程请求参数异常！");
		}
		AjaxResponse<String> response = new AjaxResponse<String>();
		List<RestVariable> variables = new ArrayList<RestVariable>();
		// 更新 信贷员信息、贷款机构信息
		ToMortgage toMortgageDTO = vilidateMortage(toMortgage);// 验证前台传来的参数
		try {
			// 提交流程
			variables.add(new RestVariable("bankLevel", bankLevel));
			variables.add(new RestVariable("mainBankChoose", false));
			workFlowManager.submitTask(variables, taskId, processInstanceId,
					null, caseCode);
			// 派单给BC级别银行的信贷员之后,启动银行分级审批
			if (bankLevel == 1 || bankLevel == 9) {
				toMortgageService.startTmpBankWorkFlow(caseCode,
						processInstanceId);
			}
			toMortgageService.updateToMortgage(toMortgageDTO);// toMortgage
																// 主键pkid作为条件更新
			response.setMessage("交易顾问派单信息提交成功！");
		} catch (BusinessException e) {
			throw new BusinessException("交易顾问派单流程结束异常！");
		}
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-03-29
	 * 
	 * @des:交易顾问派单流程是否已起判断
	 */
	@Override
	public AjaxResponse<String> isLoanerProcessStart(String caseCode) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		try {
			ToWorkFlow toWorkFlow = new ToWorkFlow();
			toWorkFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getName());
			toWorkFlow.setCaseCode(caseCode);
			// 查询流程表记录
			ToWorkFlow record = toWorkFlowService
					.queryActiveToWorkFlowByCaseCodeBusKey(toWorkFlow);
			if (null == record) {
				response.setSuccess(true);
				response.setMessage("交易顾问派单流程没有启动！");
			} else {
				response.setSuccess(false);
				response.setMessage("交易顾问派单流程已经启动，请勿重复操作！");
			}

		} catch (BusinessException e) {
			response.setSuccess(false);
			throw new BusinessException("查询交易顾问派单流程是否启动异常！");
		}
		return response;
	}

	/**
	 * @author:caoy 验证传来的贷款参数
	 * @param toMortgage
	 * @return
	 */
	private ToMortgage vilidateMortage(ToMortgage toMortgage) {
		if (toMortgage == null) {
			throw new BusinessException("参数错误");
		}
		ToMortgage toMortgageDTO = new ToMortgage();
		if (toMortgage.getComAmount() != null) {
			BigDecimal b2 = new BigDecimal(10000);
			toMortgageDTO.setComAmount(toMortgage.getComAmount().multiply(b2));
		}
		if (toMortgage.getPrfAmount() != null) {
			BigDecimal b2 = new BigDecimal(10000);
			toMortgageDTO.setComAmount(toMortgage.getPrfAmount().multiply(b2));
		}
		if (toMortgage.getPkid() == null || "".equals(toMortgage.getPkid())) {
			throw new BusinessException("参数错误");
		}
		if (toMortgage.getLoanerName() == null
				|| "".equals(toMortgage.getLoanerName())) {
			throw new BusinessException("信贷员名字为空");
		}
		if (toMortgage.getLoanerPhone() == null
				|| "".equals(toMortgage.getLoanerPhone())) {
			throw new BusinessException("信贷员电话为空");
		}
		if (toMortgage.getLoanerId() == null
				|| "".equals(toMortgage.getLoanerId())) {
			throw new BusinessException("信贷员主键为空");
		}
		if (toMortgage.getLoanerOrgId() == null
				|| "".equals(toMortgage.getLoanerOrgId())) {
			throw new BusinessException("信贷员组织主键为空");
		}
		if (toMortgage.getLoanerOrgCode() == null
				|| "".equals(toMortgage.getLoanerOrgCode())) {
			throw new BusinessException("信贷员组织代为空");
		}
		if (toMortgage.getIsLoanerArrive() == null
				|| "".equals(toMortgage.getIsLoanerArrive())) {
			throw new BusinessException("信贷员是否到场为空");
		}
		if (toMortgage.getIsTmpBank() == null
				|| "".equals(toMortgage.getIsTmpBank())) {
			throw new BusinessException("临时银行选项为空");
		}
		if (toMortgage.getFinOrgCode() == null
				|| "".equals(toMortgage.getFinOrgCode())) {
			throw new BusinessException("选择的银行选项为空");
		}
		toMortgageDTO.setLoanerId(toMortgage.getLoanerId());// 信贷员ID
		toMortgageDTO.setLoanerOrgId(toMortgage.getLoanerOrgId());// 信贷员组织id
		toMortgageDTO.setLoanerOrgCode(toMortgage.getLoanerOrgCode());// 信贷员组织code
		toMortgageDTO.setLoanerName(toMortgage.getLoanerName());// 信贷员名称
		toMortgageDTO.setLoanerPhone(toMortgage.getLoanerPhone());// 信贷员电话
		toMortgageDTO.setIsLoanerArrive(toMortgage.getIsLoanerArrive());// 信贷员是否到场
		toMortgageDTO.setIsTmpBank(toMortgage.getIsTmpBank());// 是否是临时银行
		toMortgageDTO.setFinOrgCode(toMortgage.getFinOrgCode());// 贷款银行
		toMortgageDTO.setPkid(toMortgage.getPkid());// 贷款表更新主键
		toMortgageDTO.setComAmount(toMortgage.getComAmount());// 商贷金额
		toMortgageDTO.setComDiscount(toMortgage.getComDiscount());// 商贷折扣率
		toMortgageDTO.setComYear(toMortgage.getComYear());// 商贷年份
		toMortgageDTO.setPrfAmount(toMortgage.getPrfAmount());// 公积金金额
		toMortgageDTO.setPrfYear(toMortgage.getPrfYear());// 公积金年份

		return toMortgageDTO;
	}
}
