package com.centaline.trans.eval.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.support.Assert;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.api.service.FlowApiService;
import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.api.vo.FlowFeedBack;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.cases.vo.ToEvaRefundVO;
import com.centaline.trans.common.enums.CaseParticipantEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.CcaiFlowResultEnum;
import com.centaline.trans.common.enums.CcaiTaskEnum;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.eval.entity.ToEvaRefund;
import com.centaline.trans.eval.repository.ToEvaRefundMapper;
import com.centaline.trans.eval.service.ToEvaRefundService;
@Service
public class ToEvaRefundServiceImpl implements ToEvaRefundService{

	@Autowired
	private ToEvaRefundMapper toEvaRefundMapper;
	
	@Autowired
	private ToCaseInfoMapper toCaseInfoMapper;
	
	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired
	private FlowApiService flowApiService;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	@Autowired
	ToCaseParticipantMapper participantMapper;
	
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toEvaRefundMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int insert(ToEvaRefund record) {
		// TODO Auto-generated method stub
		return toEvaRefundMapper.insert(record);
	}

	@Override
	public int insertSelective(ToEvaRefund record) { 
		int count = toEvaRefundMapper.insertSelective(record);
		return count;
	}

	@Override
	public ToEvaRefund selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		ToEvaRefund toEvaRefund = toEvaRefundMapper.selectByPrimaryKey(pkid);
		return toEvaRefund;
	}

	@Override
	public ToEvaRefund selectByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		ToEvaRefund toEvaRefund = toEvaRefundMapper.selectByCaseCode(caseCode);
		return toEvaRefund;
	}

	@Override
	public int updateByPrimaryKeySelective(ToEvaRefund record) {
		// TODO Auto-generated method stub
		return toEvaRefundMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ToEvaRefund record) {
		// TODO Auto-generated method stub
		return toEvaRefundMapper.updateByPrimaryKey(record);
	}

	/**
	 * 权证经理提交
	 */
	@Override
	public boolean submitManager(ToEvaRefundVO toEvaRefundvo,
			String approveResult) {
		List<RestVariable> variables = new ArrayList<RestVariable>();
		RestVariable  restVariable = new RestVariable();
		boolean b = false;
		FlowFeedBack info = null;
		if(approveResult.equals("0")){
			restVariable.setName("EvalRefundFirstCheck");
			restVariable.setValue(true);
			variables.add(restVariable);
		    b = workFlowManager.submitTask(variables, toEvaRefundvo.getTaskId(), toEvaRefundvo.getProcessInstanceId(), null, toEvaRefundvo.getCaseCode());
		    SessionUser user = uamSessionService.getSessionUser();
			info = new FlowFeedBack(user, CcaiFlowResultEnum.SUCCESS,"权证经理审核通过！");
		}else{
			restVariable.setName("EvalRefundFirstCheck");
			restVariable.setValue(false);
			variables.add(restVariable);
			b = workFlowManager.submitTask(variables, toEvaRefundvo.getTaskId(), toEvaRefundvo.getProcessInstanceId(), null, toEvaRefundvo.getCaseCode());
			SessionUser user = uamSessionService.getSessionUser();
			info = new FlowFeedBack(user, CcaiFlowResultEnum.FAILURE,"权证经理审核拒绝！");
		}
		ApiResultData result = flowApiService.tradeFeedBackCcai(toEvaRefundvo.getCaseCode(), CcaiTaskEnum.EVAL_REFUND_MANAGER, info);
		if(result.isSuccess()){
			return b;
		}
		return false;
	}
	

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

	/**
	 * 内勤提交
	 */
	@Override
	public boolean submitAssistant(ToEvaRefundVO toEvaRefundvo) {
		ToEvaRefund  toEvaRefund = new ToEvaRefund();
		try {
			toEvaRefund = copyProperties(toEvaRefundvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int count = updateByPrimaryKeySelective(toEvaRefund);
		if(count <= 0){
			return false;
		}
		List<RestVariable> variables = new ArrayList<RestVariable>();
		RestVariable  restVariable = new RestVariable();
		variables.add(restVariable);
		boolean b = workFlowManager.submitTask(null, toEvaRefundvo.getTaskId(), toEvaRefundvo.getProcessInstanceId(), null, toEvaRefundvo.getCaseCode());
		return b;
	}
	
	private ToEvaRefund copyProperties(ToEvaRefundVO toEvaRefundvo) {
		ToEvaRefund toEvaRefund = new ToEvaRefund();
		toEvaRefund.setPkid(toEvaRefundvo.getPkid());
		toEvaRefund.setEvalRealCharges(toEvaRefundvo.getEvalRealCharges());
		toEvaRefund.setIsNeedRecovery(toEvaRefundvo.getIsNeedRecovery());
		if (toEvaRefundvo.getIsNeedRecovery().contains("0")) {
			toEvaRefund.setReportBackNum(null);
			toEvaRefund.setBackTime(null);
			toEvaRefund.setBackCause(null);
			toEvaRefund.setIsNeedRecovery("0");
		}else{
			toEvaRefund.setReportBackNum(toEvaRefundvo.getReportBackNum());
			toEvaRefund.setBackTime(toEvaRefundvo.getBackTime());
			toEvaRefund.setBackCause(toEvaRefundvo.getBackCause());
			toEvaRefund.setIsNeedRecovery("1");
		}
		return toEvaRefund;
	}

	@Override
	public boolean submitChif(ToEvaRefundVO toEvaRefundvo, String approveResult) {
		boolean b = false;
	    b = workFlowManager.submitTask(null, toEvaRefundvo.getTaskId(), toEvaRefundvo.getProcessInstanceId(), null, toEvaRefundvo.getCaseCode());	
		SessionUser user = uamSessionService.getSessionUserById(getManagerId(toEvaRefundvo.getCaseCode()));
		FlowFeedBack info = null;
		if(approveResult.equals("1")){
			 info = new FlowFeedBack(user, CcaiFlowResultEnum.FAILURE,"权证经理审批不通过");
		}else{
			 info = new FlowFeedBack(user, CcaiFlowResultEnum.SUCCESS,"权证经理审批通过");
		}
		ApiResultData result = flowApiService.tradeFeedBackCcai(toEvaRefundvo.getCaseCode(), CcaiTaskEnum.EVAL_REFUND_MAJORDOMO, info);
		if(result.isSuccess()){
			return b;
		}
		return false;
	}
}
