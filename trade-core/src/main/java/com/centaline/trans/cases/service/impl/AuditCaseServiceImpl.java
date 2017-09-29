package com.centaline.trans.cases.service.impl;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.api.service.FlowApiService;
import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.api.vo.FlowFeedBack;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.cases.service.AuditCaseService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.ToCcaiAttachment;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.CcaiFlowResultEnum;
import com.centaline.trans.common.enums.CcaiTaskEnum;
import com.centaline.trans.common.repository.ToCcaiAttachmentMapper;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.bean.TaskQuery;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.TaskVo;

@Service
public class AuditCaseServiceImpl implements AuditCaseService {
	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private ToCaseService toCaseService;
	@Autowired
	private ToCcaiAttachmentMapper toCcaiAttachmentMapper;
	
	@Autowired
	private ToCaseInfoMapper toCaseInfoMapper;
	
	@Autowired
	private ToCaseParticipantMapper toCaseParticipantMapper;
	
	@Autowired
	private UamUserOrgService uamUserOrgServiceClient;
	//CCAI交互服务
	@Autowired
	private FlowApiService flowApiService;

	@Override
	public String getPayType(String caseCode) {
		// TODO Auto-generated method stub
		ToCaseInfo caseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
		return caseInfo.getPayType();
	}

	@Override
	public List<ToCcaiAttachment> getCcaiAttachment(String caseCode) {
		return toCcaiAttachmentMapper.findAttachmentsByCaseCode(caseCode);
	}

