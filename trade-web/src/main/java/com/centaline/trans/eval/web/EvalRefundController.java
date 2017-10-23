package com.centaline.trans.eval.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.api.service.FlowApiService;
import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.api.vo.FlowFeedBack;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.vo.ToEvaRefundVO;
import com.centaline.trans.common.enums.CaseParticipantEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.CcaiFlowResultEnum;
import com.centaline.trans.common.enums.CcaiTaskEnum;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.eval.entity.ToEvaRefund;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.service.ToEvaRefundService;
import com.centaline.trans.eval.service.ToEvalReportProcessService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ToApproveRecordService;

import reactor.core.support.Assert;

/**
 * 
 * @author wblujian
 *
 */
@Controller
@RequestMapping(value="/task/evalRefund")
public class EvalRefundController {
	
	@Autowired(required = true)
	private ToCaseService toCaseService;
	
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	
	@Autowired
	private ToEvaRefundService toEvaRefundService;
	
	@Autowired
	private ToEvalReportProcessService toEvalReportProcessService;
	
	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private ToApproveRecordService toApproveRecordService;
	
	@Autowired
	private UamSessionService uamSessionService;
		
	@Autowired
	ToCaseParticipantMapper participantMapper;
	
	@Autowired
	private FlowApiService flowApiService;
	
