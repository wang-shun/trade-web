package com.centaline.trans.task.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.EventTypeEnum;
import com.centaline.trans.common.enums.MessageEnum;
import com.centaline.trans.common.enums.TransDictEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.repository.TgServItemAndProcessorMapper;
import com.centaline.trans.common.repository.ToWorkFlowMapper;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.bean.ExecuteAction;
import com.centaline.trans.engine.bean.ExecuteGet;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.bean.TaskHistoricQuery;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.entity.ActRuEventSubScr;
import com.centaline.trans.task.entity.ToTransPlan;
import com.centaline.trans.task.repository.ActRuEventSubScrMapper;
import com.centaline.trans.task.service.MortgageSelectService;
import com.centaline.trans.task.service.ToTransPlanService;
import com.centaline.trans.task.vo.MortgageSelecteVo;
import com.centaline.trans.utils.ConstantsUtil;

@Service
public class MortgageSelectServiceImpl implements MortgageSelectService {
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private TgServItemAndProcessorService tgServItemAndProcessorService;
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private TgServItemAndProcessorMapper tgServItemAndProcessorMapper;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private ToTransPlanService toTransPlanService;
	@Autowired
	private ActRuEventSubScrMapper actRuEventSubScrMapper;
	@Autowired
	private ToMortgageService toMortgageService;
	@Autowired
	private ToCaseMapper caseMapper;
	@Autowired
	ToWorkFlowMapper toWorkFlowMapper;
	@Autowired
	MessageService messageService;
	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired(required = true)
	private PropertyUtilsService propertyUtilsService;
	
	private String getLoanReq(String mortageService){
		if(mortageService==null)return null;
		switch (mortageService) {
		case "0":
			return "0";
			default: return "1";
		}
	}
	@Override
	public boolean submit(MortgageSelecteVo vo) {
		String serivceCode = null;
	
		if ("1".equals(vo.getMortageService()) || "3".equals(vo.getMortageService())) {
			serivceCode = "3000400101";
		} else if ("2".equals(vo.getMortageService())) {
			serivceCode = "3000400201";
		}
		ToCase record=new ToCase();
		record.setCaseCode(vo.getCaseCode());
		record.setLoanReq(getLoanReq(vo.getMortageService()));
		caseMapper.updateByCaseCodeSelective(record);
		
		
		if (!"0".equals(vo.getMortageService())) {// 有贷款
			ToTransPlan plan = new ToTransPlan();
			plan.setEstPartTime(vo.getEstPartTime());
			if (vo.getPkid() != null) {
				plan.setPkid(vo.getPkid());
				toTransPlanService.updateByPrimaryKeySelective(plan);
			} else {
				plan.setCaseCode(vo.getCaseCode());
				plan.setPartCode("LoanRelease");
				toTransPlanService.insertSelective(plan);
			}
		}

		if (serivceCode != null) {
			TgServItemAndProcessor tsiap = new TgServItemAndProcessor();
			tsiap.setCaseCode(vo.getCaseCode());
			tsiap.setSrvCode(serivceCode);
			tsiap = tgServItemAndProcessorService.findTgServItemAndProcessor(tsiap);
			if (tsiap == null) {
				tsiap = new TgServItemAndProcessor();
			}
			User user = uamUserOrgService.getUserById(vo.getPartner());
			tsiap.setCaseCode(vo.getCaseCode());
			tsiap.setProcessorId(vo.getPartner());
			tsiap.setSrvCode(serivceCode);
			tsiap.setSrvCat(getSrcCat(serivceCode));
			tsiap.setOrgId(user.getOrgId());
			if (tsiap.getPkid() == null) {
				tgServItemAndProcessorService.insertSelective(tsiap);
			} else {
				tgServItemAndProcessorMapper.updateByPrimaryKey(tsiap);
			}

		}

		// 开始处理流程引擎

		List<RestVariable> variables = new ArrayList<RestVariable>();
		editRestVariables(variables, vo.getMortageService());

		return workFlowManager.submitTask(variables, vo.getTaskId(), vo.getProcessInstanceId(), null, vo.getCaseCode());

	}

