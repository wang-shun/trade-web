package com.centaline.trans.satisfaction.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.SatisfactionStatusEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.satisfaction.entity.ToSatisfaction;
import com.centaline.trans.satisfaction.repository.ToSatisfactionMapper;
import com.centaline.trans.satisfaction.service.SatisfactionService;

@Service
public class SatisfactionServiceImpl implements SatisfactionService {
	
	@Autowired
	private ToSatisfactionMapper toSatisfactionMapper;
	
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
	public void handleAfterSign(String caseCode, String signerId) {
		ToSatisfaction toSatisfaction = new ToSatisfaction();
		toSatisfaction.setCaseCode(caseCode);
		String castsatCode = uamBasedataService.nextSeqVal("CASTSAT_CODE", new SimpleDateFormat("yyyyMM").format(new Date()));
		toSatisfaction.setSignTime(new Date());
		toSatisfaction.setCastsatCode(castsatCode);
		toSatisfaction.setStatus(SatisfactionStatusEnum.DEFAULT.getCode());
		toSatisfaction.setCreateBy(signerId);
		toSatisfaction.setCreateTime(new Date());
		insertSelective(toSatisfaction);
	}

	@Override
	public void handleAfterGuohuApprove(String caseCode, String guohuerId) {
		ToWorkFlow record = new ToWorkFlow();
		record.setCaseCode(caseCode);
		record.setBusinessKey(WorkFlowEnum.SATIS_DEFKEY.getCode());
		ToWorkFlow wf = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(record);
		if(wf != null){
			messageService.sendSatisFinishMsgByIntermi(wf.getInstCode());
			
			ToSatisfaction toSatisfaction = queryToSatisfactionByCaseCode(caseCode);
			toSatisfaction.setGuohuTime(new Date());
			toSatisfaction.setStatus(SatisfactionStatusEnum.GUOHU_SURVEY_ING.getCode());
			toSatisfaction.setUpdateBy(guohuerId);
			toSatisfaction.setUpdateTime(new Date());
			update(toSatisfaction);
		}else{
			//此处暂不做处理，兼容老案件
			//@TODO 
		}
	}

	@Override
	public void dispatch(String caseCodes, String userId) {
		SessionUser user = uamSessionService.getSessionUser();
	    SessionUser caller = uamSessionService.getSessionUserById(userId);
	    
	    uamSessionService.getSessionUserById(userId);
	    String[] caseCodesArr = caseCodes.split(",");
		for(String caseCode : caseCodesArr){
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
	        StartProcessInstanceVo vo = workFlowManager.startWorkFlow(new ProcessInstance(propertyUtilsService.getSatisProcessDfKey(),
	        		caseCode, variables));
	        //更新满意度表
	        ToSatisfaction toSatisfaction = queryToSatisfactionByCaseCode(caseCode);
	        toSatisfaction.setStatus(SatisfactionStatusEnum.SIGN_SURVEY_ING.getCode());
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
	      }
	}

	@Override
	public void signPass(ToSatisfaction toSatisfaction, String taskId, String instCode) {
		  SessionUser user = uamSessionService.getSessionUser();
		  //1.更新状态
	      toSatisfaction.setStatus(SatisfactionStatusEnum.GUOHU_SURVEY_WAIT.getCode());
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
		List<ToCase> toCases = toCaseService.findAllToCase();
	      for(ToCase toCase : toCases){
	        //签约
	        if(CaseStatusEnum.YQY.getCode().compareTo(toCase.getStatus()) <= 0 ){
	          handleAfterSign(toCase.getCaseCode(), "init");
	        }
	      }
	}

	@Override
	public void bacthPushToGuohu() {
		List<ToCase> toCases = toCaseService.findAllToCase();
	      for(ToCase toCase : toCases){
	    	  pushToGuohu(toCase.getCaseCode());
	      }
	}

	@Override
	public void pushToGuohu(String caseCode) {
		ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
		//过户(要等到分单之后并签约评分完成)
        if(CaseStatusEnum.YGH.getCode().compareTo(toCase.getStatus()) <= 0 ){
          handleAfterGuohuApprove(toCase.getCaseCode(), "push");
        }
	}

}
