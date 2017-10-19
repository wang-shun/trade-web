package com.centaline.trans.task.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.bizwarn.entity.BizWarnInfo;
import com.centaline.trans.bizwarn.repository.BizWarnInfoMapper;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.enums.EventTypeEnum;
import com.centaline.trans.common.enums.MessageEnum;
import com.centaline.trans.common.enums.TransDictEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.repository.TgServItemAndProcessorMapper;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.engine.bean.ExecuteAction;
import com.centaline.trans.engine.bean.ExecuteGet;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.repository.ToWorkFlowMapper;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.entity.ActRuEventSubScr;
import com.centaline.trans.task.repository.ActRuEventSubScrMapper;
import com.centaline.trans.task.service.MortgageSelectService;
import com.centaline.trans.task.vo.MortgageSelecteVo;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
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
	private TransplanServiceFacade transplanServiceFacade;
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
	@Autowired
	private BizWarnInfoMapper bizWarnInfoMapper;
	@Autowired
	private ProcessInstanceService processInstanceService;

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
				transplanServiceFacade.updateByPrimaryKeySelective(plan);
			} else {
				plan.setCaseCode(vo.getCaseCode());
				plan.setPartCode("LoanRelease");
				transplanServiceFacade.insertSelective(plan);
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
			ToCase toCase = caseMapper.findToCaseByCaseCode(vo.getCaseCode());
			User oldUser = uamUserOrgService.getUserById(toCase.getLeadingProcessId());

			tsiap.setCaseCode(vo.getCaseCode());
			tsiap.setPreProcessorId(oldUser.getId());
			tsiap.setPreOrgId(oldUser.getOrgId());
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
	public boolean submit2(MortgageSelecteVo vo) {
		// 开始处理流程引擎
		List<RestVariable> variables = new ArrayList<RestVariable>();
		editRestVariables(variables, vo.getMortageService());

		boolean b = workFlowManager.submitTask(variables, vo.getTaskId(), vo.getProcessInstanceId(), null, vo.getCaseCode());
		ToWorkFlow workF = toWorkFlowService.queryWorkFlowByInstCode(vo.getProcessInstanceId());
		if(workF!=null){
			vo.setProcessDefinitionId(workF.getProcessDefinitionId());
		}
		loanRequirementChange(vo);

		BizWarnInfo bizWarnInfo = bizWarnInfoMapper.selectByCaseCode(vo.getCaseCode());
		if(bizWarnInfo != null){
			bizWarnInfo.setStatus("1");
			bizWarnInfoMapper.updateStatusInMortgageSelect(bizWarnInfo);   //当操作人确定好贷款选择之后，商贷预警信息状态就更改为已解除
		}
		
		return b;
	};

	@Override
	public void loanRequirementChange(MortgageSelecteVo vo) {
		
		// 如果是新流程图
		boolean isNewFlow = false;
		//TODO  取消版本校验 &&"operation_process:40:645454".compareTo(vo.getProcessDefinitionId())<=0 by:yinchao 2017-8-8  
		if(vo.getProcessDefinitionId()!=null ){
			isNewFlow=true;
		}

		if(isNewFlow) {
			ActRuEventSubScr event = new ActRuEventSubScr();
			event.setEventType(MessageEnum.CCAI_UPDATED_MSG.getEventType());
			event.setEventName(MessageEnum.CCAI_UPDATED_MSG.getName());
			event.setProcInstId(vo.getProcessInstanceId());
			//event.setActivityId(EventTypeEnum.TRADEBOUNDARYMSG.getName());
			event.setActivityId(EventTypeEnum.CCAI_UPDATED_MSG_EVENT_CATCH.getName());
			List<ActRuEventSubScr> subScrsList= actRuEventSubScrMapper.listBySelective(event);
			
			event.setEventType(MessageEnum.MORTGAGE_FINISH_MSG.getEventType());
			event.setEventName(MessageEnum.MORTGAGE_FINISH_MSG.getName());
			event.setProcInstId(vo.getProcessInstanceId());
			event.setActivityId(EventTypeEnum.INTERMEDIATECATCHEVENT.getName());
			List<ActRuEventSubScr> mortSubScrsList= actRuEventSubScrMapper.listBySelective(event);
			if (CollectionUtils.isEmpty(subScrsList)&&CollectionUtils.isEmpty(mortSubScrsList)) {
				throw new BusinessException("当前流程下不允许变更贷款需求！");
			}
			String mortType = vo.getMortageService();
			if(mortType==null) {
				throw new BusinessException("请选择相应的贷款需求！");
			}
			doBusiness(vo);
			
			ToWorkFlow wf=new ToWorkFlow();
			String processDfId=null;
			wf.setCaseCode(vo.getCaseCode());
			if(mortType.equals(ConstantsUtil.NO_LOAN)) {
				// 发送边界消息
				/*List<RestVariable> variables = new ArrayList<RestVariable>();
				editRestVariables(variables, vo.getMortageService());

				messageService.sendMortgageSelectMsgByBoudary(vo.getProcessInstanceId(), variables);*/
				//修改流程变量
			/*	for(RestVariable var:variables){
					workFlowManager.setVariableByProcessInsId(vo.getProcessInstanceId(),var.getName(),var);
				}*/
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
				processDfId=propertyUtilsService.getProcessDfId("ComLoan_Process");
			} else if(mortType.equals(ConstantsUtil.PSF_LOAN)) {
				wf.setBusinessKey(WorkFlowEnum.PSFLOAN_PROCESS.getName());
				processDfId=propertyUtilsService.getProcessDfId("PSFLoan_Process");
			}else if(mortType.equals(ConstantsUtil.COM_PSF_LOAN)){ 
				wf.setBusinessKey(WorkFlowEnum.COMANDPSFLOAN_PROCESS.getName());
				processDfId=propertyUtilsService.getProcessDfId("ComLoanAndPSFLoan_Process");
			}else {
				wf.setBusinessKey(WorkFlowEnum.LOANLOST_PROCESS.getName());
				processDfId=propertyUtilsService.getProcessDfId("LoanLost_Process");
/*				wf.setBusinessKey(WorkFlowEnum.NEWLOANLOST_PROCESS.getName());
				processDfId=propertyUtilsService.getProcessDfId("NewLoanLost_Process");*/
			}
			ToWorkFlow wordkFlowDB = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
			if(wordkFlowDB == null) {
				// 发送边界消息
				/*List<RestVariable> variables = new ArrayList<RestVariable>();
				editRestVariables(variables, vo.getMortageService());
				messageService.sendMortgageSelectMsgByBoudary(vo.getProcessInstanceId(),variables);*/
				
				// 删除所有的贷款流程
				deleteMortFlowByCaseCode(vo.getCaseCode());
				// 重新启动一个新的流程
				User u=uamUserOrgService.getUserById(vo.getPartner());//合作顾问
				Map<String, Object> vars=new HashMap<>();
				//vars.put("partner", u.getUsername());
				//vars.put("caseOwner", u.getUsername());
				vars.put("loan",u.getUsername());
				StartProcessInstanceVo p=processInstanceService.startWorkFlowByDfId(processDfId, vo.getCaseCode(), vars);
				// 设置当前任务的执行人
				ToCase toCase = toCaseService.findToCaseByCaseCode(vo.getCaseCode());
				workFlowManager.setAssginee(p.getId(), toCase.getLeadingProcessId(), vo.getCaseCode());
				
				ToWorkFlow workFlow = new ToWorkFlow();
				workFlow.setCaseCode(vo.getCaseCode());
				workFlow.setBizCode(vo.getCaseCode());
				workFlow.setBusinessKey(wf.getBusinessKey());
				workFlow.setInstCode(p.getId());
				workFlow.setProcessDefinitionId(processDfId);
				workFlow.setProcessOwner(vo.getPartner());
				toWorkFlowService.insertSelective(workFlow);
			} 
			
			
		} else {

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
			action.setMessageName(MessageEnum.CCAI_UPDATED_MSG.getName());
			action.setVariables(variables);
			workFlowManager.executeAction(action);
			workFlowManager.claimByInstCode(vo.getProcessInstanceId(), vo.getCaseCode(), null);
		}
		
		
		/*
		 * @author:zhuody
		 * @date:2017-04-10
		 * @des: 贷款需求选择，只要不是委托中原办理的商贷， 删除原先的信贷员派单流程
		 * 
		 * */
		
		/*	if(null == vo.getMortageService() || "".equals(vo.getMortageService())){
			throw new BusinessException("贷款需求变更类型不明确！");
		}
		if (!"1".equals(vo.getMortageService())){
			//删除信贷员派单和分级银行审批流程
			deleteLoanerProcess(vo);
		}*/
	}
	
	
	/*
	 * @author:zhuody
	 * @date:2017-04-10
	 * @des: 交易顾问派单和分级银行审批流程删除
	 * 
	 * */
	private void deleteLoanerProcess(MortgageSelecteVo vo){		
		//First 删除交易顾问派单流程
		ToWorkFlow loanerProcessWf = new ToWorkFlow();
		loanerProcessWf.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getName());
		loanerProcessWf.setCaseCode(vo.getCaseCode());		
		toMortgageService.deleteTmpBankProcess(loanerProcessWf);
		toWorkFlowService.deleteWorkFlowByProperty(loanerProcessWf);
		
		
		//second 删除临时银行流程相关
		ToWorkFlow twf = new ToWorkFlow();

		twf.setBusinessKey(WorkFlowEnum.TMP_BANK_DEFKEY.getCode());
		twf.setCaseCode(vo.getCaseCode());
		toMortgageService.deleteTmpBankProcess(twf);
		//将T_TO_WORKFLOW 和 T_HI_WORKFLOW 中的status设置为2 非正常结束
		toWorkFlowService.deleteWorkFlowByProperty(twf);		
		
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
		if (ConstantsUtil.COM_LOAN.equals(vo.getMortageService()) ) {  //按揭
			serivceCode = "3000400101";
		} else if (ConstantsUtil.PSF_LOAN.equals(vo.getMortageService())) { //公积金
			serivceCode = "3000400201";
		}else if(ConstantsUtil.COM_PSF_LOAN.equals(vo.getMortageService())){ //组合
			serivceCode = "3000400301";
		}
		
		ToCase record=new ToCase();
		record.setCaseCode(vo.getCaseCode());
		record.setLoanReq(vo.getMortageService()); //贷款需求
		caseMapper.updateByCaseCodeSelective(record);

		tgServItemAndProcessorMapper.deleteMortageServItem(vo.getCaseCode());
		
		if (!ConstantsUtil.NO_LOAN.equals(vo.getMortageService())) {// 有贷款
			ToTransPlan queryPlan = new ToTransPlan();
			queryPlan.setCaseCode(vo.getCaseCode());
            //by wbzhouht 在贷款需求选择这里插入的放款时间的partCode应该是LoanRelease,用MortgageSelect会导致在交易计划表中找不到该信息，导致重复插入
			queryPlan.setPartCode("LoanRelease");
			queryPlan = transplanServiceFacade.findTransPlan(queryPlan);
			ToTransPlan plan = new ToTransPlan();
			plan.setEstPartTime(vo.getEstPartTime());
			if (queryPlan != null) {
				plan.setPkid(queryPlan.getPkid());
				transplanServiceFacade.updateByPrimaryKeySelective(plan);
			} else {
				plan.setCaseCode(vo.getCaseCode());
				plan.setPartCode("LoanRelease");
				transplanServiceFacade.insertSelective(plan);
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
			ToCase toCase = caseMapper.findToCaseByCaseCode(vo.getCaseCode());
			User oldUser = uamUserOrgService.getUserById(toCase.getLeadingProcessId());
			
			tsiap.setCaseCode(vo.getCaseCode());
			tsiap.setPreProcessorId(oldUser.getId());
			tsiap.setPreOrgId(oldUser.getOrgId());
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
		exGet.setMessageEventSubscriptionName(MessageEnum.CCAI_UPDATED_MSG.getName());
		ActRuEventSubScr condition =new ActRuEventSubScr();
		condition.setEventType(MessageEnum.CCAI_UPDATED_MSG.getEventType());
		condition.setEventName(MessageEnum.CCAI_UPDATED_MSG.getName());
		condition.setProcInstId(instId);
		List<ActRuEventSubScr>subScrs= actRuEventSubScrMapper.listBySelective(condition);
		if(subScrs != null && !subScrs.isEmpty()){
			/*for (ActRuEventSubScr actRuEventSubScr : subScrs) {
				if(!"TradeBoundaryMsg".equals(actRuEventSubScr.getActivityId())){
					return actRuEventSubScr;
				}
				
			}*/
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
		restVariable5.setName("ComLoanAndPSFLoanNeed");

		if ("1".equals(loanTyby)) {/*  按揭贷 */
			restVariable1.setValue(true);
			restVariable2.setValue(false);
			restVariable5.setValue(false);
		} else if ("2".equals(loanTyby)) {/*公积金 */
			restVariable1.setValue(false);
			restVariable2.setValue(true);
			restVariable5.setValue(false);
		} else if ("3".equals(loanTyby)) {/*组合贷 */
			restVariable1.setValue(false);
			restVariable2.setValue(false);
			restVariable5.setValue(true);
		} else if ("4".equals(loanTyby)) {/* 自办 公积金 */
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
