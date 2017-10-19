package com.centaline.api.ccai.service.impl;

import com.aist.common.exception.BusinessException;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.api.ccai.listener.EvalFlowWorkListener;
import com.centaline.api.ccai.service.CcaiEvalService;
import com.centaline.api.ccai.vo.EvalRebeatImport;
import com.centaline.api.ccai.vo.EvalRebeatReportImport;
import com.centaline.api.ccai.vo.MQEvalMessage;
import com.centaline.api.ccai.vo.TaskInfo;
import com.centaline.api.common.vo.CcaiServiceResult;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.common.enums.EvalRebateStatusEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.eval.entity.ToEvalRebate;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.service.ToEvalRebateService;
import com.centaline.trans.eval.service.ToEvalReportProcessService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ToApproveRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * ccai评估相关导入实现类
 *
 * @author yinchao
 */
@Service
@Transactional
public class CcaiEvalServiceImpl implements CcaiEvalService {
	private static final String REBATE_APPROVE_TYPE = "18";
	@Autowired
	ToEvalRebateService toEvalRebateService;

	@Autowired
	ToCaseInfoService toCaseInfoService;

	@Autowired
	ToEvalReportProcessService toEvalReportProcessService;

	@Autowired
	ToApproveRecordService toApproveRecordService;

	@Autowired
	UamUserOrgService uamUserOrgService;

	@Autowired
	private JmsMessagingTemplate jmsTemplate; //activemq 消息队列
	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();//Hibernate校验工具

	@Override
	public CcaiServiceResult importEvalRebate(EvalRebeatImport info) {
		ToEvalRebate evalRebate = validateAndConvert(info);
		ToEvalRebate old = toEvalRebateService.findToEvalRebateByCaseCode(evalRebate.getCaseCode());
		if(old==null){
			evalRebate.setStatus(EvalRebateStatusEnum.DOING.getCode());
			//首次同步
			toEvalRebateService.insertSelective(evalRebate);
			//导入审批记录
			for(TaskInfo task : info.getTasks()){
				ToApproveRecord record = new ToApproveRecord();
				record.setCaseCode(evalRebate.getCaseCode());
				record.setPartCode("CCAI_REBATE");
				record.setApproveType(REBATE_APPROVE_TYPE);
				record.setContent(task.getComment());
				record.setOperatorTime(task.getDealTime());
				if(task.getResult()==0){//审批未通过
					record.setNotApprove("审批未通过.");
				}
				//设置处理人
				User u = uamUserOrgService.getUserByUsername(task.getApplyUserName());
				if(u!=null){
					record.setOperator(u.getId());
				}else{
					record.setOperator(task.getApplyRealName()+"_"+task.getApplyUserName());
				}
				toApproveRecordService.saveToApproveRecord(record);
			}
			//发送消息 启动流程
			MQEvalMessage message = new MQEvalMessage(evalRebate.getCaseCode(), WorkFlowEnum.EVAL_REBATE_PROCESS.getCode(),MQEvalMessage.STARTFLOW_TYPE);
			jmsTemplate.convertAndSend(EvalFlowWorkListener.getEvalQueueName(), message);
			return new CcaiServiceResult("00",true,"成功");
		}else{
			throw new BusinessException("返利单已存在，无法进行同步!");
		}
	}

	@Override
	public CcaiServiceResult updateEvalRebate(EvalRebeatImport info) {
		ToEvalRebate evalRebate = validateAndConvert(info);
		ToEvalRebate old = toEvalRebateService.findToEvalRebateByCaseCode(evalRebate.getCaseCode());
		if(old!=null && EvalRebateStatusEnum.BACK.getCode().equals(old.getStatus())){
			//TODO 驳回修改后再次同步
			evalRebate.setPkid(old.getPkid());
			toEvalRebateService.updateByPrimaryKeySelective(evalRebate);
			//先删除旧的审批记录
			toApproveRecordService.deleteByCaseCodeAndType(evalRebate.getCaseCode(),REBATE_APPROVE_TYPE);
			//导入审批记录
			for(TaskInfo task : info.getTasks()){
				ToApproveRecord record = new ToApproveRecord();
				record.setCaseCode(evalRebate.getCaseCode());
				record.setPartCode("CCAI_REBATE");
				record.setApproveType(REBATE_APPROVE_TYPE);
				record.setContent(task.getComment());
				record.setOperatorTime(task.getDealTime());
				//设置处理人
				User u = uamUserOrgService.getUserByUsername(task.getApplyUserName());
				if(u!=null){
					record.setOperator(u.getId());
				}else{
					record.setOperator(task.getApplyRealName()+"_"+task.getApplyUserName());
				}
				toApproveRecordService.saveToApproveRecord(record);
			}
			//发送消息 恢复流程 更改返利单状态
			MQEvalMessage message = new MQEvalMessage(evalRebate.getCaseCode(), WorkFlowEnum.EVAL_REBATE_PROCESS.getCode(),MQEvalMessage.UPDATEFLOW_TYPE);
			jmsTemplate.convertAndSend(EvalFlowWorkListener.getEvalQueueName(), message);
			return new CcaiServiceResult("00",true,"成功");
		}else{
			throw new BusinessException("返利单状态不正确，无法进行修改!");
		}
	}