	@Override
	public int addLoanProcessor(String userName, String caseCode) {
		SessionUser user = uamSessionService.getSessionUser();
		FlowFeedBack info = new FlowFeedBack(user, CcaiFlowResultEnum.SUCCESS, user.getRealName());
		// 先通知CCAI 返回结果为true再更新案件状态
		ApiResultData apiResult = flowApiService.tradeFeedBackCcai(caseCode, CcaiTaskEnum.TRADE_WARRANT_MANAGER, info);
		if (apiResult.isSuccess()) {
			/* 流程引擎相关 生成网签任务 */
			// 根据caseCode查出taskVo和ProcessInstanceId,因为设置案件审核的assignee(auditManagerAssignee)需要ProcessInstanceId；
			TaskQuery taskQuery = new TaskQuery();
			taskQuery.setProcessInstanceBusinessKey(caseCode);
			taskQuery.setTaskDefinitionKey("warrantManagerAudit");
			taskQuery.setAssignee(user.getUsername());
			PageableVo listTasks = workFlowManager.listTasks(taskQuery);
			if (listTasks.getData().size() > 0) {
				TaskVo taskVo = (TaskVo) listTasks.getData().get(0);
				List<RestVariable> variables = new ArrayList<RestVariable>();
				// 把审核通过添加到流程变量
				RestVariable caseApprove = new RestVariable();
				caseApprove.setName("caseApprove");
				caseApprove.setValue(true);
				variables.add(caseApprove);
				ToCase toCase2 = toCaseService.findToCaseByCaseCode(caseCode);
				if (workFlowManager.submitTask(variables, String.valueOf(taskVo.getId()), taskVo.getProcessInstanceId(),
						toCase2.getLeadingProcessId(), caseCode)) {
					// 再更改案件状为已接单
					ToCase toCase = new ToCase();
					toCase.setStatus(CaseStatusEnum.YJD.getCode());
					toCase.setCaseCode(caseCode);
					int updateCaseStatus = toCaseService.updateByCaseCodeSelective(toCase);

					// 先查出user
					User userByUsername = uamUserOrgServiceClient.getUserByUsername(userName);
					if (null == userByUsername) {
						throw new BusinessException("货款权证无效！");
					}

					// 再看原来有没有贷款权证
					ToCaseParticipant toCaseParticipant = new ToCaseParticipant();
					toCaseParticipant.setCaseCode(caseCode);
					toCaseParticipant.setPosition("loan");
					List<ToCaseParticipant> listParticipant = toCaseParticipantMapper
							.selectByCondition(toCaseParticipant);
					// 如果有就修改
					if (listParticipant.size() == 1) {
						ToCaseParticipant toCaseParticipant2 = listParticipant.get(0);
						toCaseParticipant.setCcaiCode(toCaseParticipant2.getCcaiCode());
						Long pkid = toCaseParticipant2.getPkid();
						// 先清空，再赋值，避免污染
						if (userByUsername != null) {
							toCaseParticipant.setPkid(toCaseParticipant2.getPkid());
							toCaseParticipant.setUserName(userByUsername.getUsername());
							toCaseParticipant.setRealName(userByUsername.getRealName());
							toCaseParticipant.setMobile(userByUsername.getMobile());
							toCaseParticipant.setGrpName(userByUsername.getOrgName());
							return toCaseParticipantMapper.updateByPrimaryKeySelective(toCaseParticipant);
						}
					} else {
						// 如果没有就插入
						toCaseParticipant.setPosition(null);
						// ToCaseParticipant这张表的ccaiCode不能为空，先从里面查出来
						List<ToCaseParticipant> selectByCaseCode = toCaseParticipantMapper.selectByCaseCode(caseCode);
						if (selectByCaseCode.size() > 0) {
							ToCaseParticipant toCaseParticipant2 = selectByCaseCode.get(0);
							toCaseParticipant.setCcaiCode(toCaseParticipant2.getCcaiCode());
							toCaseParticipant.setUserName(userByUsername.getUsername());
							toCaseParticipant.setRealName(userByUsername.getRealName());
							toCaseParticipant.setMobile(userByUsername.getMobile());
							toCaseParticipant.setGrpName(userByUsername.getOrgName());
							toCaseParticipant.setPosition("loan");
							return toCaseParticipantMapper.insertSelective(toCaseParticipant);
						}
					}
				}
			}
			throw new BusinessException("更改贷款权证出错！");
		}
		throw new BusinessException(apiResult.getMessage());
	}
	/**
	 * 根据当前贷款或者过户权证账号和caseCode查出贷款或者过户权证的上级账号
	 */
	@Override
	public String getLeaderUserName(ToCaseParticipant toCaseParticipant) {
		// TODO Auto-generated method stub
		if(null!=toCaseParticipant.getCaseCode()&&null!=toCaseParticipant.getUserName()){			
		List<ToCaseParticipant> userList = toCaseParticipantMapper.selectByCondition(toCaseParticipant);
		if(userList.size()==1){
			ToCaseParticipant toCaseParticipant2 = userList.get(0);
			return toCaseParticipant2.getGrpMgrUsername();
		}					
		}
		return null;
	}

