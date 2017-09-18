package com.centaline.trans.cases.service.impl;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.cases.service.AuditCaseService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.ToCcaiAttachment;
import com.centaline.trans.common.repository.ToCcaiAttachmentMapper;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.bean.TaskQuery;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.TaskVo;

@Service
public class AuditCaseServiceImpl implements AuditCaseService {
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
	
	@Autowired
	private WorkFlowManager workFlowManager;

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
	public int addLoanProcessor(String userName,String caseCode) {
		if(null==userName||null==caseCode){
			return 0;
		}
		ToCaseParticipant toCaseParticipant = new ToCaseParticipant();
		toCaseParticipant.setCaseCode(caseCode);
		toCaseParticipant.setPosition("loan");
//		先查出user
		User userByUsername = uamUserOrgServiceClient.getUserByUsername(userName);
		if(null==userByUsername){
			return 0;
		}
//		再先原来有没有贷款权证
		List<ToCaseParticipant> listParticipant = toCaseParticipantMapper.selectByCondition(toCaseParticipant);
		if(listParticipant.size()==1){
			ToCaseParticipant toCaseParticipant2 = listParticipant.get(0);
			toCaseParticipant.setCcaiCode(toCaseParticipant2.getCcaiCode());
			Long pkid=toCaseParticipant2.getPkid();
//			先清空，再赋值，避免污染
			if(userByUsername!=null){
				toCaseParticipant.setPkid(toCaseParticipant2.getPkid());
				toCaseParticipant.setUserName(userByUsername.getUsername());
				toCaseParticipant.setRealName(userByUsername.getRealName());
				toCaseParticipant.setMobile(userByUsername.getMobile());
				toCaseParticipant.setGrpName(userByUsername.getOrgName());
				return toCaseParticipantMapper.updateByPrimaryKeySelective(toCaseParticipant);
			}
			
			
		}else{
			toCaseParticipant.setPosition(null);
			List<ToCaseParticipant> selectByCaseCode = toCaseParticipantMapper.selectByCaseCode(caseCode);
			if(selectByCaseCode.size()>0){
				ToCaseParticipant toCaseParticipant2 = selectByCaseCode.get(0);				
				toCaseParticipant.setCcaiCode(toCaseParticipant2.getCcaiCode());
				toCaseParticipant.setUserName(userByUsername.getUsername());
				toCaseParticipant.setRealName(userByUsername.getRealName());
				toCaseParticipant.setMobile(userByUsername.getMobile());
				toCaseParticipant.setGrpName(userByUsername.getOrgName());
				toCaseParticipant.setPosition("loan");
				return toCaseParticipantMapper.insertSelective(toCaseParticipant);
			}else{
				toCaseParticipant.setCcaiCode(caseCode);
				toCaseParticipant.setUserName(userByUsername.getUsername());
				toCaseParticipant.setRealName(userByUsername.getRealName());
				toCaseParticipant.setMobile(userByUsername.getMobile());
				toCaseParticipant.setGrpName(userByUsername.getOrgName());
				toCaseParticipant.setPosition("loan");
			}
		}
		
		
		return 0;
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
	 * @return	
	 * WJD("30001001", "未接单"),
	 * YJD("30001002", "已接单"),
	 */
	@Override
	public int updateAuditCaseSuccess(String caseCode) {
		// TODO Auto-generated method stub
		ToCase toCase = new ToCase();
		toCase.setStatus("30001002");
		toCase.setCaseCode(caseCode);
		int result = toCaseService.updateByCaseCodeSelective(toCase);
		/* 流程引擎相关  生成网签任务*/
		//根据caseCode查出taskVo和ProcessInstanceId,因为设置案件审核的assignee(auditManagerAssignee)需要ProcessInstanceId；
		TaskQuery taskQuery = new TaskQuery();
		taskQuery.setProcessInstanceBusinessKey(caseCode);
		PageableVo listTasks = workFlowManager.listTasks(taskQuery);
		if (listTasks.getData().size() > 0) {
			TaskVo taskVo = (TaskVo) listTasks.getData().get(0);
			List<RestVariable> variables = new ArrayList<RestVariable>();
			RestVariable restVariable = new RestVariable();
			restVariable.setName("warrant");
			ToCaseParticipant toCaseParticipant = new ToCaseParticipant();
			toCaseParticipant.setCaseCode(caseCode);
			toCaseParticipant.setPosition("warrant");
			List<ToCaseParticipant> participantList = toCaseParticipantMapper.selectByCondition(toCaseParticipant);
			if(participantList.size()==1){
				ToCaseParticipant toCaseParticipant2 = participantList.get(0);			
				restVariable.setValue(toCaseParticipant2.getUserName());
			}else{
				return 0;
			}					
			variables.add(restVariable);
			ToCase toCase2 = toCaseService.findToCaseByCaseCode(caseCode);
			Boolean flag = workFlowManager.submitTask(variables, String.valueOf(taskVo.getId()), taskVo.getProcessInstanceId(),
					toCase2.getLeadingProcessId(), caseCode);			
		}
		return result;
	}
	
}