	@Override
	public void loanRequirementChange(MortgageSelecteVo vo) {
		ToWorkFlow workF = toWorkFlowService.queryWorkFlowByInstCode(vo.getProcessInstanceId());
		// 如果是新流程图
		boolean isNewFlow = false;
		if(workF!=null &&"operation_process:34:620096".compareTo(workF.getProcessDefinitionId())<=0){
			isNewFlow=true;
		}
		if(isNewFlow) {
			doBusiness(vo);
			
			String mortType = vo.getMortageService();
			if(mortType==null) {
				throw new BusinessException("请选择相应的贷款需求！");
			}
			ToWorkFlow wf=new ToWorkFlow();
			ProcessInstance processIns = new ProcessInstance();
			wf.setCaseCode(vo.getCaseCode());
			if(mortType.equals(ConstantsUtil.NO_LOAN)) {
				// 发送边界消息
				List<RestVariable> variables = new ArrayList<RestVariable>();
				editRestVariables(variables, vo.getMortageService());
				messageService.sendMortgageSelectMsgByBoudary(vo.getProcessInstanceId(),variables);
				ActRuEventSubScr event = new ActRuEventSubScr();
				event.setEventType(MessageEnum.MORTGAGE_FINISH_MSG.getEventType());
				event.setEventName(MessageEnum.MORTGAGE_FINISH_MSG.getName());
				event.setProcInstId(vo.getProcessInstanceId());
				event.setActivityId(EventTypeEnum.INTERMEDIATECATCHEVENT.getName());
				List<ActRuEventSubScr> subScrsList= actRuEventSubScrMapper.listBySelective(event);
				if (CollectionUtils.isEmpty(subScrsList)) {
					throw new BusinessException("当前流程下不允许变更贷款需求！");
				}
				// 删除所有的贷款流程
				deleteMortFlowByCaseCode(vo.getCaseCode());
				// 发送消息
				messageService.sendMortgageFinishMsgByIntermi(vo.getProcessInstanceId());
				// 设置主流程任务的assignee
				ToCase toCase = toCaseService.findToCaseByCaseCode(vo.getCaseCode());
				workFlowManager.setAssginee(vo.getProcessInstanceId(), toCase.getLeadingProcessId(), toCase.getCaseCode());
				return;
			} else if(mortType.equals(ConstantsUtil.COM_LOAN)) {
				wf.setBusinessKey(WorkFlowEnum.COMLOAN_PROCESS.getName());
				processIns.setProcessDefinitionId(propertyUtilsService.getProcessDfId("ComLoan_Process"));
			} else if(mortType.equals(ConstantsUtil.PSF_LOAN)) {
				wf.setBusinessKey(WorkFlowEnum.PSFLOAN_PROCESS.getName());
				processIns.setProcessDefinitionId(propertyUtilsService.getProcessDfId("PSFLoan_Process"));
			} else {
				wf.setBusinessKey(WorkFlowEnum.LOANLOST_PROCESS.getName());
				processIns.setProcessDefinitionId(propertyUtilsService.getProcessDfId("LoanLost_Process"));
			}
			ToWorkFlow wordkFlowDB = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
			if(wordkFlowDB == null) {
				// 发送边界消息
				List<RestVariable> variables = new ArrayList<RestVariable>();
				editRestVariables(variables, vo.getMortageService());
				messageService.sendMortgageSelectMsgByBoudary(vo.getProcessInstanceId(),variables);
				
				// 删除所有的贷款流程
				deleteMortFlowByCaseCode(vo.getCaseCode());
				// 重新启动一个新的流程
				processIns.setBusinessKey(vo.getCaseCode());
				StartProcessInstanceVo p = workFlowManager.startWorkFlow(processIns);
				// 设置当前任务的执行人
				ToCase toCase = toCaseService.findToCaseByCaseCode(vo.getCaseCode());
				workFlowManager.setAssginee(p.getId(), toCase.getLeadingProcessId(), vo.getCaseCode());
				
				ToWorkFlow workFlow = new ToWorkFlow();
				workFlow.setCaseCode(vo.getCaseCode());
				workFlow.setBusinessKey(wf.getBusinessKey());
				workFlow.setInstCode(p.getId());
				workFlow.setProcessDefinitionId(processIns.getProcessDefinitionId());
				workFlow.setProcessOwner(vo.getPartner());
				toWorkFlowService.insertSelective(workFlow);
			} 
			
			ActRuEventSubScr event = new ActRuEventSubScr();
			event.setEventType(MessageEnum.MORTGAGE_FINISH_MSG.getEventType());
			event.setEventName(MessageEnum.MORTGAGE_FINISH_MSG.getName());
			event.setProcInstId(vo.getProcessInstanceId());
			event.setActivityId(EventTypeEnum.INTERMEDIATECATCHEVENT.getName());
			List<ActRuEventSubScr> subScrsList= actRuEventSubScrMapper.listBySelective(event);
			if (CollectionUtils.isEmpty(subScrsList)) {
				throw new BusinessException("当前流程下不允许变更贷款需求！");
			}
		} else {
			TaskHistoricQuery query =new TaskHistoricQuery();
			query.setFinished(true);
			query.setTaskDefinitionKey("MortgageSelect");
			query.setProcessInstanceId(vo.getProcessInstanceId());
			PageableVo pageableVo=workFlowManager.listHistTasks(query);
			if(pageableVo.getData()==null||pageableVo.getData().isEmpty()){
				throw new BusinessException("请先处理贷款需求选择任务！");
			}
			ActRuEventSubScr subScr = getHightPriorityExecution(vo.getProcessInstanceId());
			if (subScr == null) {
				throw new BusinessException("当前流程下不允许变更贷款需求！");
			}
			doBusiness(vo);

			List<RestVariable> variables = new ArrayList<RestVariable>();
			editRestVariables(variables, vo.getMortageService());
			ExecuteAction action = new ExecuteAction();
			action.setAction("messageEventReceived");
			action.setExecutionId(subScr.getExecutionId());
			action.setMessageName("StartMortgageSelectMsg");
			action.setVariables(variables);
			workFlowManager.executeAction(action);
			workFlowManager.claimByInstCode(vo.getProcessInstanceId(),vo.getCaseCode(),null);
		}
	}
	
