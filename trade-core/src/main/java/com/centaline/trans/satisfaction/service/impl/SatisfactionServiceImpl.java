package com.centaline.trans.satisfaction.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.repository.VCaseTradeInfoMapper;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.SatisfactionStatusEnum;
import com.centaline.trans.common.enums.SatisfactionTypeEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
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
import com.centaline.trans.satisfaction.entity.ToSatisfaction;
import com.centaline.trans.satisfaction.repository.ToSatisfactionMapper;
import com.centaline.trans.satisfaction.service.SatisfactionService;
import com.centaline.trans.task.repository.ToSignMapper;
import com.centaline.trans.task.service.ToHouseTransferService;

@Service
public class SatisfactionServiceImpl implements SatisfactionService {
	
	@Autowired
	private ToSatisfactionMapper toSatisfactionMapper;
	
	@Autowired
	private VCaseTradeInfoMapper vCaseTradeInfoMapper;
	
	@Autowired
	private ToSignMapper toSignMapper;
	@Autowired
	private ToHouseTransferService toHouseTransferService;
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	PropertyUtilsServiceImpl propertyUtilsService;
	@Autowired
	ToCaseService toCaseService;
	@Autowired
	TgServItemAndProcessorService tgServItemAndProcessorService;
	@Autowired(required = true)
	ToWorkFlowService toWorkFlowService;
	@Autowired
	WorkFlowManager workFlowManager;
	@Autowired
	TaskService taskService;
	@Autowired
	UamBasedataService uamBasedataService;
	@Autowired
	UamUserOrgService uamUserOrgService;
	@Autowired
	UamSessionService uamSessionService;

	@Override
	public int update(ToSatisfaction toSatisfaction) {
		return toSatisfactionMapper.updateByPrimaryKey(toSatisfaction);
	}
	
	@Override
	public int updateSelective(ToSatisfaction toSatisfaction) {
		return toSatisfactionMapper.updateByPrimaryKeySelective(toSatisfaction);
	}

	@Override
	public int insert(ToSatisfaction toSatisfaction) {
		return toSatisfactionMapper.insert(toSatisfaction);
	}
	
	@Override
	public int insertSelective(ToSatisfaction toSatisfaction) {
		return toSatisfactionMapper.insertSelective(toSatisfaction);
	}

	@Override
	public List<ToSatisfaction> queryToSatisfactionList() {
		return toSatisfactionMapper.selectAll();
	}

	@Override
	public ToSatisfaction queryToSatisfactionById(Long id) {
		return toSatisfactionMapper.selectByPrimaryKey(id);
	}

	@Override
	public ToSatisfaction queryToSatisfactionByCaseCode(String caseCode) {
		return toSatisfactionMapper.selectByCaseCode(caseCode);
	}

	@Override
	public void handleAfterSign(String caseCode, String signerId, String type) {
		if(SatisfactionTypeEnum.NEW.getCode().equals(type)){
			ToSatisfaction toSatisfaction = new ToSatisfaction();
			toSatisfaction.setType(type);
			toSatisfaction.setCaseCode(caseCode);
			String castsatCode = uamBasedataService.nextSeqVal("CASTSAT_CODE", new SimpleDateFormat("yyyyMM").format(new Date()));
			toSatisfaction.setCastsatCode(castsatCode);
			toSatisfaction.setStatus(SatisfactionStatusEnum.DEFAULT.getCode());
			toSatisfaction.setCreateBy(signerId);
			toSatisfaction.setCreateTime(new Date());
			insertSelective(toSatisfaction);
		}else{
			ToSatisfaction toSatisfaction = queryToSatisfactionByCaseCode(caseCode);
			toSatisfaction.setType(type);
			toSatisfaction.setUpdateBy(signerId);
			toSatisfaction.setUpdateTime(new Date());
			updateSelective(toSatisfaction);
		}

	}

	@Override
	public void handleAfterGuohu(String caseCode, String guohuerId, Date guohuTime) {
		ToWorkFlow record = new ToWorkFlow();
		record.setCaseCode(caseCode);
		record.setBusinessKey(WorkFlowEnum.SATIS_DEFKEY.getCode());
		ToWorkFlow wf = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(record);
		if(wf != null){
			messageService.sendSatisFinishMsgByIntermi(wf.getInstCode());
			
			ToSatisfaction toSatisfaction = queryToSatisfactionByCaseCode(caseCode);
			toSatisfaction.setStatus(SatisfactionStatusEnum.GUOHU_SURVEY_ING.getCode());
			toSatisfaction.setUpdateBy(guohuerId);
			toSatisfaction.setUpdateTime(new Date());
			update(toSatisfaction);
		}
	}

