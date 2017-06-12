package com.centaline.trans.task.service.impl;

import java.util.*;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.service.AwardBaseService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.repository.TsFinOrgMapper;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.repository.ToMortgageMapper;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.repository.ToApproveRecordMapper;
import com.centaline.trans.task.service.LoanlostApproveService;

@Service
public class LoanlostApproveServiceImpl implements LoanlostApproveService {
	
	@Autowired
	private ToApproveRecordMapper toApproveRecordMapper;
	@Autowired
	private ToMortgageMapper toMortgageMapper;
	@Autowired
	private ToPropertyInfoMapper toPropertyInfoMapper;
	@Autowired
	private TsFinOrgMapper tsFinOrgMapper;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private LoanlostApproveService loanlostApproveService;
	@Autowired
	private AwardBaseService awardBaseService;
	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Override
	public Boolean saveLoanlostApprove(ToApproveRecord toApproveRecord) {
		if(StringUtils.isBlank(toApproveRecord.getCaseCode())) {
			return false;
		}
		if(toApproveRecord.getPkid() != null) {
			toApproveRecordMapper.updateByPrimaryKeySelective(toApproveRecord);
		} else {
			toApproveRecordMapper.insertSelective(toApproveRecord);
		}
		return true;
	}

	@Override
	public List<String> findApproveRecordByList(ToApproveRecord toApproveRecord) {
		return toApproveRecordMapper.findApproveRecordByList(toApproveRecord);
	}
	
	@Override
	public ToApproveRecord findLastApproveRecord(ToApproveRecord toApproveRecord){
		return toApproveRecordMapper.findLastApproveRecord(toApproveRecord);
	}

	@Override
	public Map<String, Object> queryCaseInfo(String caseCode, String partCode,String instCode) {
		ToMortgage toMortgage = toMortgageMapper.findToMortgageByCaseCode(caseCode);
		ToPropertyInfo toPropertyInfo = toPropertyInfoMapper.findToPropertyInfoByCaseCode(caseCode);
		Map<String, Object> caseDetail = new HashMap<String, Object>();
		caseDetail.put("caseCode", caseCode);
		if(toMortgage != null) {
			TsFinOrg tsFinOrg = tsFinOrgMapper.findBankByFinOrg(toMortgage.getLastLoanBank());
			if(tsFinOrg != null)
			caseDetail.put("lastLoanBank", tsFinOrg.getFinOrgName());
			caseDetail.put("mortTotalAmount", toMortgage.getMortTotalAmount());
			caseDetail.put("content", toMortgage.getSelfDelReason());
			caseDetail.put("loanLostApplyReason", toMortgage.getLoanLostApplyReason());
		} 
		if(toPropertyInfo != null) {
			caseDetail.put("propertyAddr", toPropertyInfo.getPropertyAddr());
		}
		
		return caseDetail;
	}

	@Override
	public AjaxResponse saveAndSubmitLoanlostApproveFirst(ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO, String caseClose, String caseClose_response) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		ToApproveRecord toApproveRecord = new ToApproveRecord();
		toApproveRecord.setProcessInstance(processInstanceVO.getProcessInstanceId());
		toApproveRecord.setPartCode(processInstanceVO.getPartCode());
		toApproveRecord.setOperatorTime(new Date());
		toApproveRecord.setApproveType(loanlostApproveVO.getApproveType());
		toApproveRecord.setCaseCode(processInstanceVO.getCaseCode());
		boolean b = caseClose.equals("true");
		toApproveRecord.setContent((b?"通过":"不通过") + (",审批意见为"+caseClose_response));
		toApproveRecord.setOperator(loanlostApproveVO.getOperator());
		loanlostApproveService.saveLoanlostApprove(toApproveRecord);