	@Override
	public CcaiServiceResult importEvalRebateReport(EvalRebeatReportImport info) {
		ToEvalRebate evalRebate = validateAndConvert(info);
		ToEvalRebate old = toEvalRebateService.findToEvalRebateByCaseCode(evalRebate.getCaseCode());
		if(old!=null && EvalRebateStatusEnum.SUCCESS.getCode().equals(old.getStatus())){
			//修改原有信息 生成最终报告
			evalRebate.setPkid(old.getPkid());
			evalRebate.setStatus(EvalRebateStatusEnum.FINISH.getCode());
			evalRebate.setEvalRecept(info.getEvalRecept());
			evalRebate.setCreateTime(info.getCreateTime());
			toEvalRebateService.updateByPrimaryKeySelective(evalRebate);
			//先删除旧的审批记录
			toApproveRecordService.deleteByCaseCodeAndType(evalRebate.getCaseCode(),REBATE_APPROVE_TYPE);
			//导入审批记录
			for(TaskInfo task : info.getTasks()){
				ToApproveRecord record = new ToApproveRecord();
				record.setCaseCode(evalRebate.getCaseCode());
				record.setPartCode("CCAI_REBATE");
				record.setApproveType(REBATE_APPROVE_TYPE);
				record.setContent(task.getComment());
				record.setOperatorTime(task.getDealTime());
				//设置处理人
				User u = uamUserOrgService.getUserByUsername(task.getApplyUserName());
				if(u!=null){
					record.setOperator(u.getId());
				}else{
					record.setOperator(task.getApplyRealName()+"_"+task.getApplyUserName());
				}
				toApproveRecordService.saveToApproveRecord(record);
			}
			//发送消息 恢复流程 更改返利单状态
			MQEvalMessage message = new MQEvalMessage(evalRebate.getCaseCode(), WorkFlowEnum.EVAL_REBATE_PROCESS.getCode(),MQEvalMessage.UPDATEFLOW_TYPE);
			jmsTemplate.convertAndSend(EvalFlowWorkListener.getEvalQueueName(), message);
			return new CcaiServiceResult("00",true,"成功");
		}else{
			throw new BusinessException("返利单状态不正确，无法更改报告!");
		}
	}

	/**
	 * 将导入的评估返利信息
	 * 校验和转换为系统中的对象，并保存/更新
	 * @param erImport 导入的评估返利信息
	 * @return
	 */
	private ToEvalRebate validateAndConvert(EvalRebeatImport erImport){
		StringBuilder msg = new StringBuilder();
		buildErrorMessage(validator.validate(erImport.getTasks()),msg,"");
		if (msg.length() > 0) {
			throw new BusinessException(msg.toString());
		}
		//TODO 其他更多的校验
		String caseCode = toCaseInfoService.findcaseCodeByccaiCode(erImport.getOringinCcaiCode());
		if(StringUtils.isNotBlank(caseCode)){
			ToEvalReportProcess evalReportProcess = toEvalReportProcessService.findToEvalReportProcessByCaseCode(caseCode);
			if(evalReportProcess!=null&&StringUtils.isNotBlank(evalReportProcess.getEvaCode())){
				ToEvalRebate rebate = new ToEvalRebate();
				rebate.setEvaCode(evalReportProcess.getEvaCode());
				rebate.setCaseCode(caseCode);
				rebate.setCcaiCode(erImport.getCcaiCode());
				rebate.setEvaRpocessId(erImport.getApplyId());
				rebate.setEvalPrice(erImport.getEvalPrice());
				rebate.setEvalRealCharges(erImport.getEvalRealCharges());
				rebate.setEvalDueCharges(erImport.getEvalDueCharges());
				rebate.setInputTime(erImport.getInputTime());
				rebate.setApplyRealName(erImport.getApplyRealName());
				rebate.setApplyUserName(erImport.getApplyUserName());
				return rebate;
			}else{
				throw new BusinessException("未获取到返利申请对应的评估信息!");
			}
		}else{
			throw new BusinessException("未获取到返利申请对应的成交报告["+erImport.getOringinCcaiCode()+"]信息!");
		}
	}

	/**
	 * 根据将校验信息的结果拼接到传入的msgBuilder中，
	 * 如果appendBefore不为null，则拼接到每个错误消息前
	 *
	 * @param validate
	 * @param msgBuilder
	 * @param appendBefore
	 * @param <T>
	 */
	private <T> void buildErrorMessage(Set<ConstraintViolation<T>> validates, StringBuilder msgBuilder, String appendBefore) {
		for (ConstraintViolation constraintViolation : validates) {
			msgBuilder.append(appendBefore).append(constraintViolation.getMessage()).append("\r\n");
		}
	}
}