	@Override
	public void dispatch(String caseCodes, String userId) {
		SessionUser user = uamSessionService.getSessionUser();
	    SessionUser caller = uamSessionService.getSessionUserById(userId);
	    
	    uamSessionService.getSessionUserById(userId);
	    List<String> list = Arrays.asList(caseCodes.split(","));
	    list.forEach(caseCode -> {
	        /**
	         * 查询案件签约和过户阶段对应的交易顾问
	         * 1.字典表查出该案件下签约(3000401001)和过户(3000401002)对应的SRV_CODE
	         * 2.sctrans.T_TG_SERV_ITEM_AND_PROCESSOR表查出SRV_CODE对应的PROCESSORID
	         * 3.根据PROCESSORID查出用户信息
	         */
	        TgServItemAndProcessor record = new TgServItemAndProcessor();
	        record.setCaseCode(caseCode);
	        record.setSrvCode("3000401001");
	        TgServItemAndProcessor processor1 = tgServItemAndProcessorService.findTgServItemAndProcessor(record);
	        record.setSrvCode("3000401002");
	        TgServItemAndProcessor processor2 = tgServItemAndProcessorService.findTgServItemAndProcessor(record);
	        SessionUser consultant1 = uamSessionService.getSessionUserById(processor1.getProcessorId());
	        SessionUser consultant2 = uamSessionService.getSessionUserById(processor2.getProcessorId());
	        List<RestVariable> variables = new ArrayList<RestVariable>();
	        variables.add(new RestVariable("caller", caller.getUsername()));
	        variables.add(new RestVariable("consultant1",consultant1.getUsername()));
	        variables.add(new RestVariable("consultant2",consultant2.getUsername()));
	        variables.add(new RestVariable("signResult", false));//此处做默认设定，否则workFlowManager.setVariableByProcessInsId会报错
	        StartProcessInstanceVo vo = workFlowManager.startWorkFlow(new ProcessInstance(propertyUtilsService.getSatisProcessDfKey(),
	        		caseCode, variables));
	        /**过户案件特殊处理---START**/
	        String status = SatisfactionStatusEnum.SIGN_SURVEY_ING.getCode();
	        ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
	        Date guohuApplyTime = vCaseTradeInfoMapper.selectGuohuSubTime(caseCode);
	        if(guohuApplyTime != null){
	        	//跳过签约回访并发送消息，直接生成过户回访任务
	        	workFlowManager.setVariableByProcessInsId(vo.getId(), "signResult", new RestVariable("signResult", true));
	        	PageableVo pageableVo = taskService.listTasks(vo.getId(), false);
	    		List<TaskVo> taskList = pageableVo.getData();
	    		taskList.forEach(task -> {
	    			if ("SignSurvey".equals(task.getTaskDefinitionKey())) {taskService.complete(task.getId() + "");}
	    		});
	    		
		        messageService.sendSatisFinishMsgByIntermi(vo.getId());
		        status = SatisfactionStatusEnum.GUOHU_SURVEY_ING.getCode();
	        }
	        /**过户案件特殊处理---END**/
	        //更新满意度表
	        ToSatisfaction toSatisfaction = queryToSatisfactionByCaseCode(caseCode);
	        toSatisfaction.setStatus(status);
	        toSatisfaction.setUpdateBy(user.getId());
	        toSatisfaction.setUpdateTime(new Date());
	        toSatisfaction.setCallerId(caller.getId());
	        updateSelective(toSatisfaction);
	        //插入流程表
	        ToWorkFlow twf = new ToWorkFlow();
	        twf.setBizCode(toSatisfaction.getPkid().toString());
	        twf.setCaseCode(caseCode);
	        twf.setBusinessKey(WorkFlowEnum.SATIS_DEFKEY.getCode());
	        twf.setProcessDefinitionId(propertyUtilsService.getSatisProcessDfKey());
	        twf.setProcessOwner(user.getId());
	        twf.setInstCode(vo.getId());
	        twf.setStatus(WorkFlowStatus.ACTIVE.getCode());
	        toWorkFlowService.insertSelective(twf);
	    });
	}

	@Override
	public void signPass(ToSatisfaction toSatisfaction, String taskId, String instCode) {
		  SessionUser user = uamSessionService.getSessionUser();
		  //1.更新状态
	      toSatisfaction.setStatus(SatisfactionStatusEnum.GUOHU_SURVEY_WAIT.getCode());
	      toSatisfaction.setSignTime(new Date());
	      toSatisfaction.setUpdateBy(user.getId());
	      toSatisfaction.setUpdateTime(new Date());
	      updateSelective(toSatisfaction);
	      //2.设置流程变量
	      List<RestVariable> variables = new ArrayList<RestVariable>();
	      variables.add(new RestVariable("signResult",true));
	      //3.推进流程
	      workFlowManager.submitTask(variables, taskId, instCode, null, toSatisfaction.getCaseCode());
	}