		RestVariable restVariable = new RestVariable();
		List<RestVariable> variables = new ArrayList<RestVariable>();
		restVariable.setName("CaseCloseFirstCheck");
		restVariable.setValue(b);
		variables.add(restVariable);
		if(!StringUtils.isBlank(caseClose_response)) {
			RestVariable restVariable1 = new RestVariable();
			restVariable1.setName("CaseCloseFirstCheck_response");
			restVariable1.setValue(caseClose_response);
			variables.add(restVariable1);
		}
		ToCase toCase = toCaseService.findToCaseByCaseCode(processInstanceVO.getCaseCode());
		workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), toCase.getLeadingProcessId(), processInstanceVO.getCaseCode());
		ajaxResponse.setSuccess(true);
		return ajaxResponse;
	}

	@Override
	public AjaxResponse saveAndSubmitLoanlostApproveSecond(ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO, String caseCloseSecondCheck, String caseCloseSecondCheck_response) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		ToApproveRecord toApproveRecord = new ToApproveRecord();
		toApproveRecord.setProcessInstance(processInstanceVO.getProcessInstanceId());
		toApproveRecord.setPartCode(processInstanceVO.getPartCode());
		toApproveRecord.setOperatorTime(new Date());
		toApproveRecord.setApproveType(loanlostApproveVO.getApproveType());
		toApproveRecord.setCaseCode(processInstanceVO.getCaseCode());
		boolean b = caseCloseSecondCheck.equals("true");
		toApproveRecord.setContent((b?"通过":"不通过") + (",审批意见为"+caseCloseSecondCheck_response));
		toApproveRecord.setOperator(loanlostApproveVO.getOperator());
		loanlostApproveService.saveLoanlostApprove(toApproveRecord);

	/*流程引擎相关*/
		RestVariable restVariable = new RestVariable();
		List<RestVariable> variables = new ArrayList<RestVariable>();
		restVariable.setName("CaseCloseSecondCheck");
		restVariable.setValue(caseCloseSecondCheck.equals("true"));
		variables.add(restVariable);
		if(!StringUtil.isBlank(caseCloseSecondCheck_response)) {
			RestVariable restVariable1 = new RestVariable();
			restVariable1.setName("CaseCloseSecondCheck_response");
			restVariable1.setValue(caseCloseSecondCheck_response);
			variables.add(restVariable1);
		}


		ToCase toCase = toCaseService.findToCaseByCaseCode(processInstanceVO.getCaseCode());
		workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(),toCase.getLeadingProcessId(), processInstanceVO.getCaseCode());
		/*审批通过，完结交易单*/
		if(caseCloseSecondCheck.equals("true")) {
			toCase.setCaseProperty("30003002");
			toCase.setCloseTime(new Date());
			toCaseService.updateByCaseCodeSelective(toCase);
			/*审批通过才能流程才会结束*/
			ToWorkFlow t = new ToWorkFlow();
			t.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
			t.setCaseCode(processInstanceVO.getCaseCode());
			ToWorkFlow mainflow = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(t);
			mainflow.setStatus(WorkFlowStatus.COMPLETE.getCode());
			toWorkFlowService.updateByPrimaryKeySelective(mainflow);
			awardBaseService.setAwradCaseCloseDate(processInstanceVO.getCaseCode(), new Date());//设置基本奖金的结案时间
		}
		ajaxResponse.setSuccess(true);
		ajaxResponse.setMessage("审核成功");
		return ajaxResponse;
	}

	@Override
	public AjaxResponse saveAndSubmitLoanlostApproveThird(ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO, String caseCloseThirdCheck, String caseCloseThirdCheck_response) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		ToApproveRecord toApproveRecord = new ToApproveRecord();
		toApproveRecord.setProcessInstance(processInstanceVO.getProcessInstanceId());
		toApproveRecord.setPartCode(processInstanceVO.getPartCode());
		toApproveRecord.setOperatorTime(new Date());
		toApproveRecord.setApproveType(loanlostApproveVO.getApproveType());
		toApproveRecord.setCaseCode(processInstanceVO.getCaseCode());
		boolean b = caseCloseThirdCheck.equals("true");
		toApproveRecord.setContent((b?"通过":"不通过") + (",审批意见为"+caseCloseThirdCheck_response));
		toApproveRecord.setOperator(loanlostApproveVO.getOperator());
		loanlostApproveService.saveLoanlostApprove(toApproveRecord);


		/*流程引擎相关*/
		RestVariable restVariable = new RestVariable();
		List<RestVariable> variables = new ArrayList<RestVariable>();
		restVariable.setName("CaseCloseThirdCheck");
		restVariable.setValue(caseCloseThirdCheck.equals("true"));
		variables.add(restVariable);
		if(!StringUtil.isBlank(caseCloseThirdCheck_response)) {
			RestVariable restVariable1 = new RestVariable();
			restVariable1.setName("CaseCloseThirdCheck_response");
			restVariable1.setValue(caseCloseThirdCheck_response);
			variables.add(restVariable1);
		}
		ToCase toCase = toCaseService.findToCaseByCaseCode(processInstanceVO.getCaseCode());
		workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(),toCase.getLeadingProcessId(), processInstanceVO.getCaseCode());
		ajaxResponse.setSuccess(true);
		ajaxResponse.setMessage("审核成功");
		return ajaxResponse;
	}
}
