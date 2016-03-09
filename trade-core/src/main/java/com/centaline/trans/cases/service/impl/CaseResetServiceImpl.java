package com.centaline.trans.cases.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.CaseResetService;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseResetVo;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.service.ToTransPlanService;
@Service
public class CaseResetServiceImpl implements CaseResetService {
	@Autowired
	private ToWorkFlowService workflowService;
	@Autowired
	private ToCaseService caseService;
	@Autowired
	private ToCaseInfoService caseInfoservice;
	@Autowired
	private ToTransPlanService toTransPlanService;
	@Autowired
	private TgServItemAndProcessorService tgServItemAndProcessorService;
	@Autowired
	private WorkFlowManager workflowManager;
	@Override
	public void reset(CaseResetVo vo) {
		//更新Workflow表为终止状态
		ToWorkFlow tf=new ToWorkFlow();
		tf.setCaseCode(vo.getCaseCode());
		List<ToWorkFlow> tfs=workflowService.queryActiveToWorkFlowByCaseCode(tf);
		if(tfs!=null){
			for (ToWorkFlow toWorkFlow : tfs) {
				toWorkFlow.setStatus(WorkFlowStatus.TERMINATE.getCode());//流程终止状态
				workflowService.updateByPrimaryKeySelective(toWorkFlow);
			}
		}
		//操作Case表和Caseinfo表
		ToCase cas= caseService.findToCaseByCaseCode(vo.getCaseCode());
		ToCaseInfo casInfo=caseInfoservice.findToCaseInfoByCaseCode(vo.getCaseCode());
		
		cas.setLeadingProcessId(casInfo.getRequireProcessorId());
		cas.setStatus(CaseStatusEnum.WFD.getCode());
		casInfo.setResDate(null);
		casInfo.setIsResponsed("0");
		
		caseInfoservice.updateByPrimaryKey(casInfo);
		caseService.updateByPrimaryKey(cas);
		//删除交易计划表
		toTransPlanService.deleteTransPlansByCaseCode(vo.getCaseCode());
		//删除服务表
		tgServItemAndProcessorService.deleteByPrimaryCaseCode(vo.getCaseCode());
		
		//删除流程引擎
		if(tfs!=null){
			for (ToWorkFlow toWorkFlow : tfs) {
				workflowManager.deleteProcess(toWorkFlow.getInstCode());
			}
		}
	}
}
