package com.centaline.trans.mortgage.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.TmpBankStatusEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.mgr.entity.ToSupDocu;
import com.centaline.trans.mgr.service.ToSupDocuService;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.repository.ToMortgageMapper;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.service.UnlocatedTaskService;

@Service
public class ToMortgageServiceImpl implements ToMortgageService {

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

	@Override
	public ToMortgage saveToMortgage(ToMortgage toMortgage) {
		//有记录  update、反之 insert
		if (toMortgage.getPkid() != null) {
				toMortgageMapper.update(toMortgage);
		} else {
				toMortgageMapper.insertSelective(toMortgage);
		}
		//formCommLoan 是否商贷
		if("1".equals(toMortgage.getFormCommLoan())&&StringUtils.isNotBlank(toMortgage.getLastLoanBank())){
			//   重新设定最终贷款银行（商贷）
			toMortgageMapper.restSetLastLoanBank(toMortgage);	
		}
		
		if(null!=toMortgage.getCustCode()){
			TgGuestInfo guest=tgGuestInfoService.selectByPrimaryKey(Long.parseLong(toMortgage.getCustCode()));
			if(guest!=null){
				guest.setWorkUnit(toMortgage.getCustCompany());
				guest.setGuestName(toMortgage.getCustName());
				tgGuestInfoService.updateByPrimaryKeySelective(guest);
			}
		}
		return toMortgage;
	}

	@Override
	public void saveToMortgageAndSupDocu(ToMortgage toMortgage) {
		ToMortgage mortgage=null;
		ToMortgage condition=new ToMortgage();//用这三个条件确定一条商贷的贷款信息,防止前台重复提交数据或者加载数据出问题时数据重复
		condition.setCaseCode(toMortgage.getCaseCode());
		condition.setIsMainLoanBank(toMortgage.getIsMainLoanBank());
		condition.setIsDelegateYucui("1");
		List<ToMortgage>list= toMortgageMapper.findToMortgageByConditionWithCommLoan(condition);
		if(list!=null&&!list.isEmpty()){
			mortgage=list.get(0);
		}
		if (mortgage != null) {
				toMortgage.setPkid(mortgage.getPkid());
				toMortgageMapper.update(toMortgage);
		} else {
			toMortgage.setIsDelegateYucui("1");
			toMortgageMapper.insertSelective(toMortgage);
		}
		if("1".equals(toMortgage.getFormCommLoan())&&StringUtils.isNotBlank(toMortgage.getLastLoanBank())){
			toMortgageMapper.restSetLastLoanBank(toMortgage);	
		}
		ToSupDocu toSupDocu = toMortgage.getToSupDocu();
		ToSupDocu supDocu = toSupDocuService.findByCaseCode(toMortgage
				.getCaseCode());

		if (supDocu != null) {
			supDocu.setSupContent(toSupDocu.getSupContent());
			supDocu.setRemindTime(toSupDocu.getRemindTime());
			toSupDocuService.updateToSupDocu(supDocu);
		} else {
			if (StringUtils.isNotBlank(toSupDocu.getSupContent())) {
				toSupDocu.setCaseCode(toMortgage.getCaseCode());
				toSupDocu.setPartCode("ComLoanProcess");
				toSupDocuService.saveToSupDocu(toSupDocu);
			}
		}
		if(null!=toMortgage.getCustCode()){
			TgGuestInfo guest=tgGuestInfoService.selectByPrimaryKey(Long.parseLong(toMortgage.getCustCode()));
			if(guest!=null){
				guest.setWorkUnit(toMortgage.getCustCompany());
				guest.setGuestName(toMortgage.getCustName());
				tgGuestInfoService.updateByPrimaryKeySelective(guest);
			}
		}
		// 为主流程设置变量
		setEvaReportNeedAtLoanRelease(toMortgage);
	}