	/****
	 *  删除该案件下的所有贷款类型
	 * 
	 * 
	 */
	private void deleteMortFlowByCaseCode(String caseCode) {
		ToWorkFlow workFlow = new ToWorkFlow();
		workFlow.setCaseCode(caseCode);
		List<ToWorkFlow> wordkFlowDBList = toWorkFlowMapper.getMortToWorkFlowByCaseCode(workFlow);
		for(ToWorkFlow workFlowDB : wordkFlowDBList) {
			workFlowManager.deleteProcess(workFlowDB.getInstCode());
			toWorkFlowMapper.deleteWorkFlowByInstCode(workFlowDB.getInstCode());
		}
	}

	/**
	 * 处理业务表单
	 * 
	 * @param vo
	 */
	private void doBusiness(MortgageSelecteVo vo) {
		String serivceCode = null;
		if ("1".equals(vo.getMortageService()) || "3".equals(vo.getMortageService())) {
			serivceCode = "3000400101";
		} else if ("2".equals(vo.getMortageService())) {
			serivceCode = "3000400201";
		}
		
		ToCase record=new ToCase();
		record.setCaseCode(vo.getCaseCode());
		record.setLoanReq(getLoanReq(vo.getMortageService()));
		caseMapper.updateByCaseCodeSelective(record);
		
		tgServItemAndProcessorMapper.deleteMortageServItem(vo.getCaseCode());
		
		if (!"0".equals(vo.getMortageService())) {// 有贷款
			ToTransPlan queryPlan = new ToTransPlan();
			queryPlan.setCaseCode(vo.getCaseCode());
			queryPlan.setPartCode("LoanRelease");
			queryPlan = toTransPlanService.findTransPlan(queryPlan);
			ToTransPlan plan = new ToTransPlan();
			plan.setEstPartTime(vo.getEstPartTime());
			if (queryPlan != null) {
				plan.setPkid(queryPlan.getPkid());
				toTransPlanService.updateByPrimaryKeySelective(plan);
			} else {
				plan.setCaseCode(vo.getCaseCode());
				plan.setPartCode("LoanRelease");
				toTransPlanService.insertSelective(plan);
			}
		}

		if (serivceCode != null) {
			TgServItemAndProcessor tsiap = new TgServItemAndProcessor();
			tsiap.setCaseCode(vo.getCaseCode());
			tsiap.setSrvCode(serivceCode);
			tsiap = tgServItemAndProcessorService.findTgServItemAndProcessor(tsiap);
			if (tsiap == null) {
				tsiap = new TgServItemAndProcessor();
			}
			User user = uamUserOrgService.getUserById(vo.getPartner());
			tsiap.setCaseCode(vo.getCaseCode());
			tsiap.setProcessorId(vo.getPartner());
			tsiap.setSrvCode(serivceCode);
			tsiap.setSrvCat(getSrcCat(serivceCode));
			tsiap.setOrgId(user.getOrgId());
			if (tsiap.getPkid() == null) {
				tgServItemAndProcessorService.insertSelective(tsiap);
			} else {
				tgServItemAndProcessorMapper.updateByPrimaryKey(tsiap);
			}

		}

		toMortgageService.inActiveMortageByCaseCode(vo.getCaseCode());
	}