	/**
	 * 
	 * @since:2017年9月18日 下午1:58:30
	 * @description:接单审核通过，更新接单状态为已接单，在接单界面消除本条案件；生成网签任务；
	 * @author:xiefei1
	 */
	@Override
	public void updateAuditCaseSuccess(String caseCode) {
		// TODO Auto-generated method stub
		SessionUser user = uamSessionService.getSessionUser();
		FlowFeedBack info = new FlowFeedBack(user, CcaiFlowResultEnum.SUCCESS,user.getRealName());
		//先通知CCAI 返回结果为true再更新案件状态
		ApiResultData apiResult = flowApiService.tradeFeedBackCcai(caseCode, CcaiTaskEnum.TRADE_WARRANT_MANAGER,info);
//		测试错误捕获
		if(apiResult.isSuccess()){
			ToCase toCase = new ToCase();
			toCase.setStatus(CaseStatusEnum.YJD.getCode());
			toCase.setCaseCode(caseCode);
			toCaseService.updateByCaseCodeSelective(toCase);
			/* 流程引擎相关  生成网签任务*/
			//根据caseCode查出taskVo和ProcessInstanceId,因为设置案件审核的assignee(auditManagerAssignee)需要ProcessInstanceId；
			TaskQuery taskQuery = new TaskQuery();
			taskQuery.setProcessInstanceBusinessKey(caseCode);
			taskQuery.setTaskDefinitionKey("warrantManagerAudit");
			taskQuery.setAssignee(user.getUsername());
			PageableVo listTasks = workFlowManager.listTasks(taskQuery);
			if (listTasks.getData().size() > 0) {
				TaskVo taskVo = (TaskVo) listTasks.getData().get(0);
				List<RestVariable> variables = new ArrayList<RestVariable>();
//				把审核通过添加到流程变量
				RestVariable caseApprove = new RestVariable();
				caseApprove.setName("caseApprove");
				caseApprove.setValue(true);
				variables.add(caseApprove);											
				ToCase toCase2 = toCaseService.findToCaseByCaseCode(caseCode);
				if(!workFlowManager.submitTask(variables, String.valueOf(taskVo.getId()), taskVo.getProcessInstanceId(),
						toCase2.getLeadingProcessId(), caseCode)
						){
					throw new BusinessException("提交任务出错！");					
				}
			}			
		}else{
			throw new BusinessException(apiResult.getMessage());
		}				
	}
	/**
	 * 
	 * @since:2017年9月26日 下午3:58:30
	 * @description:接单审核通过后 修改状态，返回接单列表页面；
	 * @author:xiefei1
	 * @return	
	 * WJD("30001001", "未接单"),
	 * YJD("30001002", "已接单"),
	 * 
	 */
	@Override
	public int returnCaseToCCAI(String caseCode,String returnReason,String returnComment) {
		// TODO Auto-generated method stub
		SessionUser user = uamSessionService.getSessionUser();
		//先通知CCAI 返回结果为true再更新案件状态
//		<option value="1">权证专员错误</option>
//		<option value="2">附件错误</option>
		FlowFeedBack info;
		if(returnReason.contains("2")){
			 info = new FlowFeedBack(user, CcaiFlowResultEnum.SUPPLEMENT,returnComment);			
		}else{
			 info = new FlowFeedBack(user, CcaiFlowResultEnum.BACK,returnComment);
		}
		ApiResultData apiResult = flowApiService.tradeFeedBackCcai(caseCode, CcaiTaskEnum.TRADE_WARRANT_MANAGER,info);
		if(apiResult.isSuccess()){
			ToCase toCase = new ToCase();
			toCase.setStatus(CaseStatusEnum.BHCCAI.getCode());
			toCase.setCaseCode(caseCode);
			int result = toCaseService.updateByCaseCodeSelective(toCase);
			/* 流程引擎相关  生成网签任务*/
			//根据caseCode查出taskVo和ProcessInstanceId,因为设置案件审核的assignee(auditManagerAssignee)需要ProcessInstanceId；
			TaskQuery taskQuery = new TaskQuery();
			taskQuery.setProcessInstanceBusinessKey(caseCode);
			taskQuery.setTaskDefinitionKey("warrantManagerAudit");
			PageableVo listTasks = workFlowManager.listTasks(taskQuery);
			if (listTasks.getData().size() > 0) {
				TaskVo taskVo = (TaskVo) listTasks.getData().get(0);
				List<RestVariable> variables = new ArrayList<RestVariable>();
//				把审核通过添加到流程变量
				RestVariable caseApprove = new RestVariable();
				caseApprove.setName("caseApprove");
				caseApprove.setValue(false);
				variables.add(caseApprove);				
				ToCase toCase2 = toCaseService.findToCaseByCaseCode(caseCode);
				if(workFlowManager.submitTask(variables, String.valueOf(taskVo.getId()), taskVo.getProcessInstanceId(),
						toCase2.getLeadingProcessId(), caseCode)
						){
					return 1;
				}else{
					throw new BusinessException("提交任务出错！");
				}
			}
			return result;
			//调用流程引擎 设置网关判断参数 完成环节 by:yinchao 2017-9-26
			// List<RestVariable> variables = new ArrayList<>();
			// variables.add(new RestVariable("caseApprove",true));
			// //驳回使用下面的设置参数
			// variables.add(new RestVariable("caseApprove",false));
		}else{
			throw new BusinessException(apiResult.getMessage());
		}
	}	
}