	@Override
	public ToMortgage findToMortgageById(Long id) {
		return toMortgageMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateToMortgage(ToMortgage toMortgage) {
		toMortgageMapper.update(toMortgage);

	}

	@Override
	public ToMortgage findToMortgageByCaseCode(String caseCode) {
		ToMortgage toMortgage = toMortgageMapper
				.findToMortgageByCaseCode(caseCode);
		if (toMortgage != null) {
			ToSupDocu toSupDocu = toSupDocuService.findByCaseCode(caseCode);
			toMortgage.setToSupDocu(toSupDocu);
			toMortgage
					.setComAmount(toMortgage.getComAmount() != null ? toMortgage
							.getComAmount().divide(new BigDecimal(10000))
							: null);
			toMortgage.setMortTotalAmount(toMortgage.getMortTotalAmount()!=null?toMortgage.getMortTotalAmount()
					.divide(new BigDecimal(10000)):null);
			toMortgage
					.setPrfAmount(toMortgage.getPrfAmount() != null ? toMortgage
							.getPrfAmount().divide(new BigDecimal(10000))
							: null);
			toMortgage.setToSupDocu(toSupDocu);
		}
		return toMortgage;
	}

	@Override
	public ToMortgage findToMortgageByCaseCode(ToMortgage toMortgage) {
		List<ToMortgage> list = toMortgageMapper
				.findToMortgageByCaseCodeAndBankType(toMortgage);
		if (CollectionUtils.isNotEmpty(list)) {
			ToMortgage mort = null;
			ToSupDocu toSupDocu = toSupDocuService.findByCaseCode(toMortgage
					.getCaseCode());

			if (list.size() == 1) {
				mort = list.get(0);
			} else {
				for (ToMortgage mortgage : list) {
					if (StringUtils.isNotBlank(mortgage.getLastLoanBank())) {
						mort = mortgage;
						break;
					}
				}
			}
			mort.setComAmount(mort.getComAmount() != null ? mort.getComAmount()
					.divide(new BigDecimal(10000)) : null);
			mort.setMortTotalAmount(mort.getMortTotalAmount() != null ?mort.getMortTotalAmount().divide(
					new BigDecimal(10000)):null);
			mort.setPrfAmount(mort.getPrfAmount() != null ? mort.getPrfAmount()
					.divide(new BigDecimal(10000)) : null);
			mort.setToSupDocu(toSupDocu);

			return mort;
		}
		return null;
	}
	@Override
	public ToMortgage findToMortgageByCaseCodeWithCommLoan(ToMortgage toMortgage) {
		toMortgage.setIsDelegateYucui("1");
		List<ToMortgage> list = toMortgageMapper
				.findToMortgageByConditionWithCommLoan(toMortgage);
		if (CollectionUtils.isNotEmpty(list)) {
			ToMortgage mort = null;
			ToSupDocu toSupDocu = toSupDocuService.findByCaseCode(toMortgage
					.getCaseCode());

			if (list.size() == 1) {
				mort = list.get(0);
			} else {
				for (ToMortgage mortgage : list) {
					if (StringUtils.isNotBlank(mortgage.getLastLoanBank())) {
						mort = mortgage;
						break;
					}
				}
			}
/*			mort.setComAmount(mort.getComAmount() != null ? mort.getComAmount()
					.divide(new BigDecimal(10000)) : null);
			mort.setMortTotalAmount(mort.getMortTotalAmount() != null ?mort.getMortTotalAmount().divide(
					new BigDecimal(10000)):null);
			mort.setPrfAmount(mort.getPrfAmount() != null ? mort.getPrfAmount()
					.divide(new BigDecimal(10000)) : null);
					*/
			mort.setToSupDocu(toSupDocu);

			return mort;
		}
		return null;
	}

	@Override
	public ToMortgage findToMortgageByCaseCode2(String caseCode) {

		List<ToMortgage> toMortgageList = toMortgageMapper
				.findToMortgageByCaseCodeNoBlank(caseCode);
		if (CollectionUtils.isNotEmpty(toMortgageList)) {
			ToMortgage mort = null;
			if (toMortgageList.size() == 1) {
				mort = toMortgageList.get(0);
			} else {
				for (ToMortgage toMortgage : toMortgageList) {
					if (StringUtils.isNotEmpty(toMortgage.getLastLoanBank())) {
						mort = toMortgage;
					}
				}
			}
			if (mort == null) {
				return mort;
			}
			mort.setComAmount(mort.getComAmount() != null ? mort.getComAmount()
					.divide(new BigDecimal(10000)) : null);
			mort.setMortTotalAmount(mort.getMortTotalAmount() !=null ?mort.getMortTotalAmount()
					.divide(new BigDecimal(10000)) : null);
			mort.setPrfAmount(mort.getPrfAmount() != null ? mort.getPrfAmount()
					.divide(new BigDecimal(10000)) : null);
			return mort;
		}
		return null;
	}
	

	@Override
	public ToMortgage findToMortgageByMortTypeAndCaseCode(String caseCode, String mortType) {
		ToMortgage toMortgage=new ToMortgage();
		toMortgage.setCaseCode(caseCode);
		toMortgage.setMortType(mortType);
		toMortgage.setIsDelegateYucui("1");
		List<ToMortgage> list=toMortgageMapper.findToMortgageByCondition(toMortgage);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	@Override
	public ToMortgage findToSelfLoanMortgage(String caseCode) {
		ToMortgage toMortgage=new ToMortgage();
		toMortgage.setCaseCode(caseCode);
		toMortgage.setIsDelegateYucui("0");
		List<ToMortgage> list=toMortgageMapper.findToMortgageByCondition(toMortgage);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void inActiveMortageByCaseCode(String caseCode) {
		toMortgageMapper.inActiveMortageByCaseCode(caseCode);
	}

	@Override
	public void submitMortgage(ToMortgage toMortgage, List<RestVariable> variables, String taskId,
			String processInstanceId) {
		
		// 保存贷款信息
		ToMortgage mortgage= findToMortgageById(toMortgage.getPkid());
		mortgage.setApprDate(toMortgage.getApprDate());
		mortgage.setRemark(toMortgage.getRemark());
		saveToMortgage(mortgage);
		
		// 提交任务
		ToCase toCase = toCaseService.findToCaseByCaseCode(toMortgage.getCaseCode());
		workFlowManager.submitTask(variables, taskId, processInstanceId, 
				toCase.getLeadingProcessId(), toMortgage.getCaseCode());
		
		// 发送消息
		ToWorkFlow wf=new ToWorkFlow();
		wf.setCaseCode(toMortgage.getCaseCode());
		wf.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
		ToWorkFlow wordkFlowDB = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);

		if(wordkFlowDB!=null &&  "operation_process:40:645454".compareTo(wordkFlowDB.getProcessDefinitionId())<=0) {
			messageService.sendMortgageFinishMsgByIntermi(wordkFlowDB.getInstCode());
			// 设置主流程任务的assignee
			workFlowManager.setAssginee(wordkFlowDB.getInstCode(), toCase.getLeadingProcessId(), wordkFlowDB.getCaseCode());
			
			// 结束当前流程
			ToWorkFlow workFlowOld =new ToWorkFlow();
			// 流程结束状态
			workFlowOld.setStatus("4");
			workFlowOld.setInstCode(processInstanceId);
			toWorkFlowService.updateWorkFlowByInstCode(workFlowOld);
		}
		
	}
	
	/**
	 * 删除临时银行审批流程
	 * @param twf
	 */
	@Override
	public void deleteTmpBankProcess(ToWorkFlow twf){
		try{
			ToWorkFlow temBankWF= toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(twf);
			//删除临时银行审批任务和流程
			if(temBankWF != null){
				unlocatedTaskService.deleteByInstCode(temBankWF.getInstCode());
				workFlowManager.deleteProcess(temBankWF.getInstCode());
			}
		}catch(Exception e){
			throw new BusinessException("删除临时银行任务和流程失败！");
		}
		
	}

	
	private void setEvaReportNeedAtLoanRelease(ToMortgage toMortgage) {
			ToWorkFlow wf=new ToWorkFlow();
			wf.setCaseCode(toMortgage.getCaseCode());
			wf.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
			ToWorkFlow wordkFlowDB = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
			// 是否需要发起评估报告
			if(StringUtils.isNotBlank(toMortgage.getIfReportBeforeLend()) && "1".equals(toMortgage.getIfReportBeforeLend()) && wordkFlowDB!=null
					&& "operation_process:40:645454".compareTo(wordkFlowDB.getProcessDefinitionId())<=0) {
				String variableName = "EvaReportNeedAtLoanRelease";
				RestVariable restVariable = new RestVariable();
				restVariable.setType("boolean");
				restVariable.setValue(true);
				workFlowManager.setVariableByProcessInsId(wordkFlowDB.getInstCode(), variableName, restVariable);
			}
	}

	@Override
	public AjaxResponse<String> startTmpBankWorkFlow(String caseCode) {
		
		AjaxResponse<String> response = new AjaxResponse<String>();
		
    	try{
		/*流程引擎相关*/
		List<RestVariable> variables = new ArrayList<RestVariable>();
		ToCase te=toCaseService.findToCaseByCaseCode(caseCode);
		String orgId = te.getOrgId();
		SessionUser user = uamSessionService.getSessionUser();
		//查询主管
		User manager = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(orgId, "Manager");
		String parsentId = uamUserOrgService.getOrgById(orgId).getParentId();
		//查询高级主管
		User seniorManager = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(orgId, "Senior_Manager");
		//查询总监
		User director = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(parsentId, "director");
		
		variables.add(new RestVariable("Manager",manager.getUsername()));
		variables.add(new RestVariable("SeniorManager",seniorManager == null?null:seniorManager.getUsername()));
		variables.add(new RestVariable("director",director.getUsername()));

		//启动 
		ProcessInstance process = new ProcessInstance(
				propertyUtilsService.getProcessTmpBankAuditDfKey(), caseCode, variables);
		StartProcessInstanceVo vo = workFlowManager.startCaseWorkFlow(process, manager.getUsername(),caseCode);
		//插入工作流表
		ToWorkFlow toWorkFlow = new ToWorkFlow();
		toWorkFlow.setBusinessKey(WorkFlowEnum.TMP_BANK_BUSSKEY.getCode());
		toWorkFlow.setCaseCode(caseCode);
		toWorkFlow.setInstCode(vo.getId());
		toWorkFlow.setProcessDefinitionId(propertyUtilsService.getProcessTmpBankAuditDfKey());
		toWorkFlow.setProcessOwner(user.getId());
		toWorkFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
		toWorkFlowService.insertSelective(toWorkFlow);
		
		//更新贷款表临时银行状态为默认：‘’
		ToMortgage mortageDb = findToMortgageByCaseCode2(caseCode);
		if(mortageDb != null){
			mortageDb.setComAmount(mortageDb.getComAmount() != null?mortageDb.getComAmount().multiply(new BigDecimal(10000)):null);
			mortageDb.setMortTotalAmount(mortageDb.getMortTotalAmount() != null?mortageDb.getMortTotalAmount().multiply(new BigDecimal(10000)):null);
			mortageDb.setPrfAmount(mortageDb.getPrfAmount() != null?mortageDb.getPrfAmount().multiply(new BigDecimal(10000)):null);
			mortageDb.setTmpBankStatus(TmpBankStatusEnum.INAPPROVAL.getCode());
			updateToMortgage(mortageDb);
		}
		response.setSuccess(true);
		response.setMessage("已成功开启临时银行审批流程！");
	}catch(Exception e){
		response.setSuccess(false);
		response.setMessage(e.getMessage());
	}
	return response;

	}

	@Override
	public AjaxResponse<?> tmpBankThriceAduit(ToMortgage mortage, String prAddress, String tmpBankName, String tmpBankCheck,
			String taskId, String bankCode, String temBankRejectReason, String processInstanceId, String caseCode,
			String post) {
		SessionUser user=uamSessionService.getSessionUser();

		if("manager".equals(post)){
			boolean isManagerApprove = false;
			if("true".equals(tmpBankCheck)){
				isManagerApprove = true;
			}
				
			//更新案件信息
			ToMortgage mortageDb= findToMortgageById(mortage.getPkid());

			if(!isManagerApprove){
//				mortageDb.setRecLetterNo("");
//				mortageDb.setTmpBankUpdateBy("");
//				mortageDb.setIsTmpBank("0");
//				mortageDb.setLastLoanBank("");
//				mortageDb.setFinOrgCode("");
				mortageDb.setTmpBankStatus(TmpBankStatusEnum.REJECT.getCode());
				mortageDb.setTmpBankRejectReason(temBankRejectReason);
				//更新流程状态为‘4’：已完成
				ToWorkFlow twf = new ToWorkFlow();
				twf.setBusinessKey(WorkFlowEnum.TMP_BANK_BUSSKEY.getCode());
				twf.setCaseCode(caseCode);
				ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(twf);
				if(record != null){
					record.setStatus(WorkFlowStatus.COMPLETE.getCode());
					toWorkFlowService.updateByPrimaryKeySelective(record);
				}

			}else{
				mortageDb.setFinOrgCode(bankCode);
				mortageDb.setTmpBankUpdateBy(user.getId());
				mortageDb.setTmpBankUpdateTime(new Date());
				mortageDb.setTmpBankStatus(TmpBankStatusEnum.INAPPROVAL.getCode());
				mortageDb.setTmpBankRejectReason("");
			}
			updateToMortgage(mortageDb);	
	
			List<RestVariable> variables = new ArrayList<RestVariable>();
			variables.add(new RestVariable("isManagerApprove",isManagerApprove));
			
			workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);

		}else if("seniorManager".equals(post)){
			boolean isSeniorManagerApprove = false;
			if("true".equals(tmpBankCheck)){
				isSeniorManagerApprove = true;
			}

			//更新案件信息
			ToMortgage mortageDb= findToMortgageById(mortage.getPkid());

			if(!isSeniorManagerApprove ){
//				mortageDb.setRecLetterNo("");
//				mortageDb.setTmpBankUpdateBy("");
//				mortageDb.setIsTmpBank("0");
//				mortageDb.setLastLoanBank("");
//				mortageDb.setFinOrgCode("");
				mortageDb.setTmpBankStatus(TmpBankStatusEnum.REJECT.getCode());
				mortageDb.setTmpBankRejectReason(temBankRejectReason);
				//更新流程状态为‘4’：已完成
				ToWorkFlow twf = new ToWorkFlow();
				twf.setBusinessKey(WorkFlowEnum.TMP_BANK_BUSSKEY.getCode());
				twf.setCaseCode(caseCode);
				ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(twf);
				if(record != null){
					record.setStatus(WorkFlowStatus.COMPLETE.getCode());
					toWorkFlowService.updateByPrimaryKeySelective(record);
				}
			}else{

				//mortageDb.setTmpBankUpdateBy(user.getId());
				mortageDb.setTmpBankUpdateTime(new Date());
			}
			updateToMortgage(mortageDb);			
			
			
			List<RestVariable> variables = new ArrayList<RestVariable>();
			variables.add(new RestVariable("isSeniorManagerApprove",isSeniorManagerApprove ));
			
			workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);
		}else if("director".equals(post)){	
			
			ToMortgage mortageDb= findToMortgageById(mortage.getPkid());
			ToCase c = toCaseService.findToCaseByCaseCode(mortageDb.getCaseCode());
			//更新案件信息
			if("false".equals(tmpBankCheck)){
//				mortageDb.setRecLetterNo("");
//				mortageDb.setTmpBankUpdateBy("");
//				mortageDb.setIsTmpBank("0");
//				mortageDb.setLastLoanBank("");
//				mortageDb.setFinOrgCode("");
				mortageDb.setTmpBankStatus(TmpBankStatusEnum.REJECT.getCode());
				mortageDb.setTmpBankRejectReason(temBankRejectReason);
			}else if("true".equals(tmpBankCheck)){
				mortageDb.setTmpBankStatus(TmpBankStatusEnum.AGREE.getCode());

				//mortageDb.setTmpBankUpdateBy(user.getId());
				mortageDb.setTmpBankUpdateTime(new Date());
			}

			updateToMortgage(mortageDb);		
			
			//更新流程状态为‘4’：已完成
			ToWorkFlow twf = new ToWorkFlow();
			twf.setBusinessKey(WorkFlowEnum.TMP_BANK_BUSSKEY.getCode());
			twf.setCaseCode(caseCode);
			ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(twf);
			if(record != null){
				record.setStatus(WorkFlowStatus.COMPLETE.getCode());
				toWorkFlowService.updateByPrimaryKeySelective(record);
			}
			
			Map<String, Object>params=new HashMap<String, Object>();
			params.put("case_code", mortageDb.getCaseCode());
			params.put("property_address", prAddress);
			params.put("bank", tmpBankName);
			
			String content = uamTemplateService.mergeTemplate("TMP_BANK_REMINDER", params);
			Message message = new Message();
			// 消息标题
			message.setTitle("临时银行处理提醒");
			// 消息类型
			message.setType(MessageType.SITE);
			/* 设置提醒列别 */

			message.setMsgCatagory(MsgCatagoryEnum.NEWS.getCode());

			/* 内容 */
			message.setContent(content);
			/* 发送人 */
			String senderId = user.getId();
			/* 设置发送人 */
			message.setSenderId(senderId);

			uamMessageService.sendMessageByUser(message, c.getLeadingProcessId());
			
			List<RestVariable> variables = new ArrayList<RestVariable>();
			workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);
			}
		
		return AjaxResponse.success();
	}

	
	@Override
	public ToMortgage getMortgageByCaseCode(String caseCode) {
		return toMortgageMapper.getMortgageByCaseCode(caseCode);
	}

	@Override
	public int updateTmpBankStatus(String caseCode) {
		return toMortgageMapper.updateTmpBankStatus(caseCode);
	}
	
	

}
