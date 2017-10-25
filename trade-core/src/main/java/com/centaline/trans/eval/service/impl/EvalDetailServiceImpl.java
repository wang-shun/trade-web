package com.centaline.trans.eval.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.service.ToCaseParticipantService;
import com.centaline.trans.common.enums.EvalStatusEnum;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.exception.WorkFlowException;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.evaPricing.entity.ToEvaPricingVo;
import com.centaline.trans.evaPricing.service.EvaPricingService;
import com.centaline.trans.eval.entity.ToEvaInvoice;
import com.centaline.trans.eval.entity.ToEvaRefund;
import com.centaline.trans.eval.entity.ToEvalRebate;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.entity.ToEvalSettle;
import com.centaline.trans.eval.repository.ToEvaRefundMapper;
import com.centaline.trans.eval.repository.ToEvalReportProcessMapper;
import com.centaline.trans.eval.service.EvalDetailService;
import com.centaline.trans.eval.service.ToEvaCommPersonAmountService;
import com.centaline.trans.eval.service.ToEvaCommissionChangeService;
import com.centaline.trans.eval.service.ToEvaInvoiceService;
import com.centaline.trans.eval.service.ToEvalRebateService;
import com.centaline.trans.eval.service.ToEvalReportProcessService;
import com.centaline.trans.eval.service.ToEvalSettleService;
import com.centaline.trans.eval.vo.EvalChangeCommVO;
import com.centaline.trans.task.service.ActRuTaskService;

/**
 * @Description:TODO
 * @author：jinwl6
 * @date:2017年10月18日
 * @version:
 */
@Service
public class EvalDetailServiceImpl implements EvalDetailService {
	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired
	private EvaPricingService evaPricingService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	ActRuTaskService actRuTaskService;
	@Autowired
	ToEvalReportProcessService toEvalReportProcessService;
	@Autowired
	ToEvalSettleService toEvalSettleService;
	@Autowired
	ToEvaInvoiceService toEvaInvoiceService;
	@Autowired
	ToEvaCommissionChangeService toEvaCommissionChangeService;
	@Autowired
	ToEvaRefundMapper toEvaRefundMapper;
	@Autowired
	ToEvalRebateService toEvalRebateService;
	@Resource
    private UamTemplateService  uamTemplateService;	
	@Qualifier("uamMessageServiceClient")
	@Autowired
	UamMessageService uamMessageService;
	@Autowired
	ToCaseParticipantService toCaseParticipantService;
	@Autowired
	ToEvalReportProcessMapper toEvalReportProcessMapper;
	@Autowired
	ToEvaCommPersonAmountService toEvaCommPersonAmountService;
	@Autowired
	UamUserOrgService uamUserOrgService;
	