	@Override
	public void signReject(ToSatisfaction toSatisfaction, String taskId, String instCode) {
		  SessionUser user = uamSessionService.getSessionUser();
		  //1.更新状态
	      toSatisfaction.setStatus(SatisfactionStatusEnum.SIGN_SURVEY_REJECT.getCode());
	      toSatisfaction.setUpdateBy(user.getId());
	      toSatisfaction.setUpdateTime(new Date());
	      updateSelective(toSatisfaction);
	      //2.设置流程变量
	      List<RestVariable> variables = new ArrayList<RestVariable>();
	      variables.add(new RestVariable("signResult",false));
	      //3.推进流程
	      workFlowManager.submitTask(variables, taskId, instCode, null, toSatisfaction.getCaseCode());
	}

	@Override
	public void signFollow(ToSatisfaction toSatisfaction, String taskId, String instCode) {
		  SessionUser user = uamSessionService.getSessionUser();
		  //1.更新状态
	      toSatisfaction.setStatus(SatisfactionStatusEnum.SIGN_SURVEY_ING.getCode());
	      toSatisfaction.setUpdateBy(user.getId());
	      toSatisfaction.setUpdateTime(new Date());
	      updateSelective(toSatisfaction);
	      //2.设置流程变量
	      List<RestVariable> variables = new ArrayList<RestVariable>();
	      //3.推进流程
	      workFlowManager.submitTask(variables, taskId, instCode, null, toSatisfaction.getCaseCode());
	}

	@Override
	public void guohuPass(ToSatisfaction toSatisfaction, String taskId, String instCode) {
		  SessionUser user = uamSessionService.getSessionUser();
		  //1.更新状态
	      toSatisfaction.setStatus(SatisfactionStatusEnum.GUOHU_SURVEY_PASS.getCode());
	      toSatisfaction.setGuohuTime(new Date());
	      toSatisfaction.setCloseTime(new Date());
	      toSatisfaction.setUpdateBy(user.getId());
	      toSatisfaction.setUpdateTime(new Date());
	      updateSelective(toSatisfaction);
	      //2.设置流程变量
	      List<RestVariable> variables = new ArrayList<RestVariable>();
	      variables.add(new RestVariable("guohuResult",true));
	      //3.推进流程
	      workFlowManager.submitTask(variables, taskId, instCode, null, toSatisfaction.getCaseCode());
	}

	@Override
	public void guohuReject(ToSatisfaction toSatisfaction, String taskId, String instCode) {
		  SessionUser user = uamSessionService.getSessionUser();
		  //1.更新状态
	      toSatisfaction.setStatus(SatisfactionStatusEnum.GUOHU_SURVEY_REJECT.getCode());
	      toSatisfaction.setUpdateBy(user.getId());
	      toSatisfaction.setUpdateTime(new Date());
	      updateSelective(toSatisfaction);
	      //2.设置流程变量
	      List<RestVariable> variables = new ArrayList<RestVariable>();
	      variables.add(new RestVariable("guohuResult",false));
	      //3.推进流程
	      workFlowManager.submitTask(variables, taskId, instCode, null, toSatisfaction.getCaseCode());
	}

	@Override
	public void guohuFollow(ToSatisfaction toSatisfaction, String taskId, String instCode) {
		  SessionUser user = uamSessionService.getSessionUser();
		  //1.更新状态
	      toSatisfaction.setStatus(SatisfactionStatusEnum.GUOHU_SURVEY_ING.getCode());
	      toSatisfaction.setUpdateBy(user.getId());
	      toSatisfaction.setUpdateTime(new Date());
	      updateSelective(toSatisfaction);
	      //2.设置流程变量
	      List<RestVariable> variables = new ArrayList<RestVariable>();
	      //3.推进流程
	      workFlowManager.submitTask(variables, taskId, instCode, null, toSatisfaction.getCaseCode());
	}

	@Override
	public void initSatisList() {
		List<ToCase> toCases = toCaseService.findToCaseByStatus(CaseStatusEnum.YQY.getCode());
		toCases.forEach(toCase -> {
			String caseCode = toCase.getCaseCode();
	        //签约
	        handleAfterSign(caseCode, "init", SatisfactionTypeEnum.NEW.getCode());
		});
	}

}