	public ActRuEventSubScr getHightPriorityExecution(String instId) {
		ExecuteGet exGet = new ExecuteGet();
		exGet.setProcessInstanceId(instId);
		exGet.setMessageEventSubscriptionName("StartMortgageSelectMsg");
		ActRuEventSubScr condition=new ActRuEventSubScr();
		condition.setEventType("message");
		condition.setEventName("StartMortgageSelectMsg");
		condition.setProcInstId(instId);
		List<ActRuEventSubScr>subScrs= actRuEventSubScrMapper.listBySelective(condition);
		if(subScrs != null && !subScrs.isEmpty()){
			for (ActRuEventSubScr actRuEventSubScr : subScrs) {
				if(!"TradeBoundaryMsg".equals(actRuEventSubScr.getActivityId())){
					return actRuEventSubScr;
				}
				
			}
			return subScrs.get(0);// 只剩下交易流程了
		}
		return null;
	}

	private List<RestVariable> editRestVariables(List<RestVariable> variables, String loanTyby) {

		RestVariable restVariable1 = new RestVariable();
		restVariable1.setName("ComLoanNeed");
		RestVariable restVariable2 = new RestVariable();
		restVariable2.setName("PSFLoanNeed");
		RestVariable restVariable5 = new RestVariable();
		restVariable5.setName("SelfLoanNeed");

		if (loanTyby.equals("1")) {/* 中原 组合贷 */
			restVariable1.setValue(true);
			restVariable2.setValue(false);
			restVariable5.setValue(false);
		} else if (loanTyby.equals("2")) {/* 中原 公积金 */
			restVariable1.setValue(false);
			restVariable2.setValue(true);
			restVariable5.setValue(false);
		} else if (loanTyby.equals("3")) {/* 自办 组合贷 */
			restVariable1.setValue(false);
			restVariable2.setValue(false);
			restVariable5.setValue(true);
		} else if (loanTyby.equals("4")) {/* 自办 公积金 */
			restVariable1.setValue(false);
			restVariable2.setValue(false);
			restVariable5.setValue(true);
		} else {/* 无贷款 */
			restVariable1.setValue(false);
			restVariable2.setValue(false);
			restVariable5.setValue(false);
		}

		variables.add(restVariable1);
		variables.add(restVariable2);
		variables.add(restVariable5);

		return variables;
	}

	/**
	 * 通过srvCode查询SrvCat
	 * 
	 * @param srvCode
	 * @return
	 */
	private String getSrcCat(String srvCode) {
		Dict dict = uamBasedataService.findDictByTypeAndCode(TransDictEnum.TFWBM.getCode(), srvCode);
		if (dict == null)
			return null;
		Dict dictF = uamBasedataService.findDictById(dict.getParentId());
		if (dictF == null)
			return null;
		return dictF.getCode();
	}
}