	@Override
	public void evalDetail(HttpServletRequest request, String caseCode, String evaCode) {
				SessionUser user = uamSessionService.getSessionUser();
				String userOrgId = user.getServiceDepId();
				
				ToEvaPricingVo toEvaPricingVo = evaPricingService.findEvaPricingDetailByCaseCode(caseCode);//查询询价信息
				ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.findToEvalReportProcessByEvalCode(evaCode);//查询
				
				// 工作流
				ToWorkFlow inWorkFlow = new ToWorkFlow();
				inWorkFlow.setBusinessKey(WorkFlowEnum.EVAL_PROCESS.getCode());
				inWorkFlow.setBizCode(evaCode);
				ToWorkFlow toWorkFlow = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(inWorkFlow);
				
				//评估发票信息
				ToEvaInvoice toEvaInvoice = toEvaInvoiceService.selectByEvaCode(evaCode);
				//评估返利报告审批信息
				ToEvalRebate toEvalRebate = toEvalRebateService.findToEvalRebateByCaseCode(caseCode);
				
				//评估结算信息
				ToEvalSettle toEvalSettle = toEvalSettleService.findToCaseByCaseCode(caseCode);
				
				//评估退费信息
				ToEvaRefund toEvaRefund = toEvaRefundMapper.selectByEvaCode(caseCode);
				//调佣审批信息
				//ToEvaCommissionChange toEvaCommissionChange = toEvaCommissionChangeService.selectByCaseCode(caseCode);
				EvalChangeCommVO toEvaCommPersonAmount = toEvaCommPersonAmountService.getFullEvalChangeCommVO(caseCode);
				
				request.setAttribute("toEvaPricingVo", toEvaPricingVo);
				request.setAttribute("toEvalReportProcessVo", toEvalReportProcess);
				request.setAttribute("toEvaInvoiceVo", toEvaInvoice);
				request.setAttribute("toEvalRebateVo", toEvalRebate);
				request.setAttribute("toEvalSettleVo", toEvalSettle);
				request.setAttribute("toEvaRefundVo", toEvaRefund);
				request.setAttribute("toEvaCommissionChange", toEvaCommPersonAmount);
				
				request.setAttribute("caseCode", caseCode);
				request.setAttribute("evaCode", evaCode);
				request.setAttribute("queryOrg", userOrgId);
				request.setAttribute("toWorkFlow", toWorkFlow);
	}
	
	
	@Override
	public AjaxResponse<?> evalReject(HttpServletRequest request, String caseCode, String evaCode) {
		        SessionUser currentUser = uamSessionService.getSessionUser();
		        //删除评估流程
				List<TaskVo> taskVos = actRuTaskService.getRuTaskByBizCode(evaCode);
				for (TaskVo task : taskVos) {
						try{
							workFlowManager.deleteProcess(task.getInstCode());
						}catch(WorkFlowException e){
		                    throw e;					
						}
				}
				
				//更新评估单状态为驳回
			    ToEvalReportProcess toEvalReportProcess = new ToEvalReportProcess();
			    toEvalReportProcess.setStatus(EvalStatusEnum.BBH.getCode());
			    toEvalReportProcess.setEvaCode(evaCode);
				toEvalReportProcessMapper.updateStatusByEvalCode(toEvalReportProcess);
				
				//根据案件号查询贷款权证
				ToCaseParticipant toCaseParticipant = new ToCaseParticipant();
				toCaseParticipant.setCaseCode(caseCode);
				toCaseParticipant.setPosition("loan");
				List<ToCaseParticipant> toCaseParticipantList = toCaseParticipantService.findToCaseParticipantByCondition(toCaseParticipant);
				if(toCaseParticipantList!=null && toCaseParticipantList.size()>0){
					//发站内信息通知申请人
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("mobile", currentUser.getMobile());
					params.put("caseCode", caseCode);
					String content = uamTemplateService.mergeTemplate("TMP_EVAL_REJECT", params);
				    Message message = new Message();
				    // 消息标题
				    message.setTitle("评估驳回提醒");
				    // 消息类型
				    message.setType(MessageType.SITE);
				    //设置提醒列别 
				    message.setMsgCatagory(MsgCatagoryEnum.NEWS.getCode());
				    //内容 
				    message.setContent(content);
				    // 发送人 
				    String senderId = currentUser.getId();
				    //设置发送人 
				    message.setSenderId(senderId);
				    uamMessageService.sendMessageByDist(message,uamUserOrgService.getUserByUsername(toCaseParticipantList.get(0).getUserName()).getId());
				}
				
		 return new AjaxResponse<>(true);
	}
    
	@Override
	public AjaxResponse<?> saveEvaComChangeItems(HttpServletRequest request, String evaCode,String changeInfo) {
		int result = toEvalReportProcessService.updateChangeInfoByEvalCode(evaCode, changeInfo);
		if(result>0){
			return new AjaxResponse<>(true);
		}else{
			return new AjaxResponse<>(false);
		}
		
	}


	@Override
	public AjaxResponse<?> checkTransferCommission(HttpServletRequest request, String evaCode) {
		ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.selectToEvaReportProcessByEvaCode(evaCode);
		String changeInfo=toEvalReportProcess.getChangeInfo();
		if(changeInfo!=null){
			return new AjaxResponse<>(true);
		}else{
			return new AjaxResponse<>(false);
		}
	}

}