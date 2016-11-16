package com.centaline.trans.cases.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.bizwarn.service.BizWarnInfoService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.CaseResetService;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseResetVo;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.exception.WorkFlowException;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.service.UnlocatedTaskService;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import com.centaline.trans.utils.ConstantsUtil;

@Service
public class CaseResetServiceImpl implements CaseResetService {
	@Autowired
	private ToWorkFlowService workflowService;
	@Autowired
	private UnlocatedTaskService unlocatedTaskService;
	@Autowired
	private ToCaseService caseService;
	@Autowired
	private ToCaseInfoService caseInfoservice;
	@Autowired
	private TgServItemAndProcessorService tgServItemAndProcessorService;
	@Autowired
	private WorkFlowManager workflowManager;

	@Autowired
	private BizWarnInfoService bizWarnInfoService;

	@Autowired
	private ToMortgageService toMortgageService;
	
	@Autowired
	private TransplanServiceFacade toTransplanOperateService;//add by zhoujp 

	@Override
	public void reset(CaseResetVo vo) {
		// 更新Workflow表为终止状态
		ToWorkFlow tf = new ToWorkFlow();
		tf.setCaseCode(vo.getCaseCode());
		List<ToWorkFlow> tfs = workflowService
				.queryActiveToWorkFlowByCaseCode(tf);
		if (tfs != null) {
			for (ToWorkFlow toWorkFlow : tfs) {
				toWorkFlow.setStatus(WorkFlowStatus.TERMINATE.getCode());// 流程终止状态
				workflowService.updateByPrimaryKeySelective(toWorkFlow);
			}
		}
		// 操作Case表和Caseinfo表
		ToCase cas = caseService.findToCaseByCaseCode(vo.getCaseCode());
		ToCaseInfo casInfo = caseInfoservice.findToCaseInfoByCaseCode(vo
				.getCaseCode());

		cas.setLeadingProcessId(casInfo.getRequireProcessorId());
		cas.setStatus(CaseStatusEnum.WFD.getCode());
		cas.setCaseProperty(CasePropertyEnum.TPZT.getCode());
		casInfo.setResDate(null);
		casInfo.setIsResponsed("0");

		caseInfoservice.updateByPrimaryKey(casInfo);
		caseService.updateByPrimaryKey(cas);
		//将交易计划表的数据转移到交易计划历史表并删除交易计划表
		toTransplanOperateService.processRestartOrResetOperate(vo.getCaseCode(), ConstantsUtil.PROCESS_RESET);

		// 删除服务表
		tgServItemAndProcessorService.deleteByPrimaryCaseCode(vo.getCaseCode());
		// 无效掉表单数据
		workflowService.inActiveForm(vo.getCaseCode());
		// 删除流程引擎
		if (tfs != null) {
			for (ToWorkFlow toWorkFlow : tfs) {
				try {
					unlocatedTaskService.deleteByInstCode(toWorkFlow
							.getInstCode());
					workflowManager.deleteProcess(toWorkFlow.getInstCode());
				} catch (WorkFlowException e) {
					if (!e.getMessage().contains("statusCode[404]"))
						throw e;
				}

			}
		}

		bizWarnInfoService.deleteByCaseCode(vo.getCaseCode()); // 删除商贷流失预警信息

		// 流程重启更改掉案件临时银行的状态
		ToMortgage toMortgage = toMortgageService.getMortgageByCaseCode(vo
				.getCaseCode());
		if (toMortgage != null) {
			toMortgageService.updateTmpBankStatus(vo.getCaseCode());
		}

	}
}
