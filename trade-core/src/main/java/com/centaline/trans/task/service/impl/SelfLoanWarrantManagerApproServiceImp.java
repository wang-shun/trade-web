package com.centaline.trans.task.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.api.service.FlowApiService;
import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.api.vo.FlowFeedBack;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.common.enums.CaseParticipantEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.CcaiFlowResultEnum;
import com.centaline.trans.common.enums.CcaiTaskEnum;
import com.centaline.trans.common.enums.SelfDoStatusEnum;
import com.centaline.trans.eloan.entity.ToAppRecordInfo;
import com.centaline.trans.eloan.entity.ToSelfAppInfo;
import com.centaline.trans.eloan.repository.ToAppRecordInfoMapper;
import com.centaline.trans.eloan.service.ToSelfAppInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.core.WorkFlowEngine;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.repository.ActRuEventSubScrMapper;
import com.centaline.trans.task.service.SelfLoanWarrantManagerApproService;
import com.centaline.trans.task.service.ToApproveRecordService;
import com.centaline.trans.task.vo.ToAppRecordInfoVO;

import reactor.core.support.Assert;
/**
 * 
 * @author wblujian
 *
 */
@Service
public class SelfLoanWarrantManagerApproServiceImp implements SelfLoanWarrantManagerApproService {

	private static final String SELFDO_LOAN_TYPE = "20"; //自办贷款审批类型
	
	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	
	@Autowired
	private ActRuEventSubScrMapper actRuEventSubScrMapper;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	@Autowired
	private WorkFlowEngine engine;//该处使用engine 否则无法进行访问流程引擎平台
	
	@Autowired
	private FlowApiService flowApiService;
	
	@Autowired
	UamSessionService uamSessionService;
	
	@Autowired
	ToCaseParticipantMapper participantMapper;
	
	@Autowired
	ToCaseMapper toCasemapper;
	
	@Autowired
	private ToAppRecordInfoMapper toAppRecordInfoMapper;
	
	@Autowired
	private ToApproveRecordService toApproveRecordService;
	
	@Autowired
	private ToSelfAppInfoService toSelfAppInfoService;
	
	
	private void insertRcord(ToAppRecordInfoVO vo){
		SessionUser user = uamSessionService.getSessionUser();
		ToApproveRecord record = new ToApproveRecord();
		record.setCaseCode(vo.getCaseCode());
		record.setPartCode(vo.getTaskId());
		record.setApproveType(SELFDO_LOAN_TYPE);
		record.setProcessInstance(vo.getProcessInstanceId());
		record.setContent(vo.getResult()==0?"通过审批,"+vo.getComment():"驳回,"+vo.getComment());
		record.setOperatorTime(new Date());
		//设置处理人
		if(user!=null){
			record.setOperator(user.getId());
		}
		toApproveRecordService.saveToApproveRecord(record);
	}
	
	@Override
	public boolean saveAndSubmit(ToAppRecordInfoVO vo) {
		String type = "自办贷款";
		ToSelfAppInfo  toSelfAppInfo = toSelfAppInfoService.getAppInfoByCaseCode(vo.getCaseCode(), type);
		int count = saveToAppRecordInfoVO(vo);
		if(count <= 0){
			return false;
		}
		List<RestVariable> variables = new ArrayList<RestVariable>();
		RestVariable  restVariable = new RestVariable();
		boolean b = false;
		if(vo.getResult() == 1){ //0通过1驳回
			restVariable.setName("approval");
			restVariable.setValue(false);
			variables.add(restVariable);
			b = workFlowManager.submitTask(variables, vo.getTaskId(), vo.getProcessInstanceId(), null, vo.getCaseCode());
			insertRcord(vo);
		}else{
			restVariable.setName("approval");
			restVariable.setValue(true);
			variables.add(restVariable);
			b = workFlowManager.submitTask(variables, vo.getTaskId(), vo.getProcessInstanceId(), null, vo.getCaseCode());
			insertRcord(vo);
			if(toSelfAppInfo != null){
				toSelfAppInfo.setStatus(SelfDoStatusEnum.SUCCESS.getCode());
				toSelfAppInfoService.updateByPrimaryKeySelective(toSelfAppInfo);
				return b;
			}else{
				return false;
			}
			
		}
		SessionUser user = uamSessionService.getSessionUserById(getManagerId(vo.getCaseCode()));
		FlowFeedBack info = new FlowFeedBack(user, CcaiFlowResultEnum.BACK,"自办贷款审批权证经理驳回");
		ApiResultData result = flowApiService.tradeFeedBackCcai(vo.getCaseCode(), CcaiTaskEnum.MORTGAGE_CUSTOMER_MANAGER, info);
		if(result.isSuccess()){
			if(toSelfAppInfo != null){
				toSelfAppInfo.setStatus(SelfDoStatusEnum.BACK.getCode());
				toSelfAppInfoService.updateByPrimaryKeySelective(toSelfAppInfo);
				return b;
			}else{
				return false;
			}
		}
		return false;
	}

	
	private int saveToAppRecordInfoVO(ToAppRecordInfoVO vo) {
		ToAppRecordInfo toAppRecordInfo = copyProperties(vo);
		List<ToAppRecordInfo> list = new ArrayList<ToAppRecordInfo>();
		list.add(toAppRecordInfo);
		return toAppRecordInfoMapper.insertAppRecordInfo(list);
		
	}


	private ToAppRecordInfo copyProperties(ToAppRecordInfoVO vo) {
		SessionUser user = uamSessionService.getSessionUser();
		ToAppRecordInfo  toAppRecordInfo = new ToAppRecordInfo();
		toAppRecordInfo.setApplyRealName(user.getRealName());
		toAppRecordInfo.setApplyUserName(user.getUsername());
		toAppRecordInfo.setCreateTime(new Date());
		toAppRecordInfo.setDealTime(new Date());
		toAppRecordInfo.setLevel(user.getServiceJobName());
		toAppRecordInfo.setResult(vo.getResult());
		toAppRecordInfo.setSelfAppInfoId(vo.getSelfAppInfoId());
		toAppRecordInfo.setVisitResult(vo.getVisitResult());
		toAppRecordInfo.setVisitTime(vo.getVisitTime());
		toAppRecordInfo.setComment(vo.getComment());
		return toAppRecordInfo;
	}


	/**
	 * 获取对应的权证经理 域账号
	 * @param caseCode
	 * @return
	 */
	private String getManagerId(String caseCode){
		List<ToCaseParticipant> participants = participantMapper.selectByCaseCode(caseCode);
		ToCaseParticipant pa = null;
		for(ToCaseParticipant p :participants){
			System.out.println(p.getPosition()+"---"+p.getUserName()+"----"+p.getRealName()+" manager :"+p.getGrpMgrUsername());
			//优先找贷款权证
			if(CaseParticipantEnum.LOAN.getCode().equals(p.getPosition())){
				pa = p ;
				break;
			}
			//没有贷款权证 找过户权证
			if(pa==null && CaseParticipantEnum.WARRANT.getCode().equals(p.getPosition())){
				pa = p;
			}
		}
		Assert.notNull(pa,"贷款或者过户权证不能都不存在");
		User u = uamUserOrgService.getUserByUsername(pa.getGrpMgrUsername());
		Assert.notNull(u,pa.getGrpMgrUsername()+" 主管信息不存在");
		return u.getId();
	}
}
