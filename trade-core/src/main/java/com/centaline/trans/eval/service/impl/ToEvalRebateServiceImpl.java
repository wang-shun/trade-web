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
import com.centaline.trans.common.enums.EvalRebateStatusEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.eval.entity.ToEvalRebate;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.repository.ToEvalRebateMapper;
import com.centaline.trans.eval.service.ToEvalRebateService;
import com.centaline.trans.eval.service.ToEvalReportProcessService;
import com.centaline.trans.message.activemq.vo.MQEvalMessage;
import com.centaline.trans.mgr.service.TsFinOrgService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ToApproveRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

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

	@Autowired
	private JmsTemplate jmsTemplate; //activemq 消息队列


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
			feedBack = new FlowFeedBack(user, CcaiFlowResultEnum.NORMAL_BACK,record.getContent());
		}
		ToEvalReportProcess eval = toEvalReportProcessService.findToEvalReportProcessByCaseCode(rebate.getCaseCode());

		CcaiEvalRebateVo vo = new CcaiEvalRebateVo();
		//评估公司
		vo.setAssessCompany(tsFinOrgService.findBankByFinOrg(eval.getFinOrgId()).getFinOrgName());
		vo.setAssessPrice(eval.getEvaPrice());//评估价
		vo.setAssessReceip(rebate.getEvalRecept());//评估收据
		vo.setCode(rebate.getCaseCode());//案件编号
		vo.setAssessCompanyPrice(rebate.getEvalCost());//评估公司成本
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

	@Override
	public void startRebateFlow(String caseCode, String evalCode) {
		if(StringUtils.isNotBlank(evalCode)){
			//获取CCAI已同步过的评估返利申请信息
			ToEvalRebate evalRebate = findToEvalRebateByCaseCode(caseCode);
			//案件状态必须为未关联评估单才可以启动流程
			if(evalRebate!=null&&EvalRebateStatusEnum.RELATION.getCode().equals(evalRebate.getStatus())){
				evalRebate.setStatus(EvalRebateStatusEnum.DOING.getCode());
				evalRebate.setEvaCode(evalCode);
				updateByPrimaryKeySelective(evalRebate);
				//发送消息 启动流程
				MQEvalMessage message = new MQEvalMessage(evalRebate.getCaseCode(), WorkFlowEnum.EVAL_REBATE_PROCESS.getCode(),MQEvalMessage.STARTFLOW_TYPE);
				//队列名称与trade-api中 EvalFlowWorkListener 中定义一致，需要同步修改
				jmsTemplate.convertAndSend("evalqueue", message);
			}
		}else{
			throw new BusinessException("未获取到评估报告编号!");
		}
	}
}
