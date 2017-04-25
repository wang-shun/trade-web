package com.centaline.trans.mortgage.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.comment.service.ToCaseCommentService;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.LoanerStatusEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToPropertyInfoService;
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
import com.centaline.trans.mortgage.entity.ToMortLoaner;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.repository.ToMortgageMapper;
import com.centaline.trans.mortgage.service.LoanerProcessService;
import com.centaline.trans.mortgage.service.ToMortLoanerService;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.service.ToApproveRecordService;
import com.centaline.trans.task.service.UnlocatedTaskService;
import com.centaline.trans.utils.DateUtil;

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

	@Autowired
	private ToPropertyInfoService toPropertyInfoService;

	@Autowired
	private ToMortLoanerService toMortLoanerService;

	@Autowired
	private UamBasedataService uamBasedataService;

	@Autowired
	private ToCaseCommentService toCaseCommentService;

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-03-24
	 * 
	 * @des:启动信贷员流程
	 */
	@Override
	public AjaxResponse<String> startLoanerOrderWorkFlow(String caseCode,
			String loanerUserId, String loanerOrgId, String bankOrgCode,
			int bankLevel, String isMainLoanBank) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		/* 流程引擎相关 */
		List<RestVariable> variables = new ArrayList<RestVariable>();
		SessionUser user = uamSessionService.getSessionUser();

		try {
			ToWorkFlow toWorkFlow = new ToWorkFlow();
			toWorkFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getName());
			toWorkFlow.setCaseCode(caseCode);
			// 查询流程表记录
			ToWorkFlow record = toWorkFlowService
					.queryActiveToWorkFlowByCaseCodeBusKey(toWorkFlow);
			if (null != record) {
				throw new BusinessException("启动失败,该流程正在运行！");
			}
			User loaner = uamUserOrgService.getUserById(loanerUserId);

			variables
					.add(new RestVariable("loanerUserId", loaner.getUsername()));
			variables.add(new RestVariable("bankLevel", bankLevel));
			variables
					.add(new RestVariable("sessionUserId", user.getUsername())); // 派单人
			// 设置流程变量的2中方式
			RestVariable restBankLevel = new RestVariable();
			restBankLevel.setName("bankLevelApprove");
			restBankLevel.setValue(false);
			variables.add(restBankLevel); // 预设值消息信息
			variables.add(new RestVariable("mainBankChoose", false)); // 默认启动流程时，不走作废程序

			// 启动流程
			ProcessInstance process = new ProcessInstance(
					propertyUtilsService.getProcessLoanerDfKey(), caseCode,
					variables);
			StartProcessInstanceVo vo = workFlowManager.startCaseWorkFlow(
					process, loaner.getUsername(), caseCode);

			// 启动流程之后 把交易顾问派单流程直接推送完
			@SuppressWarnings("rawtypes")
			PageableVo pageableVo = taskService.listTasks(vo.getId(), false);// Loaner_Process:4:1030016
			@SuppressWarnings("unchecked")
			List<TaskVo> taskList = pageableVo.getData();
			for (TaskVo task : taskList) {
				if ("LoanerSendOrder".equals(task.getTaskDefinitionKey())) {// ZY-AJ-201605-1460
					taskService.complete(task.getId() + "");
					break;
				}
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("caseCode", caseCode);
			map.put("isMainLoanBank", isMainLoanBank);
			ToMortgage toMortgageInfo = toMortgageMapper
					.findToMortgageByCaseCodeAndDisTime(map);
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
			workFlow.setProcessDefinitionId(propertyUtilsService
					.getProcessLoanerDfKey());
			workFlow.setProcessOwner(user.getId());
			workFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
			toWorkFlowService.insertSelective(workFlow);

			// 派单给BC级别银行的信贷员之后,启动银行分级审批
			// bankLevel == 1 B级银行不审批 变更
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
	 * @date:2017-04-13
	 * 
	 * @des:启动信贷员流程
	 */
	@Override
	public AjaxResponse<String> newStartLoanerOrderWorkFlow(
			ToMortgage toMortgage) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		/* 流程引擎相关 */
		List<RestVariable> variables = new ArrayList<RestVariable>();
		SessionUser user = uamSessionService.getSessionUser();

		String caseCode = toMortgage.getCaseCode() == null ? "" : toMortgage
				.getCaseCode();
		String loanerId = toMortgage.getLoanerId() == null ? "" : toMortgage
				.getLoanerId();
		String bankLevel = toMortgage.getBankLevel() == null ? "" : toMortgage
				.getBankLevel();

		try {
			/*
			 * 拍单流程判断是否 启动以派单表为主 ToWorkFlow toWorkFlow = new ToWorkFlow();
			 * toWorkFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getName());
			 * toWorkFlow.setCaseCode(caseCode); // 查询流程表记录 ToWorkFlow record =
			 * toWorkFlowService
			 * .queryActiveToWorkFlowByCaseCodeBusKey(toWorkFlow); if (null !=
			 * record) { throw new BusinessException("派单流程启动失败,该流程正在运行！"); }
			 */

			User loaner = uamUserOrgService.getUserById(loanerId);

			variables.add(new RestVariable("loanerUserName", loaner
					.getUsername()));
			variables.add(new RestVariable("sessionUserName", user
					.getUsername())); // 派单人

			// 启动流程
			ProcessInstance process = new ProcessInstance(
					propertyUtilsService.getProcessLoanerDfKey(), caseCode,
					variables);
			StartProcessInstanceVo vo = workFlowManager.startCaseWorkFlow(
					process, loaner.getUsername(), caseCode);

			// 具体的业务逻辑处理， 贷款表中 冗余派单人ID、 时间， 取消审核人id、时间信息
			Map<String, Object> mapParam = new HashMap<String, Object>();
			mapParam.put("caseCode", caseCode);
			mapParam.put("isMainLoanBank", toMortgage.getIsMainLoanBank());
			ToMortgage toMortgageInfo = toMortgageMapper
					.findToMortgageByCaseCodeAndDisTime(mapParam);
			String bizCode = "";

			// 添加贷款表中的 信贷员、派单员等信息
			toMortgage.setCaseCode(caseCode);
			toMortgage.setDispachUserId(user.getId());
			toMortgage.setDispachTime(new Date());
			toMortgage.setLoanerId(loanerId);
			toMortgage.setLoanerProcessInstCode(vo.getId());
			toMortgage.setIsActive("1");
			toMortgage.setIsDelegateYucui("1");
			toMortgage.setBankLevel(String.valueOf(bankLevel));

			if (null != toMortgageInfo) {
				toMortgage.setPkid(toMortgageInfo.getPkid());
				toMortgageMapper.update(toMortgage);
				bizCode = toMortgageInfo.getPkid() == null ? ""
						: toMortgageInfo.getPkid().toString();
			} else {
				toMortgageMapper.insertSelective(toMortgage);
				bizCode = toMortgage.getPkid().toString();
			}

			// 冗余 派单流程表信息
			ToPropertyInfo toPropertyInfo = toPropertyInfoService
					.findToPropertyInfoByCaseCode(caseCode);

			// 生产 receiveCode
			String dateStr = DateUtil.getFormatDate(new Date(), "yyyyMMdd");
			String month = dateStr.substring(0, 6);
			String receiveCode = uamBasedataService.nextSeqVal("CASE_AP_CODE",
					month);
			if (null == receiveCode) {
				throw new BusinessException("生成接收编码异常！");
			}

			ToMortLoaner toMortLoaner = new ToMortLoaner();
			toMortLoaner.setCaseCode(caseCode);
			toMortLoaner.setCustName(toMortgage.getCustName());
			if (null != toPropertyInfo) {
				toMortLoaner.setHouAddress(toPropertyInfo.getPropertyAddr());
			}
			toMortLoaner.setReceiveCode(receiveCode);
			toMortLoaner.setMortType(toMortgage.getMortType());
			toMortLoaner.setIsMainLoanBankProcess(toMortgage
					.getIsMainLoanBank());// 1:主选银行派单流程
			toMortLoaner.setMortTotalAmount(toMortgage.getMortTotalAmount());
			toMortLoaner.setComAmount(toMortgage.getComAmount());
			toMortLoaner.setComYear(toMortgage.getComYear());
			toMortLoaner.setComDiscount(toMortgage.getComDiscount());
			toMortLoaner.setPrfAmount(toMortgage.getPrfAmount());
			toMortLoaner.setPrfYear(toMortgage.getPrfYear());
			toMortLoaner.setMortPkid(bizCode);
			toMortLoaner.setLoanerStatus(LoanerStatusEnum.ACCEPTING.getCode());
			toMortLoaner.setSendId(user.getId());
			toMortLoaner.setSendName(user.getRealName());
			toMortLoaner.setSendTime(new Date());
			toMortLoaner.setLoanerId(loanerId);
			toMortLoaner.setLoanerName(loaner.getRealName());
			toMortLoaner.setLoanerPhone(loaner.getMobile());
			toMortLoaner.setLoanerOrgId(loaner.getOrgId());
			toMortLoaner.setLoanerOrgCode(toMortgage.getLoanerOrgCode());
			toMortLoanerService.insertByToMortLoaner(toMortLoaner);

			// 插入工作流表
			ToWorkFlow workFlow = new ToWorkFlow();
			workFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getName());
			workFlow.setCaseCode(caseCode);
			workFlow.setBizCode(String.valueOf(toMortLoaner.getPkid()));// 插入数据的主键返回
			workFlow.setInstCode(vo.getId());
			workFlow.setProcessDefinitionId(propertyUtilsService
					.getProcessLoanerDfKey());
			workFlow.setProcessOwner(user.getId());
			workFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
			toWorkFlowService.insertSelective(workFlow);

			// 注意C级银行 不走派单流程
			/*
			 * if ("9".equals(bankLevel)) {
			 * toMortgageService.startTmpBankWorkFlow(caseCode, vo.getId()); }
			 */

			// 回显 派单时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String dispachTime = sdf.format(date);

			response.setSuccess(true);
			response.setMessage("恭喜，交易顾问派单成功！");
			response.setContent(dispachTime);

		} catch (BusinessException e) {
			response.setSuccess(false);
			throw new BusinessException("Sorry,交易顾问派单失败！");
		}
		return response;
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-04-13
	 * 
	 * @des:信贷员是否接单确认
	 */
	@Override
	public boolean isLoanerAcceptCase(boolean isLonaerAcceptCase,
			String taskId, String processInstanceId, String caseCode,
			String mortgageId, String stateInBank) {

		boolean loanerAccetpFlag = false;
		if ((null == caseCode || "".equals(caseCode))
				|| (null == taskId || "".equals(taskId))
				|| (null == processInstanceId || "".equals(processInstanceId))) {
			throw new BusinessException("信贷员接单确认请求参数异常！");
		}

		List<RestVariable> variables = new ArrayList<RestVariable>();

		try {
			// 信贷员接单
			if (isLonaerAcceptCase == true) {
				variables.add(new RestVariable("loanerAccept", true));

			} else {
				variables.add(new RestVariable("loanerAccept", false));

			}
			// 维护ToMortLoaner业务
			maintainToMortLoaner(mortgageId, stateInBank);
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
	 * @date:2017-04-13
	 * 
	 * @des:维护ToMortLoaner表记录
	 */
	private void maintainToMortLoaner(String id, String stateInBank) {

		SessionUser user = uamSessionService.getSessionUser();
		ToMortLoaner toMortLoaner = toMortLoanerService
				.getToMortLoanerById(Long.parseLong(id));

		if (toMortLoaner != null) {
			// 信贷员接单
			if ("ACCEPT".equals(stateInBank)) {
				toMortLoaner.setReceiveId(user.getId());
				toMortLoaner.setReceiveName(user.getRealName());
				toMortLoaner.setReceiveTime(new Date());
				// 接单之后设置派单状态为待审批
				toMortLoaner.setLoanerStatus(LoanerStatusEnum.AUDITING
						.getCode());
			}
			// 信贷员打回或银行打回
			else if ("REJECT".equals(stateInBank)
					|| "BANKREJECT".equals(stateInBank)) {
				toMortLoaner.setRejectId(user.getId());
				toMortLoaner.setRejectName(user.getRealName());
				toMortLoaner.setRejectTime(new Date());

				// 接单打回之后设置派单状态为接单打回
				if ("REJECT".equals(stateInBank)) {
					toMortLoaner.setLoanerStatus(LoanerStatusEnum.ACC_REJECTED
							.getCode());
				}
				// 审批打回之后设置派单状态为审批打回
				else if ("BANKREJECT".equals(stateInBank)) {
					toMortLoaner.setLoanerStatus(LoanerStatusEnum.AUD_REJECTED
							.getCode());
				}
			}
			// 银行审批成功
			else if ("MORT_APPROVED".equals(stateInBank)) {
				toMortLoaner.setApprovalId(user.getId());
				toMortLoaner.setApprovalName(user.getRealName());
				toMortLoaner.setApprovalTime(new Date());
				// 审批成功之后设置为派单状态为完成
				toMortLoaner.setLoanerStatus(LoanerStatusEnum.COMPLETED
						.getCode());

				toMortLoaner.setFlowStatus(stateInBank);
			} else {
				toMortLoaner.setFlowStatus(stateInBank);
			}

			try {
				toMortLoanerService.updateByPrimaryKeySelective(toMortLoaner);
			} catch (BusinessException e) {
				throw new BusinessException("信贷员派单、银行审核信息更新异常！");
			}
		}
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-03-28
	 * 
	 * @des:信贷员是否接单之银行审批
	 */
	@Override
	public boolean isBankAcceptCase(boolean isBankAcceptCase, String taskId,
			String processInstanceId, String caseCode, String mortgageId,
			String stateInBank) {

		boolean bankAccetpFlag = false;

		if ((null == caseCode || "".equals(caseCode))
				|| (null == taskId || "".equals(taskId))
				|| (null == processInstanceId || "".equals(processInstanceId))) {
			throw new BusinessException("信贷员接单银行确认请求参数异常！");
		}
		List<RestVariable> variables = new ArrayList<RestVariable>();

		try {
			// 信贷员接单
			if (isBankAcceptCase == true) {
				variables.add(new RestVariable("bankBusinessApprove", true));
				// 审批通过 更改流程状态、更新流程状态为'4'：已完成
				/*
				 * ToWorkFlow workFlow = new ToWorkFlow();
				 * workFlow.setBusinessKey
				 * (WorkFlowEnum.LOANER_PROCESS.getName());
				 * workFlow.setCaseCode(caseCode); ToWorkFlow record =
				 * toWorkFlowService
				 * .queryActiveToWorkFlowByCaseCodeBusKey(workFlow);
				 */
				// 查询方式变更，以防主选银行和备选银行 同时派单的重复
				ToWorkFlow record = toWorkFlowService
						.queryWorkFlowByInstCode(processInstanceId);
				if (record != null) {
					record.setStatus(WorkFlowStatus.COMPLETE.getCode());
					toWorkFlowService.updateByPrimaryKeySelective(record);
				}
				ToMortLoaner toMortLoaner = toMortLoanerService
						.getToMortLoanerById(Long.parseLong(mortgageId));
				long pkid = 1;
				if (null != toMortLoaner) {
					if (null != toMortLoaner.getMortPkid()
							&& !"".equals(toMortLoaner.getMortPkid())) {
						pkid = Long.parseLong(toMortLoaner.getMortPkid());
					}
				}

				ToMortgage toMortgage = toMortgageMapper
						.selectByPrimaryKey(pkid);
				if (null != toMortgage) {
					ToMortgage toMortgageForUpdate = new ToMortgage();
					toMortgageForUpdate.setPkid(toMortgage.getPkid());
					toMortgageForUpdate.setTmpBankStatus("3");
					toMortgageForUpdate.setBankApproveTime(new Date()); // 冗余信贷员审核通过时间，在页面做展示
					toMortgageMapper.update(toMortgageForUpdate);
				}

			} else {
				variables.add(new RestVariable("bankBusinessApprove", false));

			}

			// 维护ToMortLoaner业务
			maintainToMortLoaner(mortgageId, stateInBank);

			// 提交流程
			workFlowManager.submitTask(variables, taskId, processInstanceId,
					null, caseCode);
			bankAccetpFlag = true;
		} catch (BusinessException e) {

			throw new BusinessException("信贷员接单流程推进失败！");
		}

		return bankAccetpFlag;
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-04-13
	 * 
	 * @des:交易顾问派单 驳回
	 */
	@Override
	public void loanerProcessDelete(String caseCode, String taskId,
			String processInstanceId, String isMainLoanBankProcess) {
		List<RestVariable> variables = new ArrayList<RestVariable>();
		if ((null == caseCode || "".equals(caseCode))
				|| (null == taskId || "".equals(taskId))
				|| (null == processInstanceId || "".equals(processInstanceId))
				|| (null == isMainLoanBankProcess || ""
						.equals(isMainLoanBankProcess))) {
			throw new BusinessException("取消、驳回交易顾问派单流程请求参数异常！");
		}

		// 派单流程特殊,可能会有 主选银行和备选银行 2条派单记录
		List<ToMortLoaner> toMortLoanerList = new ArrayList<ToMortLoaner>();
		ToMortLoaner toMortLoaner = new ToMortLoaner();
		try {

			ToWorkFlow workFlow = new ToWorkFlow();
			// LOANER_STATUS IN ('AUD_REJECTED','ACC_REJECTED') 状态下的派单流程才能驳回
			toMortLoanerList = toMortLoanerService
					.findToMortLoanerByCaseCodeAndIsMainBank(caseCode,
							isMainLoanBankProcess);
			if (null != toMortLoanerList && toMortLoanerList.size() > 0) {
				toMortLoaner = toMortLoanerList.get(0);
			}

			if (null != toMortLoaner) {
				workFlow.setBizCode(String.valueOf(toMortLoaner.getPkid()));
			}
			// 更新流程表的状态
			workFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getName());// Loaner_Process
			workFlow.setCaseCode(caseCode);

			// 此次添加bizCode是为了区分 主选银行和候选银行 各自的拍单流程
			ToWorkFlow record = toWorkFlowService
					.queryActiveToWorkFlowByCaseCodeBusKeyBizCode(workFlow);

			// 结束流程
			variables.add(new RestVariable("currentProcessEnd", true));
			// 提交流程
			workFlowManager.submitTask(variables, taskId, processInstanceId,
					null, caseCode);

			if (record != null) {
				record.setStatus(WorkFlowStatus.COMPLETE.getCode());
			}

			toWorkFlowService.updateByPrimaryKeySelective(record);

		} catch (BusinessException e) {
			throw new BusinessException("取消交易顾问派单流程异常！");
		}
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-04-20
	 * 
	 * @des:交易顾问派单 流程关闭
	 */
	@Override
	public void loanerProcessCancle(String caseCode, String taskId,
			String processInstanceId, String isMainLoanBankProcess,
			String loanerPkid) {

		if ((null == caseCode || "".equals(caseCode))
				|| (null == taskId || "".equals(taskId))
				|| (null == processInstanceId || "".equals(processInstanceId))
				|| (null == isMainLoanBankProcess || ""
						.equals(isMainLoanBankProcess))) {
			throw new BusinessException("取消、驳回交易顾问派单流程请求参数异常！");
		}

		// 派单流程特殊,可能会有 主选银行和备选银行 2条派单记录
		SessionUser user = uamSessionService.getSessionUser();
		ToMortLoaner toMortLoaner = new ToMortLoaner();
		try {
			// 取消时 根据instCode来查询信息
			ToWorkFlow record = toWorkFlowService
					.queryWorkFlowByInstCode(processInstanceId);

			// 派单列表 取消操作
			if (record != null) {
				record.setStatus(WorkFlowStatus.TERMINATE.getCode());
				toMortLoaner.setPkid(Long.parseLong(record.getBizCode()));
			}
			toWorkFlowService.updateByPrimaryKeySelective(record);

			// 取消的时候 派单表需要修改 取消人的信息

			toMortLoaner.setCancleId(user.getId());
			toMortLoaner.setCancleName(user.getRealName());
			toMortLoaner.setCancleTime(new Date());
			toMortLoaner.setLoanerStatus(LoanerStatusEnum.CANCELED.getCode()); // 取消派单
			toMortLoanerService.updateByPrimaryKeySelective(toMortLoaner);

			// 记录一下取消操作记录
			ToCaseComment toCaseComment = new ToCaseComment();
			toCaseComment.setBizCode(loanerPkid);
			toCaseComment.setCaseCode(caseCode);
			toCaseComment.setType("TRACK");
			toCaseComment.setSource("MORT");
			toCaseComment.setSrvCode("CANCEL");
			toCaseComment.setComment("");
			toCaseComment.setCreateTime(new Date());

			if (user != null) {
				toCaseComment.setCreateBy(user.getId());
				toCaseComment.setCreatorOrgId(user.getServiceDepId());
			}

			// 保存案件操作信息
			toCaseCommentService.insertToCaseComment(toCaseComment);

		} catch (BusinessException e) {
			throw new BusinessException("交易顾问派单流程结束异常！");
		}
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-04-13
	 * 
	 * @des:交易顾问派单信息提交
	 */
	@Override
	public void loanerProcessSubmit(ToMortgage toMortgage, String caseCode,
			String taskId, String processInstanceId, int bankLevel) {
		if ((null == caseCode || "".equals(caseCode))
				|| (null == taskId || "".equals(taskId))
				|| (null == processInstanceId || "".equals(processInstanceId))) {
			throw new BusinessException("交易顾问重新派单流程请求参数异常！");
		}

		List<RestVariable> variables = new ArrayList<RestVariable>();
		// 更新 信贷员信息、贷款机构信息
		ToMortgage toMortgageDTO = vilidateMortage(toMortgage);// 验证前台传来的参数
		SessionUser user = uamSessionService.getSessionUser();
		User loaner = uamUserOrgService
				.getUserById(toMortgageDTO.getLoanerId());

		try {
			// 设置流程变量、提交流程
			variables.add(new RestVariable("currentProcessEnd", true));

			workFlowManager.submitTask(variables, taskId, processInstanceId,
					null, caseCode);
			// 更新流程表的状态为已完成
			ToWorkFlow workFlow = new ToWorkFlow();
			workFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getName());// Loaner_Process
			workFlow.setCaseCode(caseCode);
			ToWorkFlow record = toWorkFlowService
					.queryActiveToWorkFlowByCaseCodeBusKey(workFlow);
			if (record != null) {
				record.setStatus(WorkFlowStatus.COMPLETE.getCode());
				toWorkFlowService.updateByPrimaryKeySelective(record);
			}

			variables.add(new RestVariable("currentProcessEnd", false));
			variables.add(new RestVariable("loanerUserName", loaner
					.getUsername()));
			variables.add(new RestVariable("sessionUserName", user
					.getUsername())); // 派单人
			// 启动流程
			ProcessInstance process = new ProcessInstance(
					propertyUtilsService.getProcessLoanerDfKey(), caseCode,
					variables);
			StartProcessInstanceVo vo = workFlowManager.startCaseWorkFlow(
					process, loaner.getUsername(), caseCode);

			toMortgageService.updateToMortgage(toMortgageDTO);// 主键pkid作为条件更新

			//
			ToMortLoaner toMortLoaner = new ToMortLoaner();
			ToPropertyInfo toPropertyInfo = toPropertyInfoService
					.findToPropertyInfoByCaseCode(caseCode);

			// 冗余 派单流程表信息
			toMortLoaner.setCaseCode(caseCode);
			toMortLoaner.setCustName(toMortgage.getCustName());
			if (null != toPropertyInfo) {
				toMortLoaner.setHouAddress(toPropertyInfo.getPropertyAddr());
			}
			// 生产 receiveCode
			String dateStr = DateUtil.getFormatDate(new Date(), "yyyyMMdd");
			String month = dateStr.substring(0, 6);
			String receiveCode = uamBasedataService.nextSeqVal("CASE_AP_CODE",
					month);
			if (null == receiveCode) {
				throw new BusinessException("生成接收编码异常！");
			}
			toMortLoaner.setReceiveCode(receiveCode);
			toMortLoaner.setMortType(toMortgage.getMortType());
			toMortLoaner.setIsMainLoanBankProcess(toMortgage
					.getIsMainLoanBank());// 1:主选银行派单流程

			toMortLoaner.setMortTotalAmount(toMortgageDTO.getMortTotalAmount());
			toMortLoaner.setComAmount(toMortgageDTO.getComAmount());
			toMortLoaner.setComYear(toMortgageDTO.getComYear());
			toMortLoaner.setComDiscount(toMortgageDTO.getComDiscount());
			toMortLoaner.setPrfAmount(toMortgageDTO.getPrfAmount());
			toMortLoaner.setPrfYear(toMortgageDTO.getPrfYear());
			toMortLoaner.setMortPkid(String.valueOf(toMortgageDTO.getPkid()));
			toMortLoaner.setLoanerStatus(LoanerStatusEnum.ACCEPTING.getCode());
			toMortLoaner.setSendId(user.getId());
			toMortLoaner.setSendName(user.getRealName());
			toMortLoaner.setSendTime(new Date());

			toMortLoaner.setLoanerId(toMortgageDTO.getLoanerId());
			toMortLoaner.setLoanerName(loaner.getRealName());
			toMortLoaner.setLoanerPhone(loaner.getMobile());
			toMortLoaner.setLoanerOrgId(loaner.getOrgId());
			toMortLoaner.setLoanerOrgCode(toMortgage.getLoanerOrgCode());
			toMortLoanerService.insertByToMortLoaner(toMortLoaner);

			// 插入工作流表
			ToWorkFlow newWorkFlow = new ToWorkFlow();
			newWorkFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getName());
			newWorkFlow.setCaseCode(caseCode);
			newWorkFlow.setBizCode(String.valueOf(toMortLoaner.getPkid()));
			newWorkFlow.setInstCode(vo.getId());
			newWorkFlow.setProcessDefinitionId(propertyUtilsService
					.getProcessLoanerDfKey());
			newWorkFlow.setProcessOwner(user.getId());
			newWorkFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
			toWorkFlowService.insertSelective(newWorkFlow);

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
	public AjaxResponse<String> isLoanerProcessStart(String caseCode,
			String isMainLoanBank) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try {
			/*
			 * ToWorkFlow toWorkFlow = new ToWorkFlow();
			 * toWorkFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getName());
			 * toWorkFlow.setCaseCode(caseCode); // 查询流程表记录 ToWorkFlow record =
			 * toWorkFlowService
			 * .queryActiveToWorkFlowByCaseCodeBusKey(toWorkFlow);
			 */

			ToMortLoaner toMortLoaner = new ToMortLoaner();
			toMortLoaner.setCaseCode(caseCode);
			toMortLoaner.setIsMainLoanBankProcess(isMainLoanBank);
			ToMortLoaner toMortLoanerProcess = toMortLoanerService
					.findToMortLoaner(toMortLoaner);

			if (null == toMortLoanerProcess) {
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
		if (toMortgage.getMortTotalAmount() != null) {
			BigDecimal b2 = new BigDecimal(10000);
			toMortgageDTO.setMortTotalAmount(toMortgage.getMortTotalAmount()
					.multiply(b2));
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
		toMortgageDTO.setCustName(toMortgage.getCustName());
		toMortgageDTO.setIsMainLoanBank(toMortgage.getIsMainLoanBank());
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

	@Override
	public void loanerProcessList(HttpServletRequest request) {

		SessionUser user = uamSessionService.getSessionUser();
		String userJob = user.getServiceJobCode();
		boolean queryOrgFlag = false;
		boolean isAdminFlag = false;
		int userJobCode = -1;

		StringBuffer reBuffer = new StringBuffer();
		// 如果不是交易顾问
		if (!userJob.equals(TransJobs.TJYGW.getCode())) {
			// 下面有组别
			queryOrgFlag = true;
			String depString = user.getServiceDepHierarchy();
			String userOrgIdString = user.getServiceDepId();
			if (depString.equals(DepTypeEnum.TYCTEAM.getCode())) {
				reBuffer.append(userOrgIdString);
				userJobCode = 2;
			} else if (depString.equals(DepTypeEnum.TYCQY.getCode())) {
				List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(
						userOrgIdString, DepTypeEnum.TYCTEAM.getCode());
				for (Org org : orgList) {
					reBuffer.append(org.getId());
					reBuffer.append(",");
				}
				reBuffer.deleteCharAt(reBuffer.length() - 1);
				userJobCode = 1;
			} else {
				isAdminFlag = true;
				userJobCode = 0;
			}
		} else {
			userJobCode = 3;
		}

		request.setAttribute("queryOrgs", reBuffer.toString());
		request.setAttribute("queryOrgFlag", queryOrgFlag);
		request.setAttribute("isAdminFlag", isAdminFlag);
		request.setAttribute("userJobCode", userJobCode);
		request.setAttribute("serviceDepId", user.getServiceDepId());// 登录用户的org_id

	}
}
