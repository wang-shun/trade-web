package com.centaline.trans.mortgage.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.eval.entity.ToEvaFeeRecord;
import com.centaline.trans.eval.service.ToEvaFeeRecordService;
import com.centaline.trans.mortgage.entity.ToEvaReport;
import com.centaline.trans.mortgage.repository.ToEvaReportMapper;
import com.centaline.trans.mortgage.service.ToEvaReportService;
import com.centaline.trans.remote.service.EguService;
import com.centaline.trans.remote.vo.MortgageAttamentVo;
import com.centaline.trans.task.vo.ProcessInstanceVO;

@Service
public class ToEvaReportServiceImpl implements ToEvaReportService{

	@Autowired
	private ToEvaReportMapper toEvaReportMapper;
	
	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private ToCaseService toCaseService;
	
	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	
	@Autowired
	private EguService eguService;
	
	@Autowired
	private ToEvaFeeRecordService toEvaFeeRecordService;
	
	@Autowired
	private PropertyUtilsService propertyUtilsService;
	
	@Override
	public void saveToEvaReport(ToEvaReport toEvaReport) {
		if(toEvaReport.getPkid() != null){
			toEvaReportMapper.update(toEvaReport);
		}else{
			toEvaReportMapper.insert(toEvaReport);
		}
	}

	@Override
	public List<ToEvaReport> findToEvaReportByEvaCode(ToEvaReport toEvaReport) {
		
		return toEvaReportMapper.findToEvaReportByEvaCode(toEvaReport);
	}

	@Override
	public void updateToEvaReport(ToEvaReport toEvaReport) {
		toEvaReportMapper.update(toEvaReport);
		
	}

	@Override
	public ToEvaReport findFinalComByCaseCode(String caseCode) {
		return toEvaReportMapper.findFinalComByCaseCode(caseCode);
	}

	@Override
	public ToEvaReport findToEvaReportById(Long pkid) {
		return toEvaReportMapper.findToEvaReportById(pkid);
	}

	@Override
	public ToEvaReport findByProcessId(String processId) {
		
		return toEvaReportMapper.findByProcessId(processId);
	}

	@Override
	public void submitEvaReport(ToEvaReport toEvaReport,
			ProcessInstanceVO processInstanceVO,
			MortgageAttamentVo mortgageAttament) {
		ToEvaFeeRecord toEvaFeeRecord = toEvaFeeRecordService.findToEvaFeeRecordByCaseCode(processInstanceVO.getCaseCode());
		if(toEvaFeeRecord == null || !StringUtils.equals(toEvaFeeRecord.getIsEvalFeeGet(),"1")){
    		throw new BusinessException("该案件的评估费未收齐，不能发起报告申请！");
		}
		
		ProcessInstance processInstance = new ProcessInstance();
		processInstance.setBusinessKey(WorkFlowEnum.EVA_WBUSSKEY.getCode());
		processInstance.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.EVA_WBUSSKEY.getCode()));
    	
    	ToCase toCase = toCaseService.findToCaseByCaseCode(processInstanceVO.getCaseCode());	
    	StartProcessInstanceVo pIVo = workFlowManager.startCaseWorkFlow1(processInstance, processInstanceVO.getCaseCode(),toCase.getLeadingProcessId());
    	if(pIVo==null||pIVo.getId()==null){
    		throw new BusinessException("流程启动失败！");
    	}
    	SessionUser user = uamSessionService.getSessionUser();
    	//保存流程数据
		ToWorkFlow toWorkFlow = new ToWorkFlow();
		toWorkFlow.setCaseCode(processInstanceVO.getCaseCode());
		toWorkFlow.setInstCode(pIVo.getId());
		toWorkFlow.setProcessDefinitionId(pIVo.getProcessDefinitionId());
		toWorkFlow.setBusinessKey(WorkFlowEnum.EVA_WBUSSKEY.getCode());
		toWorkFlow.setProcessOwner(user.getId());
		toWorkFlowService.insertSelective(toWorkFlow);
		
		//保存报告单信息
		ToEvaReport report = new ToEvaReport();
		report.setEvaCode(toEvaReport.getEvaCode());
		report.setReportType(toEvaReport.getReportType());
		report.setCaseCode(toEvaReport.getCaseCode());
		report.setReportAriseTime(new Date());
		report.setFeedback("已发起");
		report.setReportAriseTime(new Date());
		report.setReportResponseTime(new Date());
		report.setEvaProcessId(pIVo.getId());
		report.setFinOrgCode(toEvaReport.getFinOrgCode());
		report.setComment(toEvaReport.getComment());
		report.setIsMainLoanBank(toEvaReport.getIsMainLoanBank());
		saveToEvaReport(report);	
		eguService.saveAttachment(mortgageAttament);
	}

}
