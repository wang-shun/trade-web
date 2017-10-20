/**
 * 
 */
package com.centaline.trans.eval.service.impl;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.api.service.EvalApiService;
import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.api.vo.CcaiEvalRebateVo;
import com.centaline.trans.api.vo.FlowFeedBack;
import com.centaline.trans.common.enums.CcaiFlowResultEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.impl.QuickQueryFinOrgImpl;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.eval.entity.ToEval;
import com.centaline.trans.eval.entity.ToEvalRebate;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.repository.ToEvalRebateMapper;
import com.centaline.trans.eval.service.ToEvalRebateService;
import com.centaline.trans.eval.service.ToEvalReportProcessService;
import com.centaline.trans.mgr.service.TsFinOrgService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ToApproveRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ToEvalRebateServiceImpl implements ToEvalRebateService {

	@Autowired
	ToEvalRebateMapper toEvalRebateMapper;//评估返利

	@Autowired
	ToApproveRecordService toApproveRecordService;//审批记录

	@Autowired
	private WorkFlowManager workFlowManager;//activiti

	@Autowired
	private ToWorkFlowService toWorkFlowService;//流程记录

	@Autowired
	private EvalApiService evalApiService;//CCAI交互接口

	@Autowired
	private UamSessionService uamSessionService; //当前登陆用户

	@Autowired
	private ToEvalReportProcessService toEvalReportProcessService;//评估信息

	@Autowired
	private TsFinOrgService tsFinOrgService; //评估公司信息



	@Override
	public int insertSelective(ToEvalRebate record) {
		return toEvalRebateMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(ToEvalRebate record) {
		return toEvalRebateMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public ToEvalRebate findToEvalRebateByCaseCode(String caseCode) {
		return toEvalRebateMapper.findToEvalRebateByCaseCode(caseCode);
	}

	@Override
	public ToEvalRebate selectByPrimaryKey(Long pkid) {
		return toEvalRebateMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public void assistantApprove(ToEvalRebate rebate, ToApproveRecord record,boolean approve) {
		SessionUser user = uamSessionService.getSessionUser();
		//与CCAI交互
		FlowFeedBack feedBack;
		if(approve){
			feedBack = new FlowFeedBack(user, CcaiFlowResultEnum.SUCCESS,record.getContent());
		}else{
			feedBack = new FlowFeedBack(user, CcaiFlowResultEnum.FAILURE,record.getContent());
		}
		ToEvalReportProcess eval = toEvalReportProcessService.findToEvalReportProcessByCaseCode(rebate.getCaseCode());

		CcaiEvalRebateVo vo = new CcaiEvalRebateVo();
		//评估公司
		vo.setAssessCompany(tsFinOrgService.findBankByFinOrg(eval.getFinOrgId()).getFinOrgName());
		vo.setAssessPrice(eval.getEvaPrice());//评估价
		vo.setAssessReceip(rebate.getEvalRecept());//评估收据
		vo.setCode(rebate.getCaseCode());//案件编号
		ApiResultData result = evalApiService.evalRebateFeedBack(feedBack,vo);
		//根据交互成功 修改信息及完成流程变更状态
		if(result.isSuccess()){
			toEvalRebateMapper.updateByPrimaryKeySelective(rebate);
			record.setOperatorTime(new Date());
			record.setOperator(user.getId());
			toApproveRecordService.saveToApproveRecord(record);
			List<RestVariable> variables = new ArrayList<>();
			variables.add(new RestVariable("approve",approve));
			workFlowManager.submitTask(variables,record.getTaskId(),record.getProcessInstance(),record.getOperator(),record.getCaseCode());
			if(approve){
				////更改T_TO_WORKFLOW状态为完结
				ToWorkFlow workFlowOld = new ToWorkFlow();
				workFlowOld.setStatus(WorkFlowStatus.COMPLETE.getCode());
				workFlowOld.setInstCode(record.getProcessInstance());
				toWorkFlowService.updateWorkFlowByInstCode(workFlowOld);
			}
		}else{
			throw new BusinessException(result.getMessage());
		}
	}
}
