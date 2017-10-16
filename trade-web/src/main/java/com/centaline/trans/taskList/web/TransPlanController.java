package com.centaline.trans.taskList.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.repository.ToApproveRecordMapper;
import com.centaline.trans.task.service.LoanlostApproveService;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.entity.TsTransPlanHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import com.centaline.trans.transplan.vo.TransPlanVO;
import com.centaline.trans.utils.UiImproveUtil;

@Controller
@RequestMapping(value = "/task/transPlan")
public class TransPlanController {

	@Autowired
	private TransplanServiceFacade transplanServiceFacade;

	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private ToCaseInfoService toCaseInfoService;
	@Autowired(required =true)
	private UamSessionService uamSessionService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private LoanlostApproveService loanlostApproveService;
	@RequestMapping("process")
	public String toProcess(HttpServletRequest request,
			HttpServletResponse response, String caseCode, String source,
			String taskitem, String instCode) {
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		int cou = toCaseService.findToLoanAgentByCaseCode(caseCode);
		if (cou > 0) {
			caseBaseVO.setLoanType("30004005");
		}
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		/*获取案件信息，用以判断贷款类型 by wbzhouht*/
		ToCaseInfo toCaseInfo=toCaseInfoService.findToCaseInfoByCaseCode(caseCode);
		if(null!=toCaseInfo){
			request.setAttribute("toCaseInfo",toCaseInfo);
		}
		request.setAttribute("transPlan",
				transplanServiceFacade.findTransPlanByCaseCode(caseCode));
		return "task" + UiImproveUtil.getPageType(request)
				+ "/taskTransPlanFilling";
	}

	@RequestMapping(value = "saveTransPlan")
	@ResponseBody
	public Boolean saveTransPlan(HttpServletRequest request,
			TransPlanVO transPlanVO) {
		transplanServiceFacade.saveToTransPlan(transPlanVO);

		return true;
	}

	@RequestMapping(value = "submitTransPlan")
	@ResponseBody
	public boolean submitTransPlan(HttpServletRequest request,
			TransPlanVO transPlanVO) {
		transplanServiceFacade.saveToTransPlan(transPlanVO);

		/* 流程引擎相关 */
		List<RestVariable> variables = new ArrayList<RestVariable>();

		ToCase toCase = toCaseService.findToCaseByCaseCode(transPlanVO
				.getCaseCode());

		return workFlowManager.submitTask(variables, transPlanVO.getTaskId(),
				transPlanVO.getProcessInstanceId(),
				toCase.getLeadingProcessId(), transPlanVO.getCaseCode());
	}

	/**
	 * 页面初始化
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "showTransPlanHistory")
	public String showTransPlanHistory(String caseCode, ServletRequest request) {
		request.setAttribute("caseCode", caseCode);
		return "trans/trans_history_list";
	}

	/**
	 * 交易计划变更审核页面
	 * @author wbzhouht
	 * @param caseCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "detail")
	public String tranPlanAppver(String caseCode, ServletRequest request) {
		request.setAttribute("caseCode", caseCode);
		return "trans/trans_plan_appver";
	}
	@RequestMapping(value = "submitTransAppver")
	@ResponseBody
	public AjaxResponse<?> submitTranPlanAppver(String[]pkids, String[] newDates, String[] partCodes,Boolean audit,TransPlanVO transPlanVO){
		/**
		 *
		 * 同意变更交易计划，修改交易计划表，并修改交易历史记录表的状态并提交任务，
		 * 不同意则不修改交易计划表，修改交易历史记录表的状态并提交任务
		 */
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		ToTransPlan tp=new ToTransPlan();
		TsTransPlanHistory ts1=new TsTransPlanHistory();
		if(pkids.length>0&&newDates.length>0&&partCodes.length>0&&transPlanVO!=null) {
			for (int i = 0; i < pkids.length; i++) {
				if (audit) {
					//通过partCode和caseCode查询交易计划表的PKID
					try {
						TsTransPlanHistory ts = new TsTransPlanHistory();
						ts.setCaseCode(transPlanVO.getCaseCode());
						ts.setPartCode(partCodes[i]);
						//根据partCode和caseCode查询交易计划
						ToTransPlan toTransPlan = transplanServiceFacade.findTransPlanPKIDBycasecodeAndPartCode(ts);
						if (toTransPlan != null) {
							tp.setPkid(toTransPlan.getPkid());

						tp.setEstPartTime(format.parse(newDates[i]));
						//修改交易计划
						transplanServiceFacade.updateByPrimaryKeySelective(tp);
						ts1.setPkid(Long.parseLong(pkids[i]));
						ts1.setAuditResult(1);
						//修改交易计划历史记录为审核通过状态
						transplanServiceFacade.updateTransPlanHistoryByPKID(ts1);
						}else {
							return AjaxResponse.fail("未找到交易计划！");
						}
					} catch (ParseException e) {
						return AjaxResponse.fail("数据转换异常");
					}
				} else {
					ts1.setPkid(Long.parseLong(pkids[i]));
					ts1.setAuditResult(2);
					//修改交易计划历史记录为审核不通过状态
					transplanServiceFacade.updateTransPlanHistoryByPKID(ts1);
				}
			}
			//获取登录人信息
			SessionUser sessionUser = uamSessionService.getSessionUser();
			//启动流程变量
			List<RestVariable> variables = new ArrayList<>();
			//提交任务
			workFlowManager.submitTask(variables, transPlanVO.getTaskId(), transPlanVO.getProcessInstanceId(), sessionUser.getId(), transPlanVO.getCaseCode());
			//在审批记录表中插入数据
			ToApproveRecord toApproveRecord=new ToApproveRecord();
			toApproveRecord.setCaseCode(transPlanVO.getCaseCode());
			toApproveRecord.setProcessInstance(transPlanVO.getProcessInstanceId());
			toApproveRecord.setPartCode(transPlanVO.getPartCode());
			toApproveRecord.setOperator(sessionUser.getId());
			toApproveRecord.setContent(audit==true?"交易变更审核通过！":"交易变更审核不通过！");
			toApproveRecord.setOperatorTime(new Date());
			loanlostApproveService.saveLoanlostApprove(toApproveRecord);
			//任务完结
			ToWorkFlow flow=new ToWorkFlow();
			flow.setBusinessKey("TransPlanAppver");
			flow.setCaseCode(transPlanVO.getCaseCode());
			flow.setBizCode(transPlanVO.getCaseCode());
			ToWorkFlow tranPlanFlow= toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(flow);
			tranPlanFlow.setStatus(WorkFlowStatus.COMPLETE.getCode());
			toWorkFlowService.updateByPrimaryKeySelective(tranPlanFlow);
			return AjaxResponse.success("操作成功");
		}else {
			return AjaxResponse.fail("数据传递错误！");
		}
	}
}