	@Autowired
	ToCaseMapper toCasemapper;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	/**
	 * 内勤审核
	 */
	@RequestMapping(value="assistant")
	public String assistant(HttpServletRequest request, HttpServletResponse response,
			String caseCode, String source, String taskitem, String processInstanceId){
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);	
		toAccesoryListService.getAccesoryList(request, taskitem);
		ToEvaRefund  toEvaRefund = toEvaRefundService.selectByCaseCode(caseCode);
		ToEvalReportProcess  toEvaReport = toEvalReportProcessService.findToEvalReportProcessByCaseCode(caseCode);
		request.setAttribute("toEvaRefund", toEvaRefund);
		request.setAttribute("toEvaReport", toEvaReport);
		return "eval/evaRefundAss";
	}
	
	@RequestMapping(value="save")
	@ResponseBody
	public boolean save(ToEvaRefundVO toEvaRefundvo){
		ToEvaRefund  toEvaRefund = new ToEvaRefund();
		boolean flag = false;
		try {
			toEvaRefund = copyProperties(toEvaRefundvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int count = toEvaRefundService.updateByPrimaryKeySelective(toEvaRefund);
		if(count > 0){
			flag = true;
		}
		return flag;
	}

	private ToEvaRefund copyProperties(ToEvaRefundVO toEvaRefundvo) {
		ToEvaRefund toEvaRefund = new ToEvaRefund();
		toEvaRefund.setPkid(toEvaRefundvo.getPkid());
		toEvaRefund.setEvalRealCharges(toEvaRefundvo.getEvalRealCharges());
		toEvaRefund.setIsNeedRecovery(toEvaRefundvo.getIsNeedRecovery());
		if (toEvaRefundvo.getIsNeedRecovery().equals("0")) {
			toEvaRefund.setReportBackNum(null);
			toEvaRefund.setBackTime(null);
			toEvaRefund.setBackCause(null);
		}else{
			toEvaRefund.setReportBackNum(toEvaRefundvo.getReportBackNum());
			toEvaRefund.setBackTime(toEvaRefundvo.getBackTime());
			toEvaRefund.setBackCause(toEvaRefundvo.getBackCause());
		}
		return toEvaRefund;
	}
	


	@RequestMapping(value="submit")
	@ResponseBody
	public boolean submit(ToEvaRefundVO toEvaRefundvo){
		ToEvaRefund  toEvaRefund = new ToEvaRefund();
		try {
			toEvaRefund = copyProperties(toEvaRefundvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int count = toEvaRefundService.updateByPrimaryKeySelective(toEvaRefund);
		if(count <= 0){
			return false;
		}
		List<RestVariable> variables = new ArrayList<RestVariable>();
		RestVariable  restVariable = new RestVariable();
		variables.add(restVariable);
		boolean b = workFlowManager.submitTask(null, toEvaRefundvo.getTaskId(), toEvaRefundvo.getProcessInstanceId(), null, toEvaRefundvo.getCaseCode());
		return b;
	}
	
	/*private void saveToApproveRecord(ToEvaRefundVO toEvaRefundvo) {
		SessionUser user = uamSessionService.getSessionUser();
		ToApproveRecord toApproveRecord = new ToApproveRecord();
		toApproveRecord.setProcessInstance(toEvaRefundvo.getProcessInstanceId());
		toApproveRecord.setPartCode(toEvaRefundvo.getPartCode());
		toApproveRecord.setOperatorTime(new Date());
		toApproveRecord.setCaseCode(toEvaRefundvo.getCaseCode());
		toApproveRecord.setContent("审批通过");
		toApproveRecord.setOperator(user.getId());
		toApproveRecord.setTaskId(toEvaRefundvo.getTaskId());
		toApproveRecordService.saveToApproveRecord(toApproveRecord);
	}*/
	/**
	 * 退费经理审核
	 * @param request
	 * @param response
	 * @param caseCode
	 * @param source
	 * @param taskitem
	 * @param processInstanceId
	 * @return
	 */
	@RequestMapping(value="manager")
	public String manager(HttpServletRequest request, HttpServletResponse response,
			String caseCode, String source, String taskitem, String processInstanceId){
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);	
		toAccesoryListService.getAccesoryList(request, taskitem);
		ToEvaRefund  toEvaRefund = toEvaRefundService.selectByCaseCode(caseCode);
		ToEvalReportProcess  toEvaReport = toEvalReportProcessService.findToEvalReportProcessByCaseCode(caseCode);
		request.setAttribute("toEvaRefund", toEvaRefund);
		request.setAttribute("toEvaReport", toEvaReport);
		return "eval/evaRefundMan";
	};
	
	@RequestMapping(value="submitManager")
	@ResponseBody
	public boolean submitManager(ToEvaRefundVO toEvaRefundvo,String approveResult,String approveContent){
		saveRcord(toEvaRefundvo,approveResult,approveContent);
		List<RestVariable> variables = new ArrayList<RestVariable>();
		RestVariable  restVariable = new RestVariable();
		boolean b = false;
		if(approveResult.equals("0")){
			restVariable.setName("EvalRefundFirstCheck");
			restVariable.setValue(true);
			variables.add(restVariable);
		    b = workFlowManager.submitTask(variables, toEvaRefundvo.getTaskId(), toEvaRefundvo.getProcessInstanceId(), null, toEvaRefundvo.getCaseCode());
		    return b;
		}else{
			restVariable.setName("EvalRefundFirstCheck");
			restVariable.setValue(false);
			variables.add(restVariable);
			b = workFlowManager.submitTask(variables, toEvaRefundvo.getTaskId(), toEvaRefundvo.getProcessInstanceId(), null, toEvaRefundvo.getCaseCode());
		}
		
		SessionUser user = uamSessionService.getSessionUserById(getManagerId(toEvaRefundvo.getCaseCode()));
		FlowFeedBack info = new FlowFeedBack(user, CcaiFlowResultEnum.BACK,"权证经理驳回");
		ApiResultData result = flowApiService.tradeFeedBackCcai(toEvaRefundvo.getCaseCode(), CcaiTaskEnum.EVAL_REFUND_MANAGER, info);
		if(result.isSuccess()){
			//修改案件状态为驳回CCAI
			ToCase ca  = toCasemapper.findToCaseByCaseCode(toEvaRefundvo.getCaseCode());
			ca.setStartDate(CaseStatusEnum.BHCCAI.getCode());
			toCasemapper.updateByCaseCodeSelective(ca);
			return b;
		}
		return false;
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
	
	private void saveRcord(ToEvaRefundVO toEvaRefundvo,String approveResult,String approveContent){
		ToApproveRecord toApproveRecord  = new ToApproveRecord();
		SessionUser user = uamSessionService.getSessionUser();
		toApproveRecord.setProcessInstance(toEvaRefundvo.getProcessInstanceId());
		toApproveRecord.setPartCode(toEvaRefundvo.getPartCode());
		toApproveRecord.setOperatorTime(new Date());
		toApproveRecord.setCaseCode(toEvaRefundvo.getCaseCode());
		toApproveRecord.setOperator(user.getId());
		toApproveRecord.setApproveType("17");
		//System.out.println("测试是否编译");
		boolean b = approveResult.equals("0");
		boolean c = approveContent == null || approveContent.length() == 0;
		toApproveRecord.setContent((b?"通过":"不通过") + (c?",没有审批意见。":",审批意见为"+approveContent));
		toApproveRecordService.insertToApproveRecord(toApproveRecord);
	}
	/**
	 * 退费权证总监审批
	 * @return
	 */
	@RequestMapping(value="chief")
	public String chief(HttpServletRequest request, HttpServletResponse response,
			String caseCode, String source, String taskitem, String processInstanceId){
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);	
		toAccesoryListService.getAccesoryList(request, taskitem);
		ToEvaRefund  toEvaRefund = toEvaRefundService.selectByCaseCode(caseCode);
		ToEvalReportProcess  toEvaReport = toEvalReportProcessService.findToEvalReportProcessByCaseCode(caseCode);
		request.setAttribute("toEvaRefund", toEvaRefund);
		request.setAttribute("toEvaReport", toEvaReport);
		return "eval/evaRefundChi";
	}
	
	@RequestMapping(value="submitChif")
	@ResponseBody
	public boolean submitChif(ToEvaRefundVO toEvaRefundvo,String approveResult,String approveContent){
		saveRcord(toEvaRefundvo,approveResult,approveContent);
		List<RestVariable> variables = new ArrayList<RestVariable>();
		RestVariable  restVariable = new RestVariable();
		boolean b = false;
	
	    b = workFlowManager.submitTask(null, toEvaRefundvo.getTaskId(), toEvaRefundvo.getProcessInstanceId(), null, toEvaRefundvo.getCaseCode());	
		SessionUser user = uamSessionService.getSessionUserById(getManagerId(toEvaRefundvo.getCaseCode()));
		FlowFeedBack info = null;
		if(approveResult.equals("1")){
			 info = new FlowFeedBack(user, CcaiFlowResultEnum.FAILURE,"审批不通过");
		}else{
			 info = new FlowFeedBack(user, CcaiFlowResultEnum.SUCCESS,"审批通过");
		}
		ApiResultData result = flowApiService.tradeFeedBackCcai(toEvaRefundvo.getCaseCode(), CcaiTaskEnum.EVAL_REFUND_MAJORDOMO, info);
		if(result.isSuccess()){
			//修改案件状态为驳回CCAI
			ToCase ca  = toCasemapper.findToCaseByCaseCode(toEvaRefundvo.getCaseCode());
			ca.setStartDate(CaseStatusEnum.YZZ.getCode());
			toCasemapper.updateByCaseCodeSelective(ca);
			return b;
		}
		return false;
	}

}
